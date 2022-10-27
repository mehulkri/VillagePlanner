package com.example.villageplanner.ReminderLogic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reminder implements Serializable {

    private String location;
    private String title;
    private String description;
    private LocalDateTime targetTime;
    private int userId;
    private String reminderId;
    private LocalDateTime remindTime;

    Reminder(String loc, String header, LocalDateTime time) {
        setLocation(loc);
        setTitle(header);
        setTargetTime(time);
        setDescription(null);
    }

    Reminder(String loc, String header, LocalDateTime time, String des, String rId) {
        setLocation(loc);
        setTitle(header);
        setTargetTime(time);
        setDescription(des);
        reminderId = rId;
        remindTime = time.minusMinutes(10);
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

    public String getTargetTime() {
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("H:m a");
        String output = targetTime.format(formats);
        return output;
    }

    public String getLeaveTime() {
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("H:m a");
        String output = remindTime.format(formats);
        return output;
    }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public void setTargetTime(LocalDateTime targetTime) {
        this.targetTime = targetTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getReminderId() {
        return reminderId;
    }
}
