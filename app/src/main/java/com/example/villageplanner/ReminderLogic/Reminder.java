package com.example.villageplanner.ReminderLogic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Reminder implements Serializable {

    private String location;
    private String title;
    private String description;
    private LocalDateTime targetTime;
    private String userId;
    private String reminderId;
    private LocalDateTime remindTime;


    Reminder(String loc, String header, LocalDateTime time, String des, String rId, String uId) {
        setLocation(loc);
        setTitle(header);
        setTargetTime(time);
        setDescription(des);
        reminderId = rId;
        targetTime = time;
       // remindTime = time.minusMinutes(10);
        remindTime = targetTime;
        userId = uId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetTimeString() {
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("HH:mm a");
        String output = targetTime.format(formats);
        return output;
    }

    public String getLeaveTime() {
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("HH:mm a");
        String output = remindTime.format(formats);
        return output;
    }

    public LocalDateTime getTargetTime() { return targetTime; }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public void setTargetTime(LocalDateTime targetTime) {
        this.targetTime = targetTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getReminderId() {
        return reminderId;
    }
}
