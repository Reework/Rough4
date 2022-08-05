package com.shamrock.reework.activity.lifestyleplanmodule;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamrock.R;

import java.util.List;

public class NomalDayPlanAdapter extends RecyclerView.Adapter<NomalDayPlanAdapter.ViewHolder> {
    private List<Plan> data;
    Context mcontext;
    OnLifeStyleEdit  onLifeStyleEdit;
    public NomalDayPlanAdapter(List<Plan> dataList,Context context) {
        data = dataList;
        onLifeStyleEdit= (OnLifeStyleEdit) context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_normaday_plan,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
//            if (position==0){
//                holder.relain.setBackgroundColor(Color.parseColor("#ffb5e8"));
//            }
//            if (position==1){
//                holder.relain.setBackgroundColor(Color.parseColor("#85e3ff"));
//            }
//            if (position==2){
//                holder.relain.setBackgroundColor(Color.parseColor("#e7ffac"));
//            }
//            if (position==3){
//                holder.relain.setBackgroundColor(Color.parseColor("#97a2ff"));
//            }
//            if (position==4){
//                holder.relain.setBackgroundColor(Color.parseColor("#BFFCC6"));
//            }
//            if (position==5){
//                holder.relain.setBackgroundColor(Color.parseColor("#ffb5e8"));
//            }
//            if (position==6){
//                holder.relain.setBackgroundColor(Color.parseColor("#85e3ff"));
//            }
//            if (position==7){
//                holder.relain.setBackgroundColor(Color.parseColor("#e7ffac"));
//            }
//            if (position==8){
//                holder.relain.setBackgroundColor(Color.parseColor("#97a2ff"));
//            }
//            if (position==9){
//                holder.relain.setBackgroundColor(Color.parseColor("#bfccc6"));
//            }

            holder.activity_tv.setText(data.get(position).getActivityName().toString());
            holder.time_tv.setText("Start Time - "+data.get(position).getActivityTime().toString());
            holder.txt_duration.setText("Total Time - "+formatHoursAndMinutesMin(Integer.parseInt(data.get(position).getDurationInMinute().toString().trim())));
        }catch (Exception ex)
        {

        }

    }
    public  String formatHoursAndMinutesMin(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" Hr ";
        }else {
            strhours=" Hr ";

        }


        String submittime="";
        submittime= (totalMinutes / 60) + strhours + minutes+" Min";




//        if (hoursstr>0){
//
//        }else {
//
//            submittime=  minutes+" Mins";
//
//        }


        String newSubmit="";
        if (totalMinutes<60){
            newSubmit=submittime.replace("0 h ","");
        }else {
            newSubmit=submittime;
        }
        return newSubmit;


    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time_tv,activity_tv,duration_tv;
        TextView img_edit_plan,txt_duration;
        RelativeLayout relain;
        TextView img_delete_plan;
        public ViewHolder(View itemView) {
            super(itemView);
            time_tv=(TextView)itemView.findViewById(R.id.time_tv);
            activity_tv=(TextView)itemView.findViewById(R.id.activity_tv);
            duration_tv=(TextView)itemView.findViewById(R.id.duration_tv);
            img_edit_plan=itemView.findViewById(R.id.img_edit_plan);
            img_delete_plan=itemView.findViewById(R.id.img_delete_plan);
            txt_duration=itemView.findViewById(R.id.txt_duration);
            relain=itemView.findViewById(R.id.relain);
            img_edit_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLifeStyleEdit.getEditData(data.get(getAdapterPosition()));
                }
            });

            img_delete_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLifeStyleEdit.getDeleteData(Integer.parseInt(data.get(getAdapterPosition()).getId()));


                }
            });
        }
    }
}

