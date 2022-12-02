package com.example.villageplanner.ReminderLogic;

import static com.example.villageplanner.helperAPI.TimeHelper.expiryStatus;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
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
        public RelativeLayout main;

        public RemindViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
            location = (TextView) v.findViewById(R.id.author);
            time = (TextView) v.findViewById(R.id.remind_time);
            icon = (ImageView) v.findViewById(R.id.like_or_love);
            main = (RelativeLayout) v.findViewById(R.id.summary);
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
        int status = expiryStatus(item);
        int[] colors = {Color.parseColor("#FB8C00"), Color.parseColor("#FF313A")};
        if(status >= 0) {
            int textColor = Color.WHITE;
            vh.time.setTextColor(textColor);
            vh.titleView.setTextColor(textColor);
            vh.location.setTextColor(textColor);
            vh.main.setBackgroundColor(colors[status]);
        }
    }
}
