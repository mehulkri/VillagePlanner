package com.example.villageplanner.LoginTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import android.content.res.Resources;
import android.os.Build;

import com.example.villageplanner.R;

import com.example.villageplanner.createAccount.CreateAccount;
import com.example.villageplanner.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    private static String pass = "Test123!";
    private static String email = "josh@usc.edu";
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);

//    @Test
//    public void error() {
//        onView(withId(R.id.fullName)).perform(typeText("Mehul Krishna"),  closeSoftKeyboard());
//        onView(withId(R.id.nextButton)).perform(click());
//        onView(withId(R.id.emailField)).check(matches(withError("Need to input an email")));
//        onView(withId(R.id.passOne)).check(matches(withError("Need to input a password.")));
//        onView(withId(R.id.passTwo)).check(matches(withError("Need to confirm the password")));
//    }
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
    public void correct() throws InterruptedException {
        //Login with josh@usc.edu
            //a. Type in the correct views
            //b. Press login button (twice)
        onView(withId(R.id.username)).perform(typeText("jerry@seinfeld.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("jerry123!"), closeSoftKeyboard());
        //Use intent checker in HomepageButtonTest List 55.
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.login)).perform(click());

        onView(withId(R.id.username)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(pass), closeSoftKeyboard());
        //Use intent checker in HomepageButtonTest List 55.
        onView(withId(R.id.login)).perform(click());
        //onView(withId(R.id.login)).perform(click());
        wait(10000);
//        allowPermission();
    }

}
