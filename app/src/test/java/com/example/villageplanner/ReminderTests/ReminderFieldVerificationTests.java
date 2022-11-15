package com.example.villageplanner.ReminderTests;

import static com.example.villageplanner.ReminderLogic.ReminderFieldVerification.validateDate;
import static com.example.villageplanner.ReminderLogic.ReminderFieldVerification.validateHoursOfOperation;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDateTime;


public class ReminderFieldVerificationTests {
    @Test
    public void testValidateDateInvalid() {
          // Valid date
          String error = "";
          assertEquals(validateDate(2023, 7, 29, 19, 30), error);
          // Invalid month
          error = "Please choose a valid month. \n";
          assertEquals(validateDate(2023, 24, 29, 19, 30), error);
          // Invalid year
          error = "Invalid year. Please choose this year or a later year. \n";
          assertEquals(validateDate(2020, 7, 29, 19, 30), error);
          // Invalid month
          error = "This month has already passed. Please choose this current month, or a month in the future \n";
          assertEquals(validateDate(2022, 7, 29, 19, 30), error);
         //  Invalid day
         error = "This date has already passed. Please choose today or a future date \n";
         assertEquals(validateDate(2022, LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth() - 3, 19, 30), error);
         // Invalid Hour
         error = "This time has already passed. Please choose a future time \n";
        assertEquals(validateDate(2022, LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour() - 1, 30), error);
         // Invalid minute
        assertEquals(validateDate(2022, LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute() - 4), error);
    }

    @Test
    public void testValidateHoursOfOperation() {
        // Valid time
        assertEquals("CAVA is not open at this hour. Please choose a different time.",
                validateHoursOfOperation(23, 30, "CAVA") );
        // Invalid time
        assertEquals("",
                validateHoursOfOperation(12, 30, "CAVA") );
    }
}
