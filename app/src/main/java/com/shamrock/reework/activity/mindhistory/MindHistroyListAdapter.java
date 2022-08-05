package com.shamrock.reework.activity.mindhistory;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.common.Data;
import com.shamrock.reework.fragment.MasterMindFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MindHistroyListAdapter extends RecyclerView.Adapter<MindHistroyListAdapter.MyHolder>
{
    Context mContext;
    ArrayList<Data> list;
    private static LayoutInflater inflater = null;
    OnEditMindClick onEditWaterClick;
    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;
    String strText="",strText1;
    public MindHistroyListAdapter(MasterMindFragment context, ArrayList<Data> subscriptionFeatureArrayList) {
        this.list = subscriptionFeatureArrayList;
        mContext = context.getContext();
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onEditWaterClick = (OnEditMindClick) context;

    }
    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_actual_sleep_hous,img_delete_water_power;
        TextView txt_schedule_sleep_hous;
        TextView sleep_date;
        TextView img_edit_sleep;
        LinearLayout ll_mind;

        public MyHolder(@NonNull View vi)
        {
            super(vi);
            vi.setClickable(true);

            txt_actual_sleep_hous = vi.findViewById(R.id.txt_actual_sleep_hous);
            txt_schedule_sleep_hous = vi.findViewById(R.id.txt_schedule_sleep_hous);
            sleep_date = vi.findViewById(R.id.sleep_date);
            img_edit_sleep = vi.findViewById(R.id.img_edit_sleep_new);
            ll_mind = vi.findViewById(R.id.ll_mind);
            img_delete_water_power = vi.findViewById(R.id.img_delete_water_power);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_my_sleep_history, viewGroup, false);
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

            }
            String schedulehours ="";
            if (song.getScheduledMindStatus()!=null){
                schedulehours=    song.getScheduledMindStatus();

            }






            strText1 =formatDates((song.getStatusDate()));
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








            myHolder.sleep_date.setText(spannableStringBuilder1);

            if (actualhours.equalsIgnoreCase("neutral")){
                actualhours="Neutral";
            }

            if (actualhours.equalsIgnoreCase("happy")){
                actualhours="Happy";
            }

            if (actualhours.equalsIgnoreCase("stress")){
                actualhours="Stressed";
            }
            myHolder.txt_schedule_sleep_hous.setVisibility(View.GONE);
            myHolder.txt_actual_sleep_hous.setText(  ""+actualhours);
            myHolder.txt_schedule_sleep_hous.setText("Schedule Mind Status   :  "+schedulehours);

            myHolder.img_edit_sleep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditWaterClick.getEditMindPosition(song);
                }
            });

            myHolder.img_delete_water_power.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditWaterClick.deleteMindPosition(song);
                }
            });
        }


    }

    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
