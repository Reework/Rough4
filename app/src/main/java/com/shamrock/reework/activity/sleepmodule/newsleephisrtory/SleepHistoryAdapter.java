package com.shamrock.reework.activity.sleepmodule.newsleephisrtory;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.fragment.MasterSleepFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SleepHistoryAdapter extends RecyclerView.Adapter<SleepHistoryAdapter.MyHolder>
{
    Context context;
    ArrayList<SleepData> arrayList1;
    PowernapAdapter powernapAdapter;
    MasterSleepFragment masterSleepFragment;
    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;
    String strText="",strText1;

    public SleepHistoryAdapter(Context context, ArrayList<SleepData> arrayList1, MasterSleepFragment masterSleepFragment) {
        this.context=context;
        this.arrayList1=arrayList1;
        this.masterSleepFragment=masterSleepFragment;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txt_sleep_total_hrs, txt_start_sleep_date;
        RecyclerView recler_power_nap;

        LinearLayout ll_main_total_sleep;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            this.txt_start_sleep_date = itemView.findViewById(R.id.txt_start_sleep_date);
            this.txt_sleep_total_hrs = itemView.findViewById(R.id.txt_sleep_total_hrs);
            this.recler_power_nap = itemView.findViewById(R.id.recler_power_nap);
            this.ll_main_total_sleep = itemView.findViewById(R.id.ll_main_total_sleep);
        }

        @Override
        public void onClick(View v)
        {
        }
    }

    @NonNull
    @Override
    public SleepHistoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_new_sleep_history, viewGroup, false);
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
    public void onBindViewHolder(@NonNull final SleepHistoryAdapter.MyHolder holder, int i)
    {
        SleepData model = arrayList1.get(i);
        if (Integer.parseInt(model.getTotalHours())<60){
            holder.txt_sleep_total_hrs.setText(model.getTotalHours()+ " Mins");

        }else {
            holder.txt_sleep_total_hrs.setText(formatHoursAndMinutesnew(Integer.parseInt(model.getTotalHours())));


        }


//        holder.txt_sleep_total_hrs.setText(String.valueOf(model.getTotalHours()));


        int index = model.getSleepEntryDate().indexOf("T");
        String dates=(model.getSleepEntryDate().substring(0,index));



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




        holder.txt_start_sleep_date.setText(spannableStringBuilder1);

        powernapAdapter = new PowernapAdapter(context, model.getSleepDurations(),masterSleepFragment,arrayList1.get(i).getSleepEntryDate());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        holder.recler_power_nap.setLayoutManager(layoutManager);
        holder.recler_power_nap.setItemAnimator(new DefaultItemAnimator());
        holder.recler_power_nap.setAdapter(powernapAdapter);


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
    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    @Override
    public int getItemCount()
    {
        return arrayList1.size();
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
