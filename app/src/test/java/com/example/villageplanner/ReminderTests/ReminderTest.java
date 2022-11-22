package com.example.villageplanner.ReminderTests;

import static org.junit.Assert.assertEquals;

import android.location.Location;

import com.example.villageplanner.ReminderLogic.Reminder;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReminderTest {

    @Test
    public void testReminderGetters() {
        LocalDateTime time = LocalDateTime.now();
        Reminder r = new Reminder("Cava", "I am ready to have a good meal",
                time, "This is the time when I have dinner with my friends",
                "sjfjlks", "54");
        Location l = Mockito.mock(Location.class);
        r.setLastKnownLocation(l);
        assertEquals(r.getLastKnownLocation(), l);
        assertEquals(r.getTitle(),"I am ready to have a good meal" );
        assertEquals(r.getDescription(),
                "This is the time when I have dinner with my friends");
        DateTimeFormatter formats = DateTimeFormatter.ofPattern("HH:mm a");
        String output = time.format(formats);
        assertEquals(r.getTargetTimeString(), output);
    }
}
