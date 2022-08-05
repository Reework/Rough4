package com.shamrock.reework.activity.sleepmodule.newsleephisrtory;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.fragment.MasterSleepFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PowernapAdapter extends RecyclerView.Adapter<PowernapAdapter.MyHolder>
{
    Context context;
    ArrayList<SleepDurations> list;
    OnPowerNapClick onPowerNapClick;
    String sleepEntryDate;

    public PowernapAdapter(Context context, ArrayList<SleepDurations> list, MasterSleepFragment masterSleepFragment, String sleepEntryDate)
    {
        this.context = context;
        this.list = list;
        onPowerNapClick= (OnPowerNapClick) masterSleepFragment;;
        this.sleepEntryDate=sleepEntryDate;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txt_date, txt_sleep_time,txt_entry_date;
        TextView img_edit_sleep_power,img_delete_sleep_power;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            this.txt_date = itemView.findViewById(R.id.txt_date);
            this.txt_sleep_time = itemView.findViewById(R.id.txt_sleep_time);
            this.img_edit_sleep_power = itemView.findViewById(R.id.img_edit_sleep_power);
            this.txt_entry_date = itemView.findViewById(R.id.txt_entry_date);
            this.img_delete_sleep_power = itemView.findViewById(R.id.img_delete_sleep_power);
        }

        @Override
        public void onClick(View v)
        {
        }
    }

    @NonNull
    @Override
    public PowernapAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_power_nap, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PowernapAdapter.MyHolder holder, final int i)
    {
        SleepDurations model = list.get(i);

        int index = model.getStartTime().indexOf("T");
        int fromindex=index+1;
        int toindex=index+6;
        String starttime=model.getStartTime().substring(fromindex,toindex);
        String abc[]=formatDatesNew(model.getStartTime()).split(" ");


        if(abc[2].equals("PM")||abc[2].equals("pm")){
            holder.txt_date.setText(abc[1]+" PM");
        }else if(abc[2].equals("AM")||abc[2].equals("am")){
            holder.txt_date.setText(abc[1]+" AM");
        }

//        2020-06-15T00:16:00

//        yyyy-MM-dd'T'HH:mm
//        holder.txt_entry_date.setText(model.ge);

        if (Integer.parseInt(model.getTotalMinutes())<60){
            holder.txt_sleep_time.setText(model.getTotalMinutes()+ " Mins");

        }else {
            String time=formatHoursAndMinutesnew(Integer.parseInt(model.getTotalMinutes()));

            holder.txt_sleep_time.setText(formatHoursAndMinutesnew(Integer.parseInt(model.getTotalMinutes())));


        }

        holder.img_delete_sleep_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPowerNapClick.deletePowerNap(list.get(i).getId());

            }
        });

        holder.img_edit_sleep_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPowerNapClick.updatePowerNap(list.get(i),sleepEntryDate);
            }
        });

//        holder.txt_sleep_time.setText(String.valueOf(model.getTotalMinutes()));


    }

    public String formatDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
    public  String formatHoursAndMinutesnew(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" Hr ";
        }else {
            strhours=" Hrs ";

        }
        return (totalMinutes / 60) + strhours + minutes+" Mins";
    }

}
