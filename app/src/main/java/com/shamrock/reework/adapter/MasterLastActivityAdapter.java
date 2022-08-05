package com.shamrock.reework.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.model.MasterLastActivityModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MasterLastActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<MasterLastActivityModel> dataSet;
    Context context;

    public MasterLastActivityAdapter(Context context, ArrayList<MasterLastActivityModel> dataSet) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout_activity, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_master_last_activity, parent, false);
//        view.setOnClickListener(MainActivity.myOnClickListener);
            return new MyViewHolder(view);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).textView_Date.setText("Date");
            ((HeaderViewHolder) holder).textView_Calories.setText("Calories Burn");
        } else if (holder instanceof MyViewHolder) {
            MasterLastActivityModel model = dataSet.get(listPosition);
            int activityType = model.getCalories();
            String date = formatDates(model.getDate());
            ((MyViewHolder) holder).textView_Name.setText(date);
            ((MyViewHolder) holder).textView_Calories.setText("" + activityType);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView_Name, textView_Calories;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView_Name = itemView.findViewById(R.id.textView_activity_last_date);
            this.textView_Calories = itemView.findViewById(R.id.textView_activity_last_calories);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textView_Date, textView_Duration, textView_Calories;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.textView_Date = itemView.findViewById(R.id.text_header_title);
            this.textView_Duration = itemView.findViewById(R.id.text_header_duration);
            this.textView_Calories = itemView.findViewById(R.id.text_header_calories);
            this.textView_Duration.setVisibility(View.GONE);
        }
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
}
