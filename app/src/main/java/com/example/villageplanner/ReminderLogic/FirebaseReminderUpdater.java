package com.example.villageplanner.ReminderLogic;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseReminderUpdater {
    private FirebaseReminderUpdater() {
    }

    public static boolean addReminderToDatabase(Reminder notify) {
        Task<Void> result = getDatabaseReference(notify).setValue(notify);
        if(!result.isSuccessful() && result.getException() == null) {
            return true;
        } else {
            return result.isSuccessful();
        }
    }

    public static void removeReminderFromDatabase(Reminder notify) throws Exception{
        Task<Void> result = getDatabaseReference(notify).removeValue();
        if(result.getException() != null) {
            throw result.getException();
        }
    }


    public static String getUserId() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            return currentUser.getUid();
        } else {
            return "4";
        }
    }

    public static Reminder dataSnapshotToReminder(DataSnapshot data) {
        HashMap<String, Object> result = (HashMap<String, Object>) data.getValue();
        String title = (String) result.get("title");
        String descrip = (String) result.get("description");
        String loc = (String) result.get("location");
        String userId = (String) result.get("userId");
        String reminderId = (String) result.get("reminderId");
        HashMap<String, Object> time = (HashMap<String, Object>) result.get("targetTime");
        LocalDateTime targetTime = hashMapToLocalDateTime(time);
        Reminder newReminder = new Reminder(loc, title, targetTime, descrip, reminderId, userId);
        return newReminder;
    }

    private static DatabaseReference getDatabaseReference(Reminder notify) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference =  database.getReference("Reminders");
        DatabaseReference userRef = reference.child(notify.getUserId()).child(notify.getReminderId());
        return userRef;
    }

    private static LocalDateTime hashMapToLocalDateTime(HashMap<String, Object> time) {
        int year = ((Long) time.get("year")).intValue();
        int day = ((Long) time.get("dayOfMonth")).intValue();
        int hour = ((Long) time.get("hour")).intValue();
        int minute = ((Long) time.get("minute")).intValue();
        int month = ((Long) time.get("monthValue")).intValue();
        LocalDateTime newTime = LocalDateTime.of(year, month,
              day, hour, minute);
        return newTime;
    }


}


