package com.example.villageplanner.ReminderLogic;

import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.addReminderToDatabase;
import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.dataSnapshotToReminder;
import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.getReminders;
import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.removeReminderFromDatabase;
import static com.example.villageplanner.helperAPI.TimeHelper.getReminderMilli;

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


import com.example.villageplanner.HomeLogic.HomepageActivity;
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

        // Set reminders
        populate();

        adapter = new ReminderAdapter(this.reminders);
        recyclerView.setAdapter(adapter);
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
            DatabaseReference reference =  database.getReference("Reminders").child("4");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for(DataSnapshot child : dataSnapshot.getChildren()) {
                        Reminder remind  = dataSnapshotToReminder(child);
                        reminders.add(remind);
                        setAlarm(remind);
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    System.out.println("I hate this!!!");
                }
            });
            if(reminders.size() == 0) {
                displaySnackbar("Firebase could not get reminders", null, null);
                for(int i=0; i < 10; i++) {
                    this.reminders.add(new Reminder("Cava", "Eat at Cava", LocalDateTime.now(), "This is the description", "efdsfdfds", "0"));
                }
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
                2000,pendingIntent);
        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
    }
}