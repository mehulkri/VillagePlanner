package com.example.villageplanner.ReminderLogic;

import android.location.Location;

import android.content.Context;

import com.example.villageplanner.Store;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Reminder implements Serializable, Comparable<Reminder> {

    private String location;
    private String title;
    private String description;
    private LocalDateTime targetTime;
    private String userId;
    private String reminderId;
    private LocalDateTime remindTime;
    private Location lastKnownLocation;
    private boolean isLiked;


    public Reminder(String loc, String header, LocalDateTime time, String des, String rId, String uId) {
        setLocation(loc);
        setTitle(header);
        setTargetTime(time);
        setDescription(des);
        reminderId = rId;
        targetTime = time;
        remindTime = calculateReminderTime();
        userId = uId;
        isLiked = false;
    }
    public Location getLastKnownLocation() {return lastKnownLocation;}

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
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
        return getTimeString(targetTime);
    }

    public String getLeaveTime() {
        return getTimeString(remindTime);
    }

    private String getTimeString(LocalDateTime time) {
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("HH:mm a");
        String output = time.format(formats);
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

    private LocalDateTime calculateReminderTime() {
        Store location = new Store(this.location);
        long queueTime = location.queueTime();
        return targetTime.minusMinutes(queueTime+10);
    }

    public void like() {
        isLiked = true;
    }

    public boolean isThisLiked() {
        return isLiked;
    }

    @Override
    public int compareTo(Reminder o) {
        LocalDateTime other = o.getTargetTime();
        if(targetTime.isAfter(other)) {
            return 1;
        } else if(targetTime.isEqual(other)) {
            return 0;
        } else {
            return -1;
        }
    }
}
