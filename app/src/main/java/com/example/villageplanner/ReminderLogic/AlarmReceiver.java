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
        Intent i = new Intent(context, ReminderViewer.class);
        String title = intent.getStringExtra("ReminderTitle");
        String description = intent.getStringExtra("ReminderDescription");
        Reminder notify = (Reminder) intent.getSerializableExtra("Reminder");
        if(title == null) {
            title = "";
        }
        if(description == null) {
            description = "";
        }
        if(notify == null) {
            notify = new Reminder("Cava", "Eat at Cava", LocalDateTime.now(), "This is the description", "efdsfdfds", "0");
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("Reminder", notify);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "village")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
