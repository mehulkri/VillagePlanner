package com.example.villageplanner.ReminderLogic;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.example.villageplanner.R;
import com.example.villageplanner.ReminderLogic.ReminderViewer;

import java.time.LocalDateTime;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean alternateIntent = false;
        Intent i = new Intent(context, ReminderViewer.class);
        Intent a = new Intent(context, ReminderPage.class);
        String[] keys = {"ReminderTitle", "ReminderDescription", "TargetTime", "RemindTime", "LeaveTime", "Date", "Place"};
        for(String key : keys) {
            String result = intent.getStringExtra(key);
            if(result == null) {
                alternateIntent = true;
            }
            i.putExtra(key, result);
        }
        String title = intent.getStringExtra("ReminderTitle");
        String description = intent.getStringExtra("ReminderDescription");
        String location = intent.getStringExtra("Place");
        String notifTitle;
        if(location == null) {
            notifTitle = "Time to leave for the USC Village";
        } else {
            notifTitle = "Time to leave for " + location;
        }
        if(title == null) {
            title = "";
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent;
        if(alternateIntent) {
            pendingIntent = PendingIntent.getActivity(context, 0, a, 0);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "village")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(notifTitle)
                .setContentText(title)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
