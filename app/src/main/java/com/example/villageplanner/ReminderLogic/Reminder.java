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

public class Reminder implements Serializable {

    private String location;
    private String title;
    private String description;
    private LocalDateTime targetTime;
    private String userId;
    private String reminderId;
    private LocalDateTime remindTime;
    private Location lastKnownLocation;


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

    private void calculateReminderTime() {
        Store location = new Store(this.location);

    }

    public void writeToJSONFile(Context context) {
        String userString = reminderToJsonString();
        File file = new File(context.getFilesDir(), "reminders.json");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException e) {

        }
    }

    private String reminderToJsonString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userId);
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("reminderId", reminderId);
            jsonObject.put("targetTime", timeToJSON(targetTime));
            jsonObject.put("remindTime", timeToJSON(remindTime));
            return jsonObject.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private JSONObject timeToJSON(LocalDateTime time) {
        JSONObject jsonObject = new JSONObject();
        int year = time.getYear();
        int month = time.getMonthValue();
        int day = time.getDayOfMonth();
        int hour = time.getHour();
        int minute = time.getMinute();
        try {
            jsonObject.put("year", year);
            jsonObject.put("month", month);
            jsonObject.put("day", day);
            jsonObject.put("hour", hour);
            jsonObject.put("minute", minute);
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
}
