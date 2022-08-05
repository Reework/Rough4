package com.shamrock.reework.activity.HomeModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.shamrock.reework.activity.activtyhistory.WeeklyActivityData;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivtyFragAdapter extends RecyclerView.Adapter<ActivtyFragAdapter.MyHolder> {
    Context context;
    ArrayList<WeeklyActivityData> list;
    String strText="",strText1;
    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;

    public ActivtyFragAdapter(Context context, ArrayList<WeeklyActivityData> list) {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDate, tvcalory, tvActualHour,actual_min;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvcalory = itemView.findViewById(R.id.tvcalory);
            this.tvActualHour = itemView.findViewById(R.id.tvActualHour);
            this.actual_min = itemView.findViewById(R.id.actual_min);
        }

        @Override
        public void onClick(View v) {
        }
    }

    @NonNull
    @Override
    public ActivtyFragAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adap_activty_fragment, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivtyFragAdapter.MyHolder holder, int i) {
        WeeklyActivityData model = list.get(i);

        if (model != null) {


            if (!TextUtils.isEmpty(model.getCreatedOn())) {

                strText1 =formatDates( list.get(i).getCreatedOn());
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


                holder.tvDate.setText(spannableStringBuilder1);


            }



            holder.tvcalory.setText(String.valueOf(Math.round(Float.parseFloat(model.getTotalBurnedCalories()))));
        }

        holder.actual_min.setText(model.getTotalActivityMinutes());

        assert model != null;
        if (model.getScheduleMinutes()!=null){
            holder.tvActualHour.setText(model.getScheduleMinutes());

        }else {
            holder.tvActualHour.setText("0");

        }
    }
    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        int index = dateFromServer.indexOf("T");
        String strnewStartDate = dateFromServer.substring(0, index);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM-yy");
        Date date = null;
        try {
            date = dateFormat.parse(strnewStartDate);
            return getFormattedDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getFormattedDate(Date dates) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dates);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        SimpleDateFormat format = new SimpleDateFormat("d");
        String date = format.format(dates);
//
//        if(date.endsWith("1") && !date.endsWith("11"))
//            format = new SimpleDateFormat("d'st' MMM, yyyy");
//        else if(date.endsWith("2") && !date.endsWith("12"))
//            format = new SimpleDateFormat("d'nd' MMM, yyyy");
//        else if(date.endsWith("3") && !date.endsWith("13"))
//            format = new SimpleDateFormat("d'rd' MMM, yyyy");
//        else
//            format = new SimpleDateFormat("d'th' MMM, yyyy");
//        format = new SimpleDateFormat("dd-MM-yy");

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
