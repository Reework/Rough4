package com.shamrock.reework.activity.InterpretationModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shamrock.R;
import com.shamrock.reework.api.response.RescoreResponse;

import java.util.ArrayList;

public class InterpretationViewMoreAdapter extends RecyclerView.Adapter<InterpretationViewMoreAdapter.MyHolder>
{
    Context context;
    ArrayList<RescoreResponse.RescoreData> list;
    InterpMsgAdapter msgAdapter;

    public InterpretationViewMoreAdapter(Context context,  ArrayList<RescoreResponse.RescoreData> list)
    {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        RecyclerView recyclerView;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            recyclerView = (RecyclerView) itemView.findViewById(R.id.tvList);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_view_more, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        RescoreResponse.RescoreData model = list.get(i);

        ArrayList<String> msgs = model.getLstMessage();

        msgAdapter = new InterpMsgAdapter(context, msgs);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myHolder.recyclerView.setLayoutManager(layoutManager);
        myHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        myHolder.recyclerView.setAdapter(msgAdapter);

        myHolder.recyclerView.setAdapter(msgAdapter);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    /*StringBuilder sb = new StringBuilder();
        for (int index=0; index <msgs.size(); index++)
    {
        if (!TextUtils.isEmpty(msgs.get(index)))
            sb.append(msgs.get(index));
        sb.append("\n");

    }
        myHolder.tvMsg.setText(sb);*/
}
