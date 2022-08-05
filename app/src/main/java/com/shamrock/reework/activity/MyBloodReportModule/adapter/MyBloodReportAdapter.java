package com.shamrock.reework.activity.MyBloodReportModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.MyBloodReportModule.service.BloodReportItemModel;

import java.util.ArrayList;

public class MyBloodReportAdapter extends RecyclerView.Adapter<MyBloodReportAdapter.MyHolder>
{
    Context mContext;
    ArrayList<BloodReportItemModel> list;
    int colorBlue;

    public MyBloodReportAdapter(Context context, ArrayList<BloodReportItemModel> list)
    {
        this.list = list;
        mContext = context;
        colorBlue = ContextCompat.getColor(context, R.color.colorRobinEggBlue);
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle, tvDate;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

          /*  tvTitle = itemView.findViewById(R.id.textBCA_Name);
            tvDate = itemView.findViewById(R.id.textBCA_Percent);*/
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.list_row_my_bca_report_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        try
        {
            BloodReportItemModel model = list.get(i);
            String featureName = model.getName();
            String featurePercent = model.getPercent();

            Spannable wordtoSpan = new SpannableString(featurePercent);
            wordtoSpan.setSpan(new ForegroundColorSpan(colorBlue), 0, featurePercent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            myHolder.tvTitle.setText(featureName);
            myHolder.tvDate.setText(wordtoSpan);
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
