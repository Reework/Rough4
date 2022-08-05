package com.shamrock.reework.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.WaterResponse;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MasterWaterAdapter extends RecyclerView.Adapter<MasterWaterAdapter.MyHolder>
{
    private ArrayList<WaterResponse.Data> list;
    Context context;

    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;
    String strText="",strText1;
    public MasterWaterAdapter(Context context, ArrayList<WaterResponse.Data> list)
    {
        this.list = list;
        this.context = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView textView_Date, textView_Ideal, textView_Your;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);

            this.textView_Date = itemView.findViewById(R.id.text_master_water_date);
            this.textView_Ideal = itemView.findViewById(R.id.text_master_water_ideal);
            this.textView_Your = itemView.findViewById(R.id.text_master_water_your);
        }
    }

    @NonNull
    @Override
    public MasterWaterAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_master_water, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterWaterAdapter.MyHolder holder, int i)
    {
        WaterResponse.Data model = list.get(i);

        if (model!=null)
        {
            int scheduledWaterIntake = model.getScheduledWaterIntake();
            int actualWaterIntake = model.getActualWaterIntake();
            String date = model.getCreatedOn();
//            if(actualWaterIntake > scheduledWaterIntake){
//                holder.textView_Your.setTextColor(context.getResources().getColor(R.color.color_smooth_red));
//
//            }else if(actualWaterIntake < scheduledWaterIntake){
//                holder.textView_Your.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//            }
//            else if(actualWaterIntake < scheduledWaterIntake){
//                holder.textView_Your.setTextColor(context.getResources().getColor(R.color.colorReescore_BlueYellow));
//            }
            // if (scheduledWaterIntake > 0)


            if (scheduledWaterIntake>0){
                DecimalFormat decimalFormat=new DecimalFormat("0.00");


                double scheduleintake=Double.valueOf(scheduledWaterIntake)/100;

                String strscheduledWaterIntake=decimalFormat.format(scheduleintake);



                holder.textView_Ideal.setText(strscheduledWaterIntake+"L");
            }else {
                holder.textView_Ideal.setText(scheduledWaterIntake+"L");

            }



            if (actualWaterIntake>0){
                DecimalFormat decimalFormat=new DecimalFormat("0.00");


                double scheduleintake=Double.valueOf(actualWaterIntake)/100;

                String strscheduledWaterIntake=decimalFormat.format(scheduleintake);



                holder.textView_Your.setText(strscheduledWaterIntake+"L");
            }else {
                holder.textView_Your.setText(actualWaterIntake+"L");

            }



            //  if (actualWaterIntake > 0)
//                holder.textView_Your.setText(actualWaterIntake+"");

            if (!TextUtils.isEmpty(date))

                Log.e("date",date);



            holder.textView_Date.setSelected(true);


            strText1 =formatDatesNew(date);
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









            holder.textView_Date.setText(spannableStringBuilder1);
        }
    }
    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }

    /*private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<MasterWaterModel> dataSet;
    Context context;

    public MasterWaterAdapter(Context context, ArrayList<MasterWaterModel> dataSet)
    {
        this.dataSet = dataSet;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView_Date, textView_Ideal, textView_Your;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            this.textView_Date = (TextView) itemView.findViewById(R.id.text_master_water_date);
            this.textView_Ideal = (TextView) itemView.findViewById(R.id.text_master_water_ideal);
            this.textView_Your = (TextView) itemView.findViewById(R.id.text_master_water_your);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder
    {

        public HeaderViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout_water, parent, false);
            return new HeaderViewHolder(layoutView);
        }
        else if (viewType == TYPE_ITEM)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_master_water, parent, false);
            return new MyViewHolder(view);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition)
    {
        if (holder instanceof HeaderViewHolder)
        {
        }
        else if (holder instanceof MyViewHolder)
        {
            if (dataSet.get(listPosition).getYourHrs() > 8)
            {
                ((MyViewHolder) holder).textView_Your.setTextColor(ContextCompat.getColor(context, R.color.color_smooth_red));
            }
            else if
            (dataSet.get(listPosition).getYourHrs() < 8)
            {
                ((MyViewHolder) holder).textView_Your.setTextColor(ContextCompat.getColor(context, R.color.color_graph_orange));
            }
            else
            {
                ((MyViewHolder) holder).textView_Your.setTextColor(ContextCompat.getColor(context, R.color.colorBlueGreen2));
            }
            ((MyViewHolder) holder).textView_Date.setText(dataSet.get(listPosition).getDate());
            ((MyViewHolder) holder).textView_Ideal.setText("" + dataSet.get(listPosition).getIdealHrs());
            ((MyViewHolder) holder).textView_Your.setText("" + dataSet.get(listPosition).getYourHrs());
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }*/

    public String formatDatesNew(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
//            format = new SimpleDateFormat("d'st' MMM,yy");
//        else if(date.endsWith("2") && !date.endsWith("12"))
//            format = new SimpleDateFormat("d'nd' MMM,yy");
//        else if(date.endsWith("3") && !date.endsWith("13"))
//            format = new SimpleDateFormat("d'rd' MMM,yy");
//        else
//            format = new SimpleDateFormat("d'th' MMM,yy");


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

    public String formccatDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd,MMM yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
}
