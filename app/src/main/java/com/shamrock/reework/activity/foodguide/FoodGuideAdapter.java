package com.shamrock.reework.activity.foodguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.api.response.TipsResponce;

import java.util.ArrayList;


public class FoodGuideAdapter extends RecyclerView.Adapter<FoodGuideAdapter.MyHolder>
{
    Context context;
    String mComingFrom;
    ArrayList<FoodGuidePojo> list;
public FoodGuideAdapter(Context context, ArrayList<FoodGuidePojo> list)
        {
        this.context = context;
        this.list = list;
        }

public class MyHolder extends RecyclerView.ViewHolder {
    TextView txtMsg;

    public MyHolder(@NonNull View itemView)
    {
        super(itemView);
        this.txtMsg = (TextView) itemView.findViewById(R.id.txtMsg);

    }
}

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_tips, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i)
    {
        FoodGuidePojo model = list.get(i);

        if (model!=null)
        {
            holder.txtMsg.setText(model.getIngredientName());
            holder.txtMsg.setTextColor(context.getResources().getColor(R.color.black));


        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

