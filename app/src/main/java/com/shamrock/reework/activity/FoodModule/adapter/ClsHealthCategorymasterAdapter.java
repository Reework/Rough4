package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;
import com.shamrock.reework.api.response.FoodSnippingResp;

import java.util.ArrayList;
import java.util.List;

public class ClsHealthCategorymasterAdapter extends RecyclerView.Adapter<ClsHealthCategorymasterAdapter.MyHolder>
{
    Context context;
    ArrayList<HealthCatogoryData> list;
    OnHealthCatoryClick onHealthCatoryClick;
    int selectedPosition=-1;
    boolean isFirstTime=true;
    public ClsHealthCategorymasterAdapter(Context context, ArrayList<HealthCatogoryData> list)
    {
        this.context = context;
        this.list = list;
        onHealthCatoryClick= (OnHealthCatoryClick) context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_health_category;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_health_category = itemView.findViewById(R.id.txt_health_category);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_health_category, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i)
    {
        HealthCatogoryData model = list.get(i);
        myHolder.txt_health_category.setText(model.getClassification());

        if (isFirstTime){
            if (i==0){
                myHolder.txt_health_category.setBackgroundColor(Color.parseColor("#00f88b"));

            }else {
                myHolder.txt_health_category.setBackgroundColor(Color.parseColor("#ffffff"));

            }

        }else {
            if(selectedPosition==i)
                myHolder.txt_health_category.setBackgroundColor(Color.parseColor("#00f88b"));
            else
                myHolder.txt_health_category.setBackgroundColor(Color.parseColor("#ffffff"));
        }




        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFirstTime=false;
                onHealthCatoryClick.getHathCatogoryPosName(list.get(i).getClassification(),true);
                selectedPosition=i;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



}
