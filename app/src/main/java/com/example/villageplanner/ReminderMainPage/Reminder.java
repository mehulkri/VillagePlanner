package com.example.villageplanner.ReminderMainPage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reminder {

    private String location;
    private String title;
    private String description;
    private LocalDateTime remindTime;
    private int userId;

    Reminder(String loc, String header, LocalDateTime time) {
        setLocation(loc);
        setTitle(header);
        setRemindTime(time);
        setDescription(null);
    }

    Reminder(String loc, String header, LocalDateTime time, String des) {
        setLocation(loc);
        setTitle(header);
        setRemindTime(time);
        setDescription(des);
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

    public String getRemindTime() {
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("H:m a");
        String output = remindTime.format(formats);
        return output;
    }

    public void setRemindTime(LocalDateTime remindTime) {
        this.remindTime = remindTime;
    }
}
