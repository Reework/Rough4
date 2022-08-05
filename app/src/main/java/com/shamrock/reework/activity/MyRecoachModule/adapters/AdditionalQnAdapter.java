package com.shamrock.reework.activity.MyRecoachModule.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.OnHealthCatoryClick;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;
import com.shamrock.reework.activity.MyRecoachModule.activity.AdditionalDetails;

import java.util.ArrayList;

public class AdditionalQnAdapter extends RecyclerView.Adapter<AdditionalQnAdapter.MyHolder>
{
    Context context;
    ArrayList<AdditionalDetails> list;
    int selectedPosition=-1;
    public AdditionalQnAdapter(Context context, ArrayList<AdditionalDetails> list)
    {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_add_qn,txt_add_answer;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_add_qn = itemView.findViewById(R.id.txt_add_qn);
            txt_add_answer = itemView.findViewById(R.id.txt_ans);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_additional_qn, viewGroup, false);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i)
    {
        AdditionalDetails model = list.get(i);
        if (!model.getQuestion().trim().equalsIgnoreCase("IDA No.")){
            myHolder.txt_add_qn.setVisibility(View.VISIBLE);
            myHolder.txt_add_qn.setText(model.getQuestion().replaceAll("\\r\\n|\\r|\\n", " "));
            myHolder.txt_add_answer.setText(model.getAnswer());
        }else {
            myHolder.txt_add_qn.setVisibility(View.GONE);

        }







    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



}
