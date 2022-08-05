package com.shamrock.reework.activity.aNewInterpretation.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.activity.BloodTestModule.adapter.ImageViewReportActivity;
import com.shamrock.reework.activity.testimals.TestimalDataClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class
WellnessListAdapter extends RecyclerView.Adapter<WellnessListAdapter.MyHolder>
{

    Context context;

    ArrayList<WellnessParams> arylst_wellness_param;
    public WellnessListAdapter(Context context, ArrayList<WellnessParams> arylst_wellness_param)
    {
        this.context = context;
        this.arylst_wellness_param=arylst_wellness_param;

    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_user,img_testominal;
        TextView txt_wellness_qn,txt_wellness_score_max,txt_wellness_score;
        TextView txt_rmarks,txt_future_score;
        ProgressBar progressBar_wellness;


        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            txt_wellness_qn=itemView.findViewById(R.id.txt_wellness_qn);
            txt_wellness_score_max=itemView.findViewById(R.id.txt_wellness_score_max);
            txt_wellness_score=itemView.findViewById(R.id.txt_wellness_score);
            progressBar_wellness=itemView.findViewById(R.id.progressBar_wellness);
            txt_future_score=itemView.findViewById(R.id.txt_future_score);
            txt_rmarks=itemView.findViewById(R.id.txt_rmarks);



        }


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public WellnessListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_wellness_param, viewGroup, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WellnessListAdapter.MyHolder myHolder, int i)
    {







        try {
            myHolder.txt_future_score.setText("Future Score : "+arylst_wellness_param.get(i).getFutureScore());
            if (arylst_wellness_param.get(i).getRemark()!=null){
                myHolder.txt_rmarks.setText("Remark : "+arylst_wellness_param.get(i).getRemark());

            }else {
                myHolder.txt_rmarks.setText("Remark : ");

            }
            myHolder.txt_wellness_qn.setText(arylst_wellness_param.get(i).getQuestion());
            myHolder.txt_wellness_score.setText(arylst_wellness_param.get(i).getScore());
            myHolder.txt_wellness_score_max.setText(String.valueOf(arylst_wellness_param.get(i).getFutureScore()));



            double min= Double.parseDouble(arylst_wellness_param.get(i).getScore());
            double max= Double.parseDouble(String.valueOf(arylst_wellness_param.get(i).getFutureScore()));

            double percentage;

            percentage = (float) ((min / max) * 100);

            myHolder.progressBar_wellness.setProgress((int)percentage);
            myHolder.progressBar_wellness.setMax((int)100);


        }catch (Exception e){
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount()
    {
        return arylst_wellness_param.size();
    }


}
