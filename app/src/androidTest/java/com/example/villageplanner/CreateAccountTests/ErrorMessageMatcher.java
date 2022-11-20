package com.example.villageplanner.CreateAccountTests;

import android.view.View;

import androidx.test.espresso.matcher.BoundedMatcher;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.hamcrest.Description;

public class ErrorMessageMatcher extends BoundedMatcher<View, MaterialEditText> {

    static ErrorMessageMatcher withError(String error) {
        return new ErrorMessageMatcher(error);
    }

    private final String message;

    private ErrorMessageMatcher(String m) {
        super(MaterialEditText.class);
        message = m;
    }

    @Override
    protected boolean matchesSafely(MaterialEditText item) {
        return item.getHelperText() == message;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with hint text color:");
    }
}
