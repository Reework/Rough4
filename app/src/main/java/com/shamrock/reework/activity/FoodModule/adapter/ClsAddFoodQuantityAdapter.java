package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;

import java.util.ArrayList;

public class ClsAddFoodQuantityAdapter extends RecyclerView.Adapter<ClsAddFoodQuantityAdapter.MyHolder>
{
    Context context;
    ArrayList<String> list;
    OnHealthCatoryClick onHealthCatoryClick;
    int selectedPosition=-1;
    boolean isFirstTime=true;
    public ClsAddFoodQuantityAdapter(Context context, ArrayList<String> list)
    {
        this.context = context;
        this.list = list;
//        onHealthCatoryClick= (OnHealthCatoryClick) context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_health_category;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_health_category = itemView.findViewById(R.id.txt_quantity_add);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_add_quantity, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i)
    {
        String model = list.get(i);
        myHolder.txt_health_category.setText(model.toString());

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
//                onHealthCatoryClick.getHathCatogoryPosName(list.get(i).getClassification(),true);
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
