package com.example.villageplanner.ReminderLogic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.villageplanner.R;

import org.w3c.dom.Text;

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
        target = (TextView) findViewById(R.id.target);
        leave = (TextView) findViewById(R.id.leave);
        date = (TextView) findViewById(R.id.dateStuff);
        place = (TextView) findViewById(R.id.place);
        description = (TextView) findViewById(R.id.descrip);
        pic = (ImageView) findViewById(R.id.storePic);

        // Button
        nextPage = (Button) findViewById(R.id.backToReminders);

        // Set the text view texts
        title.setText("Title: " + remind.getTitle());
        target.setText("Target Time: " +  remind.getTargetTime());
        leave.setText("Leave Time: " + remind.getLeaveTime());
        String dates = String.format(Locale.getDefault(), "%02d/%02d/%04d", time.getMonthValue(),
                time.getDayOfMonth(), time.getYear());
        date.setText("Date: " + dates);
        description.setText("Description: " + remind.getDescription());

        // Set image
        pic.setImageResource(getPicture(remind.getLocation()));


    }


    private int getPicture(String store) {
        switch (store) {
            case "Village Gym":
                return R.drawable.villagegym;
            default:
                return R.drawable.cava;
        }
    }
}