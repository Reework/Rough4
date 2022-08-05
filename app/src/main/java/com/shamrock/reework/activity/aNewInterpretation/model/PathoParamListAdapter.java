package com.shamrock.reework.activity.aNewInterpretation.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.shamrock.R;

import java.util.ArrayList;

public class
PathoParamListAdapter extends RecyclerView.Adapter<PathoParamListAdapter.MyHolder>
{

    Context context;

    ArrayList<PathoParams> arylst_wellness_param;
    public PathoParamListAdapter(Context context, ArrayList<PathoParams> arylst_wellness_param)
    {
        this.context = context;
        this.arylst_wellness_param=arylst_wellness_param;

    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_user,img_testominal;
        TextView txt_wellness_qn,txt_wellness_score_max,txt_wellness_score;
        ProgressBar progressBar_wellness;
        TextView txt_rmarks,txt_future_score,txt_percent;
        DecoView arcView;
        int series1Index;
        SeriesItem seriesItem1;

        CircularProgressBar progress_circular_consumed;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            txt_wellness_qn=itemView.findViewById(R.id.txt_wellness_qn);
//            txt_wellness_score_max=itemView.findViewById(R.id.txt_wellness_score_max);
//            txt_wellness_score=itemView.findViewById(R.id.txt_wellness_score);
//            progressBar_wellness=itemView.findViewById(R.id.progressBar_wellness);
//            txt_future_score=itemView.findViewById(R.id.txt_future_score);
            txt_rmarks=itemView.findViewById(R.id.txt_rmarks);
            txt_percent=itemView.findViewById(R.id.txt_percent);

//            arcView = (DecoView)itemView.findViewById(R.id.dynamicArcView);
            progress_circular_consumed = itemView.findViewById(R.id.progress_circular_consumed);

//            arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
//                    .setRange(0, 100, 100)
//                    .setInitialVisibility(false)
//                    .setLineWidth(6f)
//                    .build());
//
//            seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
//                    .setRange(0, 100, 0)
//                    .setLineWidth(6f)
//                    .build();
//
//            series1Index = arcView.addSeries(seriesItem1);
//            arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
//                    .setDelay(10)
//                    .setDuration(20)
//                    .build());
        }


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public PathoParamListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_wellness_param_new1, viewGroup, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PathoParamListAdapter.MyHolder myHolder, int i)
    {







        try {
//            myHolder.txt_future_score.setText("Future Score : "+arylst_wellness_param.get(i).getFutureScore());
            if (arylst_wellness_param.get(i).getRemark()!=null){
                myHolder.txt_rmarks.setText("Remark : "+arylst_wellness_param.get(i).getRemark());

            }else {
                myHolder.txt_rmarks.setText("Remark : ");

            }
            myHolder.txt_wellness_qn.setText(arylst_wellness_param.get(i).getTestName());
//            myHolder.txt_wellness_score.setText(arylst_wellness_param.get(i).getCurrentScore());
//            myHolder.txt_wellness_score_max.setText(arylst_wellness_param.get(i).getFutureScore());



            double min= Double.parseDouble(arylst_wellness_param.get(i).getCurrentScore());
            double max= Double.parseDouble(arylst_wellness_param.get(i).getFutureScore());

            double percentage;

            percentage = (float) ((min / max) * 100);

//            myHolder.progressBar_wellness.setProgress((int)percentage);
//            myHolder.progressBar_wellness.setMax((int)100);
//            myHolder.arcView.addEvent(new DecoEvent.Builder((float) percentage).setIndex(myHolder.series1Index).setDelay(10).build());

            myHolder.txt_percent.setText(String.valueOf(Math.round((percentage))));

            myHolder.progress_circular_consumed.setProgress((float) percentage);
            myHolder.progress_circular_consumed.setProgressMax(100);



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
