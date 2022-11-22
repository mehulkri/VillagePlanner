package com.example.villageplanner.GoogleMapTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

import android.os.Build;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.R;
import com.example.villageplanner.ui.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class RoutingTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);

    private void allowPermission()  {
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
            UiObject allowPermissions = mDevice.findObject(new UiSelector().clickable(true).checkable(false).index(1));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                } catch (UiObjectNotFoundException e) {
                }
            }
        }
    }

    @Test
    public void displayRouteTest() throws UiObjectNotFoundException {
        onView(withId(R.id.failButton)).perform(click());
        allowPermission();
        onView(withId(R.id.store)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CAVA"))).perform(click());
        onView(withId(R.id.display_route)).perform(click());
        onView(withId(R.id.display_route)).perform(click());
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.swipe(300,300,200,200,10);
        UiObject markerOne = device.findObject(new UiSelector()
                .descriptionContains("Google Map")
                .childSelector(new UiSelector().instance(1))
        );
        markerOne.waitForExists(10000);
        markerOne.click();
    }
}