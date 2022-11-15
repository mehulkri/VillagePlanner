package com.example.villageplanner.CreateAccountTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import android.content.res.Resources;
import com.example.villageplanner.R;

import com.example.villageplanner.createAccount.CreateAccount;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateAccountUITest {
    @Rule
    public ActivityScenarioRule<CreateAccount> activityRule =
            new ActivityScenarioRule<>(CreateAccount.class);

    @Test
    public void listGoesOverTheFold() {
     //   onView(withId(R.id.C)).check(matches(isDisplayed()));
    }
}
