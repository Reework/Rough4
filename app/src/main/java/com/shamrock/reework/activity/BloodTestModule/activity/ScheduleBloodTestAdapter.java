package com.shamrock.reework.activity.BloodTestModule.activity;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleBloodTestAdapter extends RecyclerView.Adapter<ScheduleBloodTestAdapter.MyHolder>
{
    Context context;
    ArrayList<GetBloodTestReportRes.DataList> list;


    public ScheduleBloodTestAdapter(Context context, ArrayList<GetBloodTestReportRes.DataList> list)
    {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView tvActivity, tvTime, tvFrequency,textAppointmentDate,text_AppointmentName;
        ImageView  ivActivity;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvActivity = itemView.findViewById(R.id.tvActivity);
            text_AppointmentName = itemView.findViewById(R.id.text_AppointmentName);
            textAppointmentDate = itemView.findViewById(R.id.textAppointmentDate);
            tvTime = itemView.findViewById(R.id.textAppointmentTime);
            tvFrequency = itemView.findViewById(R.id.tvFrequency);
            ivActivity = itemView.findViewById(R.id.imageView_MyReminder_icon);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_blood_report_history, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        try {
            GetBloodTestReportRes.DataList model = list.get(i);

            String strDate = model.getScheduledate();
            if(strDate!=null) {
                if (!TextUtils.isEmpty(model.getScheduledate())) {
                    String date = formatDates(strDate);
                    myHolder.textAppointmentDate.setText(date);
                }
            }

            String strTime = model.getScheduletime();
            if(strTime!=null) {
                if (!TextUtils.isEmpty(strTime)) {
                    String time = formatTime(strTime);

                    if (!TextUtils.isEmpty(time))
                        myHolder.tvTime.setText(time);
                }
            }

            if(model.getHasReportGenerated()){
                myHolder.text_AppointmentName.setText("Test Completed");
            }else{
                myHolder.text_AppointmentName.setText("Test Pending");
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public String formatTime(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
/*
        2019-07-30T17:07:00*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("h:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "";
    }



    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy h:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }





}
