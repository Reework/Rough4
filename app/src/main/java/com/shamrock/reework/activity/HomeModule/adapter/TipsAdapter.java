package com.shamrock.reework.activity.HomeModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.TipsResponce;

import java.util.ArrayList;


public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.MyHolder>
{
    Context context;
    String mComingFrom;
    ArrayList<TipsResponce.Datum> list;
public TipsAdapter(Context context, ArrayList<TipsResponce.Datum> list,String commingFrom)
        {
        this.context = context;
        this.list = list;
        this.mComingFrom = commingFrom;
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
        TipsResponce.Datum model = list.get(i);

        if (model!=null)
        {
            if(mComingFrom!=null)
            if(mComingFrom.equalsIgnoreCase("MasterSleepFragment")){
                if (!list.get(i).getSleepTips().trim().isEmpty()){
                    holder.txtMsg.setText(list.get(i).getSleepTips());

                }
            }else if(mComingFrom.equalsIgnoreCase("MasterMindFragment")){
                if (!list.get(i).getMindTips().trim().isEmpty()){
                    holder.txtMsg.setText(list.get(i).getMindTips());

                }
            }

        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

