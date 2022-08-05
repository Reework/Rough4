package com.shamrock.reework.activity.sleepmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterDurations;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.OnPowerNapClick;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.OnWaterPowerNapClick;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.SleepDurations;
import com.shamrock.reework.fragment.MasterSleepFragment;
import com.shamrock.reework.fragment.MasterWaterFragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WaterPowernapAdapter extends RecyclerView.Adapter<WaterPowernapAdapter.MyHolder>
{
    Context context;
    ArrayList<WaterDurations> list;
    OnWaterPowerNapClick onPowerNapClick;
    String sleepEntryDate;

    public WaterPowernapAdapter(Context context, ArrayList<WaterDurations> list, MasterWaterFragment masterSleepFragment, String sleepEntryDate)
    {
        this.context = context;
        this.list = list;
        onPowerNapClick= (OnWaterPowerNapClick) masterSleepFragment;;
        this.sleepEntryDate=sleepEntryDate;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txt_date, txt_sleep_time,txt_water_intake_nap,txt_water_unit;

        TextView img_edit_sleep_power,img_delete_water_power;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

//            this.txt_date = itemView.findViewById(R.id.txt_date);
            txt_water_intake_nap = itemView.findViewById(R.id.txt_water_intake_naps);
            txt_water_unit = itemView.findViewById(R.id.txt_water_unit);
            img_edit_sleep_power = itemView.findViewById(R.id.img_edit_sleep_power);
            img_delete_water_power = itemView.findViewById(R.id.img_delete_water_power);
        }

        @Override
        public void onClick(View v)
        {
        }
    }

    @NonNull
    @Override
    public WaterPowernapAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_power_nap_water, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterPowernapAdapter.MyHolder holder, final int i)
    {
        WaterDurations model = list.get(i);




        DecimalFormat decimalFormat=new DecimalFormat("0.00");


//        double showsactual=Double.parseDouble(String.valueOf(model.getWaterInTake()))/100;

//        String showActual=decimalFormat.format(showsactual);





        String strqty= String.valueOf(model.getWaterInTake());
        int startindex=strqty.length()-2;
        int endindex=strqty.length()-1;
        String[] strNewQty=strqty.split("\\.");
        boolean isFound=false;


        if (strNewQty[1].equals("0")){
            isFound=true;

        }
        if (isFound){
            isFound=false;
            holder.txt_water_intake_nap.setText(""+strNewQty[0].toString());



        }else {
            holder.txt_water_intake_nap.setText(String.valueOf(model.getWaterInTake()));

        }








//        holder.txt_water_intake_nap.setText(showActual);
        holder.txt_water_unit.setText(String.valueOf(model.getUnit()));

        holder.img_edit_sleep_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPowerNapClick.updateWaterPowerNap(list.get(i),sleepEntryDate);
            }
        });

        holder.img_delete_water_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPowerNapClick.deleteWaterPowerNap(list.get(i).getId());
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
