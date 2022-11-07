package com.example.villageplanner.ReminderLogic;

import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.removeReminderFromDatabase;
import static com.example.villageplanner.helperAPI.StorePictureHelper.getPicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.villageplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.Locale;

public class ReminderViewer extends AppCompatActivity {
    TextView title;
    TextView target;
    TextView leave;
    TextView place;
    TextView date;
    TextView description;
    Button nextPage;
    ImageView pic;
    FloatingActionButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_viewer);

        // Get reminder
        Reminder remind = (Reminder) getIntent().getSerializableExtra("Reminder");

        // Time Stuff
        LocalDateTime time = remind.getRemindTime();

        // Initialize text view
        title = (TextView) findViewById(R.id.remindTItleReal);
        target = (TextView) findViewById(R.id.targetTwo);
        leave = (TextView) findViewById(R.id.leaveTwo);
        date = (TextView) findViewById(R.id.dateStuff);
        place = (TextView) findViewById(R.id.place);
        description = (TextView) findViewById(R.id.descrip);
        pic = (ImageView) findViewById(R.id.storePic);

        // Button
        nextPage = (Button) findViewById(R.id.backToRemindersTwo);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ReminderViewer.this, ReminderPage.class);
                startActivity(in);
            }
        });

        edit = (FloatingActionButton) findViewById(R.id.editReminder);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ReminderViewer.this, CreateReminder.class);
                in.putExtra("Reminder", remind);
                try {
                    removeReminderFromDatabase(remind);
                } catch (Exception e) {

                }
                startActivity(in);
            }
        });

        // Set the text view texts
        if(remind != null) {
            title.setText("Title: " + remind.getTitle());
            target.setText("Target Time: " +  remind.getTargetTimeString());
            leave.setText("Leave Time: " + remind.getLeaveTime());
            String dates = String.format(Locale.getDefault(), "%02d/%02d/%04d", time.getMonthValue(),
                    time.getDayOfMonth(), time.getYear());
            date.setText("Date: " + dates);
            description.setText("Description: " + remind.getDescription());
            place.setText("Place: " + remind.getLocation());

            // Set image
            pic.setImageResource(getPicture(remind.getLocation()));
        } else {

        }

    }

}