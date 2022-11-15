package com.example.villageplanner.HelperAPITests;

import static com.example.villageplanner.helperAPI.TimeHelper.getReminderMilli;
import static com.example.villageplanner.helperAPI.TimeHelper.isExpired;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.villageplanner.ReminderLogic.Reminder;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class TimerHelperTest {
    @Test
    public void testGetMillis() {
        LocalDateTime aDateTime = LocalDateTime.of(2015, Month.JULY, 29, 0, 1, 40);
        Reminder r = new Reminder("Cava", "Eating at Cava", aDateTime,
                "A wonderful dinner.", "jkhkljs", "sjkdsjkljkl");
        long expected = 143815330000L;
        assertTrue("Reminders Equal: ", expected < getReminderMilli(r));
        Reminder q = null;
        assertEquals(0, getReminderMilli(q));
    }

    @Test
    public void testIsExpired() {
        LocalDateTime aDateTime = LocalDateTime.of(2015, Month.JULY, 29, 0, 1, 40);
        LocalDateTime bDateTime = LocalDateTime.of(2023, Month.JULY, 29, 0, 1, 40);
        Reminder r = new Reminder("Cava", "Eating at Cava", aDateTime,
                "A wonderful dinner.", "jkhkljs", "sjkdsjkljkl");
        Reminder w = new Reminder("Cava", "Eating at Cava", bDateTime,
                "A wonderful dinner.", "jkhkljs", "sjkdsjkljkl");
        // Expired time: True
        assertTrue(isExpired(r));
        // Non-expired: False
        assertTrue(!isExpired(w));
        // Null object
        assertTrue(!isExpired(null));
    }
}
