package com.shamrock.reework.activity.FoodModule.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeeklyAnalysisAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<FoodData> mList;
    Context mContext;
    public WeeklyAnalysisAdapterNew(Context context, List<FoodData> list) {
        this.mList = list;
this.mContext  = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weekly_analysis, viewGroup, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        try {
            if(i==0){
                ((HeaderViewHolder) holder).linHeader.setVisibility(View.VISIBLE);
            }else {
                ((HeaderViewHolder) holder).linHeader.setVisibility(View.GONE);
            }
//                if(mList.get(i).getActual()>mList.get(i).getScheduled()){
//                    ((HeaderViewHolder) holder).textBurnCal.setTextColor(mContext.getResources().getColor(R.color.color_smooth_red));
//
//                }else if(mList.get(i).getActual()<mList.get(i).getScheduled()){
//                    ((HeaderViewHolder) holder).textBurnCal.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
//                }
//                else if(mList.get(i).getActual()==mList.get(i).getScheduled()){
//                    ((HeaderViewHolder) holder).textBurnCal.setTextColor(mContext.getResources().getColor(R.color.colorReescore_BlueYellow));
//                }



//                if (!String.valueOf(mList.get(i).getActual()).equalsIgnoreCase("0")){
                    ((HeaderViewHolder) holder).textBurnCal.setText(String.valueOf(Math.round(mList.get(i).getTotalCaloriesIntake())));
//
//                }else {
//                    ((HeaderViewHolder) holder).textBurnCal.setText("NA");
//
//                }


            ((HeaderViewHolder) holder).textProtein.setText(String.valueOf(mList.get(i).getTotalProteinIntake()));
            ((HeaderViewHolder) holder).textCarb.setText(String.valueOf(mList.get(i).getTotalCarbsIntake()));
            ((HeaderViewHolder) holder).textFat.setText(String.valueOf(mList.get(i).getTotalFatIntake()));
            ((HeaderViewHolder) holder).textFibre.setText(String.valueOf(mList.get(i).getTotalFibreIntake()));


            if(mList.get(i).getCreatedOn()!=null){
                if(mList.get(i).getCreatedOn().contains("T")){
                    try {
                        int index = mList.get(i).getCreatedOn().indexOf("T");

                       ((HeaderViewHolder) holder).textDate.setSelected(true);
                        ((HeaderViewHolder) holder).textDate.setText(formatDates( mList.get(i).getCreatedOn()));
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView textDate, textProtein, textBurnCal,textCarb,textFat,textFibre;
        LinearLayout linHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textBurnCal = itemView.findViewById(R.id.textBurnCal);
            textProtein = itemView.findViewById(R.id.textProtein);
            textCarb = itemView.findViewById(R.id.textCarb);
            textFat = itemView.findViewById(R.id.textFat);
            textFibre = itemView.findViewById(R.id.textFibre);

            textDate = itemView.findViewById(R.id.textDate);
            linHeader  = itemView.findViewById(R.id.linHeader);
        }
    }

    public String formatDggates(String dateFromServer)
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
        return " ";
    }
    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd,MMM");
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

        if(date.endsWith("1") && !date.endsWith("11"))
            format = new SimpleDateFormat("d'st' MMM");
        else if(date.endsWith("2") && !date.endsWith("12"))
            format = new SimpleDateFormat("d'nd' MMM");
        else if(date.endsWith("3") && !date.endsWith("13"))
            format = new SimpleDateFormat("d'rd' MMM");
        else
            format = new SimpleDateFormat("d'th' MMM");

        return format.format(dates);


    }

}
