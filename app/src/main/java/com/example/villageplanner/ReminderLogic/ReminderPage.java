package com.example.villageplanner.ReminderLogic;

import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.addReminderToDatabase;
import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.dataSnapshotToReminder;
import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.getUserId;
import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.removeReminderFromDatabase;
import static com.example.villageplanner.helperAPI.TimeHelper.getReminderMilli;
import static com.example.villageplanner.helperAPI.TimeHelper.isExpired;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.villageplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import co.dift.ui.SwipeToAction;


public class ReminderPage extends AppCompatActivity {

    RecyclerView recyclerView;
    ReminderAdapter adapter;
    SwipeToAction swipeToAction;
    FloatingActionButton plus;
    private Location lastKnownLocation;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    List<Reminder> reminders = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_page);
        createNotificationChannel();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lastKnownLocation = extras.getParcelable("location");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ReminderAdapter(this.reminders);
        recyclerView.setAdapter(adapter);
        // Set reminders
        populate();


        plus = (FloatingActionButton) findViewById(R.id.add_remind);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Reminder>() {
            @Override
            public boolean swipeLeft(final Reminder itemData) {
                final int pos = removeReminder(itemData);
                displaySnackbar(itemData.getTitle() + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBook(pos, itemData);
                    }
                });
                return true;
            }

            @Override
            public boolean swipeRight(Reminder itemData) {
                displaySnackbar(itemData.getTitle() + " liked", null, null);
                return true;
            }

            @Override
            public void onClick(Reminder itemData) {
                displaySnackbar(itemData.getTitle() + " clicked", null, null);
                Intent in = new Intent(ReminderPage.this, ReminderViewer.class);
                in.putExtra("Reminder", itemData);
                startActivity(in);
            }

            @Override
            public void onLongClick(Reminder itemData) {
                displaySnackbar(itemData.getTitle() + " long clicked", null, null);
            }

        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(ReminderPage.this, CreateReminder.class);
                next.putExtra("location", lastKnownLocation);
                startActivity(next);
            }
        });
    }

    private void populate() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference =  database.getReference("Reminders").child(getUserId());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if(dataSnapshot == null) {
                        displaySnackbar("Firebase could not get reminders", null, null);
                    } else {
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            Reminder remind  = dataSnapshotToReminder(child);
                            if(isExpired(remind)) {
                                try {
                                    removeReminderFromDatabase(remind);
                                } catch (Exception e) {
                                    displaySnackbar("Could not remove reminder", null, null);
                                }
                            } else if(!isInReminders(remind)) {
                                    reminders.add(remind);
                                    adapter.notifyItemInserted(reminders.size() -1);
                                    setAlarm(remind);
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    System.out.println("I hate this!!!");
                }
            });

            if(reminders.isEmpty()) {
                LocalDateTime time = LocalDateTime.now();
                Reminder remind = new Reminder("Village", "Test Title", time, "Having fun at the Village today",
                        "cksjdklj", "4");
                reminders.add(remind);
                adapter.notifyItemInserted(reminders.size() -1);
            }


    }


    private int removeReminder(Reminder reminder) {
        int pos = reminders.indexOf(reminder);
        reminders.remove(reminder);
        adapter.notifyItemRemoved(pos);
        // Remove from Database
        try {
            removeReminderFromDatabase(reminder);
            cancelAlarm();
        } catch (Exception e) {
            displaySnackbar(e.toString(), null, null);
        }
        return pos;
    }

    private void addBook(int pos, Reminder book) {
        reminders.add(pos, book);
        adapter.notifyItemInserted(pos);
        // Add from Database
        boolean addition = addReminderToDatabase(book);
        if(addition == false) {
            displaySnackbar("Database Error: Could not add reminder", null, null);
        }
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        if (alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);
        snack.show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "villageReminderChannel";
            String description = "Channel For Reminders in the Village Planner";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("village",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setAlarm(Reminder notify) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("ReminderTitle", notify.getTitle());
        intent.putExtra("ReminderDescription", notify.getDescription());
        intent.putExtra("Reminder", notify);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getReminderMilli(notify),
                AlarmManager.INTERVAL_DAY*365,pendingIntent);
        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
        populate();
    }

    private boolean isInReminders(Reminder notify) {
        for(Reminder r: reminders) {
            if(r.getTitle().equals(notify.getTitle()) &&
                    r.getLocation().equals(notify.getLocation())) {
                return true;
            }
        }
        return false;
    }
}