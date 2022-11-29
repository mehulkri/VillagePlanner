package com.example.villageplanner.helperAPI;

import static com.example.villageplanner.ReminderLogic.FirebaseReminderUpdater.removeReminderFromDatabase;
import static com.example.villageplanner.helperAPI.TimeHelper.isExpired;

import com.example.villageplanner.ReminderLogic.Reminder;

import java.util.List;

public class ReminderHelper {
    private ReminderHelper() {}

    // Returns true reminder is not expired and false if it is expired (and removes from database)
    private static boolean checkExpirationAndRemove(Reminder remind) {
        if(isExpired(remind)) {
            try {
                removeReminderFromDatabase(remind);
                return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private static boolean isInReminders(Reminder notify, List<Reminder> reminders) {
        for(Reminder r: reminders) {
            if(r.getTitle().equals(notify.getTitle()) &&
                    r.getLocation().equals(notify.getLocation())) {
                return true;
            }
        }
        return false;
    }

    public static boolean reminderReadyToBeDisplayed(Reminder remind, List<Reminder> reminders) {
        return isInReminders(remind, reminders) & checkExpirationAndRemove(remind);
    }
}
