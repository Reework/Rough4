package com.shamrock.reework.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.model.MasterActivityModel;

import java.util.ArrayList;

public class MasterTrackActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<MasterActivityModel> dataSet;
    Context context;

    public MasterTrackActivityAdapter(Context context, ArrayList<MasterActivityModel> dataSet) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_master_track_my_activity, parent, false);
//        view.setOnClickListener(MainActivity.myOnClickListener);
            return new MyViewHolder(view);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        if (holder instanceof HeaderViewHolder) {
        } else if (holder instanceof MyViewHolder) {
            MasterActivityModel model = dataSet.get(listPosition);
            int activityType = model.getActivityType();
            ((MyViewHolder) holder).textView_Name.setText(model.getActivityName());
            ((MyViewHolder) holder).textView_Duration.setText(model.getActivityDuration());
            ((MyViewHolder) holder).textView_Calories.setText("" + model.getCalories());
            switch (activityType) {
                case 101:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_walking);
                    break;
                case 102:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_running);
                    break;
                case 103:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_sports);
                    break;
                case 104:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_swimming);
                    break;
                case 105:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_gym);
                    break;
                case 106:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_yoga);
                    break;
                case 107:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_climbing_stairs);
                    break;
                case 108:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_dancing);
                    break;
                default:
                    ((MyViewHolder) holder).imageView.setImageResource(R.drawable.ic_activity_others);
            }
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
        ImageView imageView;
        TextView textView_Name, textView_Duration, textView_Calories;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView_Activity_Type);
            this.textView_Name = (TextView) itemView.findViewById(R.id.textView_activity_name);
            this.textView_Duration = (TextView) itemView.findViewById(R.id.textView_activity_duration);
            this.textView_Calories = (TextView) itemView.findViewById(R.id.textView_activity_calories);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
