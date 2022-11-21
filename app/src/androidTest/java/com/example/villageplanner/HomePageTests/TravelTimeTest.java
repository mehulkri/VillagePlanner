package com.example.villageplanner.HomePageTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.BundleMatchers.hasKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.R;
import com.example.villageplanner.ReminderLogic.ReminderPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TravelTimeTest {
    @Rule
    public ActivityScenarioRule<HomepageActivity> activityRule =
            new ActivityScenarioRule<HomepageActivity>(HomepageActivity.class);

    @Test
    public void RoutingDisplayTest() {
        onView(withId(R.id.store)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CAVA"))).perform(click());
        onView(withId(R.id.display_route)).perform(click());
        onView(withId(R.id.routingdisplay))
                .check(matches(withText(containsString("11 min"))));
    }

}