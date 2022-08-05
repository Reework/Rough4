package com.shamrock.reework.activity.sleepmodule;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterHistoryData;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.PowernapAdapter;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.SleepData;
import com.shamrock.reework.fragment.MasterSleepFragment;
import com.shamrock.reework.fragment.MasterWaterFragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WaterHistoryAdapter extends RecyclerView.Adapter<WaterHistoryAdapter.MyHolder>
{
    Context context;
    ArrayList<WaterHistoryData> arrayList1;
    WaterPowernapAdapter powernapAdapter;
    MasterWaterFragment masterSleepFragment;
    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;
    String strText="",strText1;

    public WaterHistoryAdapter(Context context, ArrayList<WaterHistoryData> arrayList1, MasterWaterFragment masterSleepFragment) {
        this.context=context;
        this.arrayList1=arrayList1;
        this.masterSleepFragment=masterSleepFragment;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txt_total_water, txt_water_histry_entry_Date;
        RecyclerView recler_power_nap_water;

        LinearLayout ll_main_total_sleep;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            this.txt_water_histry_entry_Date = itemView.findViewById(R.id.txt_water_histry_entry_Date);
            this.txt_total_water = itemView.findViewById(R.id.txt_total_water);
            this.recler_power_nap_water = itemView.findViewById(R.id.recler_power_nap_water);
            this.ll_main_total_sleep = itemView.findViewById(R.id.ll_main_total_sleep);
        }

        @Override
        public void onClick(View v)
        {
        }
    }

    @NonNull
    @Override
    public WaterHistoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_new_water_history, viewGroup, false);
        return new MyHolder(view);
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


    @Override
    public void onBindViewHolder(@NonNull final WaterHistoryAdapter.MyHolder holder, int i)
    {
        WaterHistoryData model = arrayList1.get(i);


//        holder.txt_sleep_total_hrs.setText(String.valueOf(model.getTotalHours()));


        int index = model.getEntryDate().indexOf("T");
        String dates=(model.getEntryDate().substring(0,index));


        strText1 =formatDates(dates);
        spannableStringBuilder1 = new SpannableStringBuilder(strText1);
        SuperscriptSpan superscriptSpan1 = new SuperscriptSpan();

        if(strText.equals("th")) {
            spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("th"),
                    strText1.indexOf("th") + ("th").length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            showSmallSizeText1("th");
        }else  if(strText.equals("nd")) {
            spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("nd"),
                    strText1.indexOf("nd") + ("nd").length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            showSmallSizeText1("nd");
        }else  if(strText.equals("rd")) {
            spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("rd"),
                    strText1.indexOf("rd") + ("rd").length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            showSmallSizeText1("rd");
        }else  if(strText.equals("st")) {
            spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("st"),
                    strText1.indexOf("st") + ("st").length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            showSmallSizeText1("st");
        }


        holder.txt_water_histry_entry_Date.setText(spannableStringBuilder1);


        DecimalFormat decimalFormat=new DecimalFormat("0.00");

        String totalwater=decimalFormat.format(model.getTotalWaterIntake());


        holder.txt_total_water.setText(String.valueOf(totalwater));

        powernapAdapter = new WaterPowernapAdapter(context, model.getWaterDurations(),masterSleepFragment,arrayList1.get(i).getEntryDate());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        holder.recler_power_nap_water.setLayoutManager(layoutManager);
        holder.recler_power_nap_water.setItemAnimator(new DefaultItemAnimator());
        holder.recler_power_nap_water.setAdapter(powernapAdapter);


        holder.ll_main_total_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ll_main_total_sleep.getVisibility()==View.VISIBLE){
                    holder.ll_main_total_sleep.setVisibility(View.GONE);
                }
                if (holder.ll_main_total_sleep.getVisibility()==View.GONE){
                    holder.ll_main_total_sleep.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    @Override
    public int getItemCount()
    {
        return arrayList1.size();
    }
    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public String getFormattedDate(Date dates) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dates);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        SimpleDateFormat format = new SimpleDateFormat("d");
        String date = format.format(dates);

        if(date.endsWith("1") && !date.endsWith("11")) {
            strText = "st";
            format = new SimpleDateFormat("d'st' MMM yy");
        }
        else if(date.endsWith("2") && !date.endsWith("12")) {
            strText = "nd";
            format = new SimpleDateFormat("d'nd' MMM yy");


        } else if(date.endsWith("3") && !date.endsWith("13")) {
            strText = "rd";
            format = new SimpleDateFormat("d'rd' MMM yy");
        }  else {
            strText = "th";
            format = new SimpleDateFormat("d'th' MMM yy");
        }

        return format.format(dates);


    }

}
