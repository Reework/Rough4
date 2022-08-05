package com.shamrock.reework.activity.recipe.cusion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;

import java.util.ArrayList;

public class CusionTypeCategoryAdapter extends RecyclerView.Adapter<CusionTypeCategoryAdapter.MyHolder>
{
    Context context;
    ArrayList<CusionData> list;
    CusionClickListner cusionClickListner;
    int selectedPosition=-1;
    boolean isFirstTime=true;
    public CusionTypeCategoryAdapter(Context context, ArrayList<CusionData> list)
    {
        this.context = context;
        this.list = list;
        cusionClickListner= (CusionClickListner) context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_cusionname;
        LinearLayout ll_main;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_cusionname = itemView.findViewById(R.id.txt_cusionname);
            ll_main = itemView.findViewById(R.id.ll_main);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_cusion_category, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i)
    {
        CusionData model = list.get(i);
        myHolder.txt_cusionname.setText(model.getCuisine());

        if (isFirstTime){
            if (i==0){
                myHolder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.bg_newdesignround_btn));
                myHolder.txt_cusionname.setTextColor(context.getResources().getColor(R.color.white));
//                cusionClickListner.onClickCusionItme(list.get(0).getCuisine());

            }else {
                myHolder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.round_rect));
                myHolder.txt_cusionname.setTextColor(context.getResources().getColor(R.color.black));
            }


        }else {
            if(selectedPosition==i) {
                myHolder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.bg_newdesignround_btn));
                myHolder.txt_cusionname.setTextColor(context.getResources().getColor(R.color.white));
            }
            else{
                myHolder.ll_main.setBackground(context.getResources().getDrawable(R.drawable.round_rect));
                myHolder.txt_cusionname.setTextColor(context.getResources().getColor(R.color.black));
            }

        }




        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFirstTime=false;
                cusionClickListner.onClickCusionItme(list.get(i).getCuisine());
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
