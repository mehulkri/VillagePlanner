package com.example.villageplanner.ReminderTests;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.example.villageplanner.ReminderLogic.Reminder;

import org.hamcrest.Matcher;

import java.time.LocalDateTime;

public final class EmptyReminderTest implements ViewAction {
    public EmptyReminderTest() {

    }

    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(RecyclerView.class));
    }

    @Override
    public String getDescription() {
        return "Adding an empty reminder";
    }

    @Override
    public void perform(UiController uiController, View view) {
        LocalDateTime time = LocalDateTime.of(2023, 5, 2, 12, 40);
        Reminder notify = new Reminder("Cava", "I want to eat today", time,
                "I am ready to have an amazing lunch", "dlklskfd", "4" );
     //   ((ReminderAdapter) ((RecyclerView) view).getAdapter()).addReminder(notify);
    }
}
