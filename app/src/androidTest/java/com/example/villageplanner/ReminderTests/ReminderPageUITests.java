package com.example.villageplanner.ReminderTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static com.example.villageplanner.ReminderTests.RecyclerMatcher.numReminders;

import static org.hamcrest.Matchers.allOf;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.UiDevice;

import com.example.villageplanner.R;
import com.example.villageplanner.ReminderLogic.ReminderPage;
import com.example.villageplanner.createAccount.CreateAccount;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReminderPageUITests {
    @Rule
    public ActivityScenarioRule<ReminderPage> activityRule =
            new ActivityScenarioRule<ReminderPage>(ReminderPage.class);

    @Test
    public void testDeleteReminder() {
//        onView(withId(R.id.recyclers)).perform(
//                RecyclerViewActions.actionOnItemAtPosition(50, new GeneralSwipeAction(
//                        Swipe.SLOW, GeneralLocation.BOTTOM_RIGHT, GeneralLocation.BOTTOM_LEFT,
//                        Press.FINGER)));
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.swipe(900,300,400,300,10);
        onView(withId(android.R.id.content)).check(matches(isDisplayed()));
    }

    @Test
    public void testLikeReminder() {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.swipe(400,300,500,300,10);
        onView(withId(android.R.id.content)).check(matches(isDisplayed()));
    }

    @Test
    public void testWhenReminderClickedGoToRemiderViewPage() throws InterruptedException {
        onView(withId(R.id.recyclers)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,  click()));

        // Can I click a button that appears in the reminder view page?
        onView(withId(R.id.editReminder)).perform(click());
    }

    @Test
    public void testOneCanEditReminder() {
        onView(withId(R.id.recyclers)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,  click()));
        onView(withId(R.id.view_reminder)).check(matches(isDisplayed()));
        onView(withId(R.id.editReminder)).perform(click());
        onView(withId(R.id.create_the_reminder)).check(matches(isDisplayed()));
    }

    @Test
    public void testNoEmptyReminder() {
        onView(withId(R.id.add_remind)).perform(click());
        onView(withId(R.id.submitReminder)).perform(click());
        onView(withId(R.id.submitReminder)).check(matches(isDisplayed()));
    }

    @Test
    public void testNoEditIntoEmpty() {
        onView(withId(R.id.recyclers)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,  click()));
        onView(withId(R.id.editReminder)).perform(click());
        onView(withId(R.id.reminderTitle)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitReminder)).perform(click());
        onView(withId(R.id.submitReminder)).check(matches(isDisplayed()));
    }
}
