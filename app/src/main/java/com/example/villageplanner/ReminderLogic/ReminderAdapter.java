package com.example.villageplanner.ReminderLogic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageplanner.R;

import java.util.List;

import co.dift.ui.SwipeToAction;

public class ReminderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Reminder> items;


    /** References to the views for each data item **/
    public class RemindViewHolder extends SwipeToAction.ViewHolder<Reminder> {
        public TextView titleView;
        public TextView location;
        public TextView time;
        public ImageView icon;

        public RemindViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
            location = (TextView) v.findViewById(R.id.author);
            time = (TextView) v.findViewById(R.id.remind_time);
            icon = (ImageView) v.findViewById(R.id.like_or_love);
        }
    }

    /** Constructor **/
    public ReminderAdapter(List<Reminder> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @VisibleForTesting
    public void addReminder(Reminder notify) {
        items.add(notify);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_item, parent, false);

        return new RemindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Reminder item = items.get(position);
        RemindViewHolder vh = (RemindViewHolder) holder;
        vh.titleView.setText(item.getTitle());
        vh.location.setText(item.getLocation());
        vh.time.setText(item.getTargetTimeString());
        vh.data = item;
        if(item.isThisLiked()) {
            vh.icon.setVisibility(View.VISIBLE);
        }

    }

}
