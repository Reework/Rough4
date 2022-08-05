package com.shamrock.reework.activity.activtyhistory;

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
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.fragment.MasterMyActivityFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityHistoryMainAdapter extends RecyclerView.Adapter<ActivityHistoryMainAdapter.MyHolder>
{
    private ArrayList<ActivityData> list;
    Context context;
    ActivityHistorySubAdapter activityHistorySubAdapter;

    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;
    MasterMyActivityFragment masterMyActivityFragment;
    String strText="",strText1;

    public ActivityHistoryMainAdapter(Context context, ArrayList<ActivityData> list, MasterMyActivityFragment masterMyActivityFragment)
    {
        this.list = list;
        this.context = context;
        this.masterMyActivityFragment=masterMyActivityFragment;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_date_create;
        RecyclerView recyler_activity_history_sub_page;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);

            this.txt_date_create = itemView.findViewById(R.id.txt_date_create);
            this.recyler_activity_history_sub_page = itemView.findViewById(R.id.recyler_activity_history_sub_page);
        }
    }
    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    @NonNull
    @Override
    public ActivityHistoryMainAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_activity_history, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHistoryMainAdapter.MyHolder holder, int i)
    {
        ActivityData model = list.get(i);

        if (model!=null)
        {


            strText1 =formatDates((model.getCreatedOn()));
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



            holder.txt_date_create.setText(spannableStringBuilder1);
            activityHistorySubAdapter = new ActivityHistorySubAdapter(context, model.getActivities(),masterMyActivityFragment);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            holder.recyler_activity_history_sub_page.setLayoutManager(layoutManager);
            holder.recyler_activity_history_sub_page.setItemAnimator(new DefaultItemAnimator());
            holder.recyler_activity_history_sub_page.setAdapter(activityHistorySubAdapter);


        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }



    public String formatDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
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
