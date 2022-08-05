package com.shamrock.reework.activity.mindhistory;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.fragment.MasterMindFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MindWeeklyListAdapter extends RecyclerView.Adapter<MindWeeklyListAdapter.MyHolder>
{
    Context mContext;
    ArrayList<com.shamrock.reework.activity.mindhistory.Data> list;
    private static LayoutInflater inflater = null;
    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;
    String strText="",strText1;
    public MindWeeklyListAdapter(MasterMindFragment context, ArrayList<com.shamrock.reework.activity.mindhistory.Data> subscriptionFeatureArrayList) {
        this.list = subscriptionFeatureArrayList;
        mContext = context.getContext();
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_actula_mind_status,txt_Date,txt_shedule_mind_status;


        public MyHolder(@NonNull View vi)
        {
            super(vi);
            vi.setClickable(true);

            txt_actula_mind_status = vi.findViewById(R.id.txt_actula_mind_status);
            txt_Date = vi.findViewById(R.id.txt_Date);
            txt_shedule_mind_status = vi.findViewById(R.id.txt_shedule_mind_status);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_weekly_mind, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i)
    {

        final Data song = list.get(i);
        if (song.getActualMindStatus()!=null){

            String actualhours = "";
            if (song.getActualMindStatus()!=null){
                actualhours=    song.getActualMindStatus();
                myHolder.txt_actula_mind_status.setText(actualhours);

            }else {
                myHolder.txt_actula_mind_status.setText("");

            }
            String schedulehours ="";
            if (song.getScheduledMindStatus()!=null){
                schedulehours=    song.getScheduledMindStatus();

            }






//            strText1 =formatDates((song.getStatusDate()));








            myHolder.txt_Date.setText(song.getCreatedOn()+" "+song.StatusTime);
            if (song.getScheduledMindStatus()!=null){
                myHolder.txt_shedule_mind_status.setText(song.getScheduledMindStatus());

            }else {
                myHolder.txt_shedule_mind_status.setText("Happy");

            }



        }


    }

    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'T'HH:mmT00:00:00.000Z");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return getFormattedDate(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }


    @Override
    public int getItemCount()
    {
        return list.size();
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
