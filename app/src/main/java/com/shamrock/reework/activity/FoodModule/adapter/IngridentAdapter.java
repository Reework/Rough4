package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.RecipeResponse;

import java.util.List;

public class IngridentAdapter extends RecyclerView.Adapter<IngridentAdapter.MyHolder>
{
    Context mContext;
    List<RecipeResponse.RecipeIngrdient> list;
    public IngridentAdapter(Context context, List<RecipeResponse.RecipeIngrdient> list)
    {
        this.list = list;
        mContext = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tvMsg;
        TextView tv_ingradient;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            tvMsg = itemView.findViewById(R.id.tvMsg);
            tv_ingradient = itemView.findViewById(R.id.tv_ingradient);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adap_inter_msg, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        double qty;
        String unit, ingredients, msg;

        qty = list.get(i).getQuantity();
        unit = list.get(i).getUnit();
        ingredients = list.get(i).getIngredients();

        if (list.get(i).getRemark()!=null&&!list.get(i).getRemark().isEmpty()){
            myHolder.tv_ingradient.setText("     Remark :"+list.get(i).getRemark());

        }else {
            myHolder.tv_ingradient.setVisibility(View.GONE);
        }

//        msg = qty+" "+unit+" "+ingredients;

        if(qty==0.25){
            msg = "1/4"+" "+unit+" "+ingredients;
        }else if(qty==0.5){
            msg = "1/2"+" "+unit+" "+ingredients;
        }else if(qty==7.5){
            msg = "3/4"+" "+unit+" "+ingredients;
        }else if(qty==1.0){
            msg = "1"+" "+unit+" "+ingredients;
        }else if(qty==2.0){
            msg = "2"+" "+unit+" "+ingredients;
        }else if(qty==3.0){
            msg = "3"+" "+unit+" "+ingredients;
        }else {

            msg = String.format("%.1f",qty)+" "+unit+" "+ingredients;
        }


        if (!TextUtils.isEmpty(msg))
            myHolder.tvMsg.setText(msg);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
