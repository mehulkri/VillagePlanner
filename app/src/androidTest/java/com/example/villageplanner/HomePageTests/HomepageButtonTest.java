package com.example.villageplanner.HomePageTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.BundleMatchers.hasKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;

import static org.hamcrest.CoreMatchers.not;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomepageButtonTest {
    @Rule
    public IntentsTestRule<HomepageActivity> intentsTestRule =
            new IntentsTestRule<>(HomepageActivity.class);


    @Test
    public void ReminderButtonGoesToReminders() {
        onView(ViewMatchers.withId(R.id.reminder)).perform(click());
        intended(toPackage("com.example.villageplanner"));
    }

}