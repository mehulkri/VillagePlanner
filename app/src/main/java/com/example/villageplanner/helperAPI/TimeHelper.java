package com.example.villageplanner.helperAPI;

import com.example.villageplanner.ReminderLogic.Reminder;

import java.time.LocalDateTime;
import java.util.Calendar;

public class TimeHelper {
    private TimeHelper() { }

    private static Calendar getRemindCalendar(Reminder notify) {
        Calendar calendar = Calendar.getInstance();
        LocalDateTime remindTime = notify.getRemindTime();
        calendar.set(remindTime.getYear(), remindTime.getMonthValue()-1,
                remindTime.getDayOfMonth(), remindTime.getHour(), remindTime.getMinute(),
                remindTime.getSecond());
        return calendar;
    }

    public static long getReminderMilli(Reminder notify) {
        if(notify == null) {
            return 0;
        } else{
            Calendar calendar = getRemindCalendar(notify);
            return calendar.getTimeInMillis();
        }
    }

    // Returns true reminder has expired
    public static boolean isExpired(Reminder notify) {
        if(notify != null) {
            LocalDateTime target = notify.getTargetTime().plusMinutes(10);
            LocalDateTime nowIsh = LocalDateTime.now();
            return nowIsh.isAfter(target);
        } else {
            return false;
        }
    }

    // -1: Not expired, 0: Remind time has pass, but target time has not passed, 1: Expired
    // (target time has passed)
    public static int expiryStatus(Reminder notify) {
        if(isExpired(notify)) {
            return 1;
        } else {
            LocalDateTime nowIsh = LocalDateTime.now();
            LocalDateTime remind = notify.getRemindTime();
            if(nowIsh.isAfter(remind)) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
