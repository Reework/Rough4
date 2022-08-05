package com.shamrock.reework.activity.spirituallibrary.adapter;

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
import com.shamrock.reework.activity.spirituallibrary.listenres.OnSpiritualTypeCLick;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.SpiritualTypeData;

import java.util.ArrayList;

public class SpiritualTypeCategoryAdapter extends RecyclerView.Adapter<SpiritualTypeCategoryAdapter.MyHolder>
{
    Context context;
    ArrayList<SpiritualTypeData> list;
    OnSpiritualTypeCLick onHealthCatoryClick;
    int selectedPosition=-1;
    boolean isFirstTime=true;
    public SpiritualTypeCategoryAdapter(Context context, ArrayList<SpiritualTypeData> list)
    {
        this.context = context;
        this.list = list;
        onHealthCatoryClick= (OnSpiritualTypeCLick) context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_spiritual_category;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_spiritual_category = itemView.findViewById(R.id.txt_spiritual_category);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_spiritaul_category, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i)
    {
        SpiritualTypeData model = list.get(i);
        myHolder.txt_spiritual_category.setText(model.getLibraryName());

        if (isFirstTime){
            if (i==0){
                myHolder.txt_spiritual_category.setBackgroundColor(Color.parseColor("#00e1b1"));

            }else {
                myHolder.txt_spiritual_category.setBackgroundColor(Color.parseColor("#ffffff"));

            }

        }else {
            if(selectedPosition==i)
                myHolder.txt_spiritual_category.setBackgroundColor(Color.parseColor("#00e1b1"));
            else
                myHolder.txt_spiritual_category.setBackgroundColor(Color.parseColor("#ffffff"));
        }




        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFirstTime=false;
                onHealthCatoryClick.getId(list.get(i).getId(),list.get(i).getLibraryName());
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
