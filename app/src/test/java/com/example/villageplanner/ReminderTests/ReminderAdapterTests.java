package com.example.villageplanner.ReminderTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.recyclerview.widget.RecyclerView;

import com.example.villageplanner.ReminderLogic.Reminder;
import com.example.villageplanner.ReminderLogic.ReminderAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class ReminderAdapterTests {
    private ReminderAdapter adapter;
    private ArrayList<Reminder> reminders;

    @Before
    public void setAdapter() {
        reminders = new ArrayList<>();
        adapter = new ReminderAdapter(reminders);
    }
    @Test
    public void testAddReminder() {
        Reminder r = Mockito.mock(Reminder.class);
        assertEquals(adapter.getItemCount(), 0);
        adapter.addReminder(r);
        assertEquals(adapter.getItemCount(), 1);
        assertEquals(adapter.getItemViewType(50), 0);
    }

}
