package com.example.villageplanner.HomePageTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReminderButtonTest {
    @Rule
    public IntentsTestRule<HomepageActivity> intentsTestRule =
            new IntentsTestRule<>(HomepageActivity.class);
    @Test
    public void ReminderButtonGoesToReminders() {
//        onView(ViewMatchers.withId(R.id.failButton))
//                .perform(click());

        Intents.init();
        onView(ViewMatchers.withId(R.id.reminder)).perform(click());
        Intents.release();
    }

}