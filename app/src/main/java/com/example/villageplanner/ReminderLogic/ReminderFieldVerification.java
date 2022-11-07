package com.example.villageplanner.ReminderLogic;

import java.time.LocalDate;
import java.time.LocalTime;


public class ReminderFieldVerification {

    private ReminderFieldVerification() { }

    public static String validateDate(int year, int month, int day, int hour, int minute) {
        String error = "";
        int currYear = LocalDate.now().getYear();
        int currMonth = LocalDate.now().getMonthValue();
        int currDay = LocalDate.now().getDayOfMonth();
        int currHour = LocalTime.now().getHour();
        int currMin = LocalTime.now().getMinute();
        if(month < 1 || month > 12) {
            error += "Please choose a valid month. \n";
        }
        if(year < currYear) {
            error += "Invalid year. Please choose this year or a later year. \n";
        } else if(year == currYear) {
            if(month < currMonth) {
                error += "This month has already passed. Please choose this current month, or a month in the future \n";
            } else if(month == currMonth) {
                if(day < currDay) {
                    error += "This date has already passed. Please choose today or a future date \n";
                } else if(day == currDay) {
                    if(hour < currHour) {
                        error += "This time has already passed. Please choose a future time \n";
                    } else if(hour == currHour) {
                        if(minute < currMin) {
                            error += "This time has already passed. Please choose a future time \n";
                        }
                    }
                }
            }
        }
        return error;
    }

    public static String validateHoursOfOperation(int hour, String location) {
        String error = "";
        if(hour < 8 || hour > 22) {
            error += location + " is not open at this hour. Please choose a different time.";
        }
        return error;
    }
}
