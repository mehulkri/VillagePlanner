package com.example.villageplanner.ReminderLogic;

import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.addReminderToDatabase;
import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.getUserId;
import static com.example.villageplanner.ReminderLogic.ReminderFieldVerification.validateDate;
import static com.example.villageplanner.ReminderLogic.ReminderFieldVerification.validateHoursOfOperation;
import static com.example.villageplanner.helperAPI.TimeHelper.getReminderMilli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import com.google.android.material.timepicker.MaterialTimePicker;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.villageplanner.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.UUID;

public class CreateReminder extends AppCompatActivity {
    private Spinner locations;
    private TextView arrivalTime;
    private TextView arrivalDate;
    private EditText description;
    private EditText title;
    private int hour, minute, year, month, day;
    private Button submit;
    private CoordinatorLayout snackLayout;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        createNotificationChannel();

        // Get reminder Intent
        Reminder remind = (Reminder) getIntent().getSerializableExtra("Reminder");
        Bundle extras = getIntent().getExtras();
        // Get reminder Intent
        if (extras != null) {
            lastKnownLocation = extras.getParcelable("location");
        }
        // Set-up the spinner for locations
        locations = findViewById(R.id.loc_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.store_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locations.setAdapter(adapter);
        if(remind != null) {
            int index = getIndexFromValue(remind.getLocation());
            if(index >= 0) {
                locations.setSelection(index);
            }
            setTitle("Edit Reminder");
        }
        // Set up the TextView
        arrivalTime = (TextView) findViewById(R.id.arrive);
        arrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });

        arrivalDate = (TextView) findViewById(R.id.dateArrive);
        if(remind == null) {
            setDateText(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth() );
            year = LocalDate.now().getYear();
            month =  LocalDate.now().getMonthValue();
            day = LocalDateTime.now().getDayOfMonth();
        } else {
            LocalDateTime target = remind.getTargetTime();
            setDateText(target.getYear(), target.getMonthValue(), target.getDayOfMonth());
            setTimeText(target.getHour(), target.getMinute());
        }
        arrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDatePicker(view);
            }
        });

        // Submit button
        submit = (Button) findViewById(R.id.submitReminder);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyData()) {
                    createNewReminder();
                }
            }
        });

        // Edit Test
        description = (EditText) findViewById(R.id.remindTitleText);
        title = (EditText) findViewById(R.id.descriptionText);
        if(remind != null) {
            description.setText(remind.getDescription());
            title.setText(remind.getTitle());
        }

        // Setup CoordinateLayout
        snackLayout = (CoordinatorLayout) findViewById(R.id.snacking);
    }

    public void popTimePicker(View view) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(LocalTime.now().getHour())
                .setMinute(LocalTime.now().getMinute())
                .setTitleText("Select Reminder Time")
                .build();

        picker.show(getSupportFragmentManager(),"village");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = picker.getHour();
                minute = picker.getMinute();
                setTimeText(picker.getHour(), picker.getMinute());
            }
        });

    }

    public void popDatePicker(View view) {
        DatePickerDialog.OnDateSetListener onTimeSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int sYear, int sMonth, int sDay) {
                year = sYear;
                month = sMonth+1;
                day = sDay;
                arrivalDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", month, day, year));
            }
        };
        // Controls the style of the clock (Theme_Material_Dialog_Alert)
        int style = 16974374;
        DatePickerDialog timerPickerDialog = new DatePickerDialog(this, style, onTimeSetListener, year, month, day);
        timerPickerDialog.setTitle("Select Date");
        timerPickerDialog.show();
    }

    private void createNewReminder() {
        // Store this reminder in a database
        Reminder notify = createReminder();
        boolean added = addReminderToDatabase(notify);
        Toast toast;
        if(!added) {
            toast = Toast.makeText(getApplicationContext(), "Firebase Error: Could not add Reminder", Toast.LENGTH_LONG);
        } else {
            setAlarm(notify);
            toast = Toast.makeText(getApplicationContext(), "Success!!", Toast.LENGTH_LONG);
            Intent in = new Intent(CreateReminder.this, ReminderViewer.class);
            in.putExtra("Reminder", notify);
            startActivity(in);
        }
        toast.show();
    }

    private boolean verifyData() {
        String dateError = validateDate(year, month, day, hour, minute);
        if(!dateError.isEmpty()) {
            printError(dateError);
            return false;
        }
        String loc = locations.getSelectedItem().toString();
        String hoursError = validateHoursOfOperation(hour, loc);
        if(!hoursError.isEmpty()) {
            printError(hoursError);
            return false;
        }
        String titular = title.getText().toString();
        if(title.getText().toString().isEmpty()) {
            printError("Need to fill in a title for your reminder");
            return false;
        }
        return true;
    }

    private Reminder createReminder() {
        String location = locations.getSelectedItem().toString();
        LocalDateTime time = LocalDateTime.of(year, month, day, hour, minute);
        // I think I switched these up
        String des = title.getText().toString();
        String titular = description.getText().toString();
        String userId = getUserId();
        if(userId.isEmpty()) {
            userId = "4";
        }
        UUID reminderId = UUID.randomUUID();
        Reminder remind = new Reminder(location, titular, time, des, reminderId.toString(), userId);
        remind.setLastKnownLocation(lastKnownLocation);

        remind.writeToJSONFile(getApplicationContext());
        return remind;
    }

    private void printError(String text) {
        Snackbar snack = Snackbar.make(snackLayout, text, Snackbar.LENGTH_LONG);
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
        intent.putExtra("LeaveTime", notify.getLeaveTime());
        intent.putExtra("TargetTime", notify.getTargetTime());
        LocalDateTime time = notify.getRemindTime();
        String dates = String.format(Locale.getDefault(), "%02d/%02d/%04d", time.getMonthValue(),
                time.getDayOfMonth(), time.getYear());
        intent.putExtra("Date", dates);
        intent.putExtra("Place", notify.getLocation());
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getReminderMilli(notify),
                AlarmManager.INTERVAL_DAY*365,pendingIntent);
        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
    }

    private void setTimeText(int hour, int minute) {
        int normalHour = hour;
        if(hour > 12) {
            normalHour = normalHour - 12;
        }
        arrivalTime.setText(String.format(Locale.getDefault(), "%02d:%02d", normalHour, minute));
    }

    private void setDateText(int year, int month, int day) {
        arrivalDate.setText(String.format(Locale.getDefault(),
                "%02d/%02d/%04d", month, day, year));
    }

    private int getIndexFromValue(String location) {
        Resources res = getResources();
        String[] locations = res.getStringArray(R.array.store_list);
        for(int i=0; i < locations.length; i++) {
            if(locations[i].equals(location)) {
                return i;
            }
        }
        return -1;
    }
}