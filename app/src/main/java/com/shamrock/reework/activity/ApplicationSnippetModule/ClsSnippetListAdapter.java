package com.shamrock.reework.activity.ApplicationSnippetModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.tips.Data;

import java.util.ArrayList;


public class ClsSnippetListAdapter extends RecyclerView.Adapter<ClsSnippetListAdapter.MyHolder>
{
    Context context;
    String mComingFrom;
    ArrayList<SnippetData> list;
public ClsSnippetListAdapter(Context context, ArrayList<SnippetData> list)
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
        SnippetData model = list.get(i);

        if (model!=null)
        {
            if (!list.get(i).getMessage().trim().isEmpty()){
                holder.txtMsg.setText(list.get(i).getMessage());

            }


        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

