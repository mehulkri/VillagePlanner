package com.example.villageplanner.ReminderLogic;

import static com.example.villageplanner.ReminderLogic.ReminderFieldVerification.validateDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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
import android.widget.Toast;

import com.example.villageplanner.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public class CreateReminder extends AppCompatActivity {
    Spinner locations;
    Spinner remindMechanism;
    TextView arrivalTime;
    TextView arrivalDate;
    EditText description;
    EditText title;
    int hour, minute, year, month, day;
    Button submit;
    CoordinatorLayout snackLayout;
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
        month = LocalDate.now().getMonthValue();
        day = LocalDate.now().getDayOfMonth();
        year = LocalDate.now().getYear();
        arrivalDate.setText(String.format(Locale.getDefault(),
                "%02d/%02d/%04d", month, day, year));
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

        // Setup CoordinateLayout
        snackLayout = (CoordinatorLayout) findViewById(R.id.snacking);
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference =  database.getReference("Reminders");
        Reminder notify = createReminder();
        DatabaseReference userRef = reference.child(Integer.toString(notify.getUserId())).child(notify.getReminderId());
        Task<Void> result = userRef.setValue(notify);
        if(!result.isSuccessful()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Firebase Error: Could not add Reminder", Toast.LENGTH_LONG);
            toast.show();
        } else {
//            Intent in = new Intent(CreateReminder.this, ReminderViewer.class);
//            in.putExtra("Reminder", in);
        }
        Intent in = new Intent(CreateReminder.this, ReminderViewer.class);
        in.putExtra("Reminder", in);

    }

    private boolean verifyData() {
        String dateError = validateDate(year, month, day, hour, minute);
        if(!dateError.isEmpty()) {
            printError(dateError);
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
        String des = description.getText().toString();
        String titular = title.getText().toString();
        int userId = 4;
        UUID reminderId = UUID.randomUUID();
        Reminder remind = new Reminder(location, titular, time, des, reminderId.toString());
        return remind;
    }

    private void printError(String text) {
        Snackbar snack = Snackbar.make(snackLayout, text, Snackbar.LENGTH_LONG);
        snack.show();
    }
}