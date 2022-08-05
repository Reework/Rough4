package com.shamrock.reework.activity.HomeModule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.pojo.ClsRepeatSleep;
import com.shamrock.reework.activity.sleepmodule.ClsRepeatSleepData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RepeatSleepListAdapter extends RecyclerView.Adapter<RepeatSleepListAdapter.MyHolder>
    {
        Context context;
        ArrayList<ClsRepeatSleepData> countryList;



    public RepeatSleepListAdapter(Context context, ArrayList<ClsRepeatSleepData> countryList)
        {
            this.context = context;
            this.countryList = countryList;
        }

        public class MyHolder extends RecyclerView.ViewHolder
        {
            TextView txt_end_time,txt_start_time,txt_add_repeat_sleep,txtsleeptype;

            public MyHolder(@NonNull View itemView)
            {
                super(itemView);

                txt_start_time = itemView.findViewById(R.id.txt_start_time);
                txt_end_time = itemView.findViewById(R.id.txt_end_time);
                txt_add_repeat_sleep = itemView.findViewById(R.id.txt_add_repeat_sleep);
                txtsleeptype = itemView.findViewById(R.id.txtsleeptype);
                txt_add_repeat_sleep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!countryList.get(getAdapterPosition()).isChecked()){

                            countryList.get(getAdapterPosition()).setChecked(true);
                            notifyDataSetChanged();
                        }else {

                            countryList.get(getAdapterPosition()).setChecked(false);
                            notifyDataSetChanged();
                        }
                    }
                });
            }

        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.row_repeat_sleep_lsit, viewGroup, false);
            return new MyHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        String abc[]=formatDates(countryList.get(i).getStartTime()).split(" ");
            String abc1[]=formatDates(countryList.get(i).getEndTime()).split(" ");

            String str,end;

            if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
                str=abc[0]+" AM";
            }else{
                str=abc[0]+" PM";
            }

            if(abc1[1].equals("am")||abc1[1].equals("Am")||abc[1].equals("AM")){
                end=abc1[0]+" AM";
            }else{
                end=abc1[0]+" PM";
            }



            myHolder.txt_start_time.setText(str+" - "+end );
            myHolder.txt_end_time.setText(formatDates(countryList.get(i).getEndTime()));
            if (countryList.get(i).isChecked()){
                myHolder.txt_add_repeat_sleep.setBackground(context.getResources().getDrawable(R.drawable.bkgr_button_green_bordered_new));
                myHolder.txt_add_repeat_sleep.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));

                myHolder.txt_add_repeat_sleep.setText(" ADDED ");
            }else {

                myHolder.txt_add_repeat_sleep.setBackground(context.getResources().getDrawable(R.drawable.bg_green_button));
                myHolder.txt_add_repeat_sleep.setTextColor(context.getResources().getColor(R.color.white));
                myHolder.txt_add_repeat_sleep.setText("   ADD   ");

            }


        }

        @Override
        public int getItemCount()
        {
            return countryList.size();
        }



      public String  formatDates(String dateFromServer)
        {
            //String strDate = "2013-05-15T10:00:00-0700";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat newDateFormatter = new SimpleDateFormat("hh:mm a");
            Date date = null;
            try
            {
                date = dateFormat.parse(dateFromServer);
                return newDateFormatter.format(date);
            } catch (ParseException e) { e.printStackTrace(); }
            return "N/A";
        }
    }
