package com.example.villageplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.villageplanner.ReminderMainPage.ReminderPage;

import java.time.LocalDateTime;
import java.util.Locale;

public class CreateReminder extends AppCompatActivity {
    Spinner locations;
    Spinner remindMechanism;
    TextView arrivalTime;
    TextView arrivalDate;
    EditText description;
    EditText title;
    int hour, minute, year, month, day;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        // Set-up the spinner for locations
        locations = findViewById(R.id.loc_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.store_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locations.setAdapter(adapter);

        // Set-up the remind mechanism spinner
        remindMechanism = findViewById(R.id.chooseRemindMechanism);
        ArrayAdapter<CharSequence> adapterTwo = ArrayAdapter.createFromResource(this,
                R.array.reminder_mechanism, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        remindMechanism.setAdapter(adapterTwo);



        // Set up the TextView
        arrivalTime = (TextView) findViewById(R.id.arrive);
        arrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });

        arrivalDate = (TextView) findViewById(R.id.dateArrive);
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
                createNewReminder();
            }
        });

        // Edit Test
        description = (EditText) findViewById(R.id.remindTitleText);
        title = (EditText) findViewById(R.id.descriptionText);
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int selectedMinute) {
                hour = hourOfDay;
                minute = selectedMinute;
                arrivalTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        // Controls the style of the clock (Theme_Material_Dialog_Alert)
        int style = 16974374;
        TimePickerDialog timerPickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timerPickerDialog.setTitle("Select Time");
        timerPickerDialog.show();
    }

    public void popDatePicker(View view) {
        DatePickerDialog.OnDateSetListener onTimeSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int sYear, int sMonth, int sDay) {
                year = sYear;
                month = sMonth;
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

        String location = locations.getSelectedItem().toString();

    }
}