package com.example.villageplanner.ReminderLogic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.villageplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;


public class ReminderPage extends AppCompatActivity {

    RecyclerView recyclerView;
    ReminderAdapter adapter;
    SwipeToAction swipeToAction;
    FloatingActionButton plus;

    List<Reminder> reminders = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_page);

        recyclerView = (RecyclerView) findViewById(R.id.recyclers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Set reminders
        populate();

        adapter = new ReminderAdapter(this.reminders);
        recyclerView.setAdapter(adapter);
        plus = (FloatingActionButton) findViewById(R.id.add_remind);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<Reminder>() {
            @Override
            public boolean swipeLeft(final Reminder itemData) {
                final int pos = removeReminder(itemData);
                displaySnackbar(itemData.getTitle() + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBook(pos, itemData);
                    }
                });
                return true;
            }

            @Override
            public boolean swipeRight(Reminder itemData) {
                displaySnackbar(itemData.getTitle() + " loved", null, null);
                return true;
            }

            @Override
            public void onClick(Reminder itemData) {
                displaySnackbar(itemData.getTitle() + " clicked", null, null);
            }

            @Override
            public void onLongClick(Reminder itemData) {
                displaySnackbar(itemData.getTitle() + " long clicked", null, null);
            }

        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(ReminderPage.this, CreateReminder.class);
                startActivity(next);
            }
        });
    }

    private void populate() {

        for(int i=0; i < 10; i++) {
            this.reminders.add(new Reminder("Cava", "Eat at Cava", LocalDateTime.now()));
        }

    }


    private int removeReminder(Reminder reminder) {
        int pos = reminders.indexOf(reminder);
        reminders.remove(reminder);
        adapter.notifyItemRemoved(pos);
        return pos;
    }

    private void addBook(int pos, Reminder book) {
        reminders.add(pos, book);
        adapter.notifyItemInserted(pos);
    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

//        View v = snack.getView();
//        v.setBackgroundColor(getResources().getColor(R.color.secondary));
//        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
//        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();
    }
}