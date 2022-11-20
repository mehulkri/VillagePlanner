package com.example.villageplanner.ReminderTests;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.hamcrest.Description;

public class RecyclerMatcher extends BoundedMatcher<View, RecyclerView> {

    static RecyclerMatcher numReminders() {
        return new RecyclerMatcher();
    }


    private RecyclerMatcher() {
        super(RecyclerView.class);
    }

    @Override
    protected boolean matchesSafely(RecyclerView item) {
        return item.getAdapter().getItemCount() == 0 ;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Find the reminders");
    }
}
