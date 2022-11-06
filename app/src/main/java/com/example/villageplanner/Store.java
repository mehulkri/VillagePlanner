package com.example.villageplanner;

import java.util.Objects;

public class Store {
    private final String name;
    // openingTimes[day #] = hour at which the store opens
    // closingTimes[day #] = hour at which the store closes
    // e.g. openingTimes[1] = 8.25 means store opens 8:15am on Monday
    // latitude and longitude describe physical location on globe
    private final double openingTime, closingTime, latitude, longitude;

    public Store(String id) {
        name = id;
        // ToDo: Pull from a JSON/Firebase
        if (Objects.equals(id, "CAVA")) {
            openingTime = 8;
            closingTime = 23;
            latitude = 34.025808154311036;
            longitude = -118.28539312174385;
        }
        else {
            openingTime = 0;
            closingTime = 0;
            latitude = 0;
            longitude = 0;
        }
    }

    public String getName() {
        return name;
    }
    public double getOpeningTime() {
        return openingTime;
    }
    public double getClosingTime() {
        return closingTime;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    double queue_time(double time){ // returned in minutes
        if(time > closingTime || openingTime > time){
            return -1; // store closed
        }
        else{
            // ToDo: make this not constant
            return 15;
        }
    }
}
