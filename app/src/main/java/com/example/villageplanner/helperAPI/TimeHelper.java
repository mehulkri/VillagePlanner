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
}
