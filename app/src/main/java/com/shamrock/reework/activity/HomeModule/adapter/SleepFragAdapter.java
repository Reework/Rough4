package com.shamrock.reework.activity.HomeModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.SleepResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SleepFragAdapter extends RecyclerView.Adapter<SleepFragAdapter.MyHolder>
{
    Context context;
    ArrayList<SleepResponse.Data> list;

    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;
    String strText="",strText1;

    public SleepFragAdapter(Context context, ArrayList<SleepResponse.Data> list)
    {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvDate, tvIdeal, tvActual;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvIdeal = itemView.findViewById(R.id.tvScheduleHour);
            this.tvActual = itemView.findViewById(R.id.tvActualHour);
        }

        @Override
        public void onClick(View v)
        {
        }
    }

    @NonNull
    @Override
    public SleepFragAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adap_sleep_fragment, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SleepFragAdapter.MyHolder holder, int i)
    {
        SleepResponse.Data model = list.get(i);

        if (model!=null) {
            int scheduledSleepHours = model.getScheduledSleepHours();
            int actualSleepHours = model.getActualSleepHours();
            String date = model.getCreatedOn();

            if (actualSleepHours > 0) {
                if (actualSleepHours > scheduledSleepHours) {
                    String text = "<font color=#FF7C9A>" + formatHoursAndMinutesMin(actualSleepHours) + "</font>  </font>";
                    holder.tvActual.setText(Html.fromHtml(text));

                }



                else if (actualSleepHours < scheduledSleepHours) {

                    //  String text = "<font color=#62A798>" + actualSleepHours + "</font> <font color=#c9d9da> hrs</font>";
                    String text = "<font color=#FF7C9A>" + formatHoursAndMinutesMin(actualSleepHours) + "</font> </font>";
                    holder.tvActual.setText(Html.fromHtml(text));
                }

                if (actualSleepHours ==scheduledSleepHours) {


                    //  String text = "<font color=#62A798>" + actualSleepHours + "</font> <font color=#c9d9da> hrs</font>";
                    String text = "<font color=#FF7C9A>" + formatHoursAndMinutesMin(actualSleepHours) + "</font> </font>";
                    holder.tvActual.setText(Html.fromHtml(text));
                }



            }else{
                holder.tvActual.setText("0"+" hrs");
            }

            if (scheduledSleepHours > 0) {
                String text = "<font>" + formatHoursAndMinutesMin(scheduledSleepHours) + "</font> <font color=#000000></font>";
                holder.tvIdeal.setText(Html.fromHtml(text + ""));
            }
            else
                holder.tvIdeal.setText("0");


            if (!TextUtils.isEmpty(date)){
                holder.tvDate.setSelected(true);






                strText1 =formatDates(date);
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







                holder.tvDate.setText("  "+spannableStringBuilder1);
            }

        }
    }

    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    public  String formatHoursAndMinutesMin(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" h ";
        }else {
            strhours=" h ";

        }


        String submittime="";
        submittime= (totalMinutes / 60) + strhours + minutes+" m";




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
    public int getItemCount()
    {
        return list.size();
    }

    public String formatDatessssssssss(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd,MMM yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd,MMM yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return getFormattedDate(date);
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

//        if(date.endsWith("1") && !date.endsWith("11"))
//            format = new SimpleDateFormat("d'st' MMM, yyyy");
//        else if(date.endsWith("2") && !date.endsWith("12"))
//            format = new SimpleDateFormat("d'nd' MMM, yyyy");
//        else if(date.endsWith("3") && !date.endsWith("13"))
//            format = new SimpleDateFormat("d'rd' MMM, yyyy");
//        else
//            format = new SimpleDateFormat("d'th' MMM, yyyy");



        if(date.endsWith("1") && !date.endsWith("11")) {
            strText = "st";
            format = new SimpleDateFormat("d'st' MMM");
        }
        else if(date.endsWith("2") && !date.endsWith("12")) {
            strText = "nd";
            format = new SimpleDateFormat("d'nd' MMM");


        } else if(date.endsWith("3") && !date.endsWith("13")) {
            strText = "rd";
            format = new SimpleDateFormat("d'rd' MMM");
        }  else {
            strText = "th";
            format = new SimpleDateFormat("d'th' MMM");
        }


        return format.format(dates);


    }

}
