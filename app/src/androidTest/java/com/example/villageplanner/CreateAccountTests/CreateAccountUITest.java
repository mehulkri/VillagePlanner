package com.example.villageplanner.CreateAccountTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.example.villageplanner.CreateAccountTests.ErrorMessageMatcher.withError;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import android.content.res.Resources;

import com.example.villageplanner.R;

import com.example.villageplanner.createAccount.CreateAccount;
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
public class CreateAccountUITest {

    private static String pass = "Tatyanaxv6#7^10";
    private static String email = "mehul.krishna@wsu.edu";
    @Rule
    public ActivityScenarioRule<CreateAccount> activityRule =
            new ActivityScenarioRule<>(CreateAccount.class);

    @Test
    public void enterOnlyNameAndGivesError() {
       onView(withId(R.id.fullName)).perform(typeText("Mehul Krishna"),  closeSoftKeyboard());
       onView(withId(R.id.nextButton)).perform(click());
       onView(withId(R.id.emailField)).check(matches(withError("Need to input an email")));
       onView(withId(R.id.passOne)).check(matches(withError("Need to input a password.")));
       onView(withId(R.id.passTwo)).check(matches(withError("Need to confirm the password")));
    }

    @Test
    public void validInputsLeadToSucess() {
        onView(withId(R.id.fullName)).perform(typeText("Mehul Krishna"), closeSoftKeyboard());
        onView(withId(R.id.emailField)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.passOne)).perform(typeText(pass), closeSoftKeyboard());
        onView(withId(R.id.passTwo)).perform(typeText(pass), closeSoftKeyboard());
        onView(withId(R.id.nextButton)).perform(click());
        onView(withId(R.id.image_picker)).check(matches(isDisplayed()));
    }

    @After
    public void afterTest() {
         FirebaseAuth mAuth = FirebaseAuth.getInstance();
         FirebaseUser currentUser = mAuth.getCurrentUser();
         if(currentUser != null) {
             if(currentUser.getEmail().equals(email)) {
                 // Delete account
                 currentUser.delete();

             }
         } else {
             Task<AuthResult> result = mAuth.signInWithEmailAndPassword(email, pass);
             if (result.isSuccessful()) {
                 currentUser = mAuth.getCurrentUser();
                 // Delete account
                 currentUser.delete();
             }
         }
    }

}
