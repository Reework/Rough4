package com.shamrock.reework.activity.tips;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;

import java.util.ArrayList;


public class ClsSleepTipsAdapter extends RecyclerView.Adapter<ClsSleepTipsAdapter.MyHolder>
{
    Context context;
    String mComingFrom;
    ArrayList<Data> list;
public ClsSleepTipsAdapter(Context context, ArrayList<Data> list, String commingFrom)
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
        Data model = list.get(i);

        if (model!=null)
        {
            if(mComingFrom!=null){
                if (!list.get(i).getDescriptions().trim().isEmpty()){
                    holder.txtMsg.setText(list.get(i).getDescriptions());

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

