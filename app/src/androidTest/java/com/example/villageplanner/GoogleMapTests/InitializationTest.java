package com.example.villageplanner.GoogleMapTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import android.os.Build;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.example.villageplanner.HomeLogic.HomepageActivity;
import com.example.villageplanner.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class InitializationTest {
    @Rule
    public ActivityScenarioRule<HomepageActivity> activityRule =
            new ActivityScenarioRule<HomepageActivity>(HomepageActivity.class);

//    private void allowPermissionsIfNeeded()  {
//        if (Build.VERSION.SDK_INT >= 23) {
//            UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
//            UiObject allowPermissions = mDevice.findObject(new UiSelector().text("While using the app"));
//            if (allowPermissions.exists()) {
//                try {
//                    allowPermissions.click();
//                } catch (UiObjectNotFoundException e) {
//                }
//            }
//        }
//    }
//
//    private void denyPermissionsIfNeeded()  {
//        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
//        UiObject allowPermissions = mDevice.findObject(new UiSelector().clickable(true).checkable(false).index(3));
//        if (allowPermissions.exists()) {
//            try {
//                allowPermissions.click();
//            } catch (UiObjectNotFoundException e) {
//                System.out.println("nO");
//            }
//        }
//        else System.out.println("No");
//    }

    @Test
    public void should_displayMap_when_permissionsAreGranted() {
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void should_displayNoPermission_when_permissionsAreDenied() {

    }

}