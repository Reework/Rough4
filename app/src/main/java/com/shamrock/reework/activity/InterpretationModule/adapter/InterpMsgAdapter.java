package com.shamrock.reework.activity.InterpretationModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;

import java.util.ArrayList;

public class InterpMsgAdapter extends RecyclerView.Adapter<InterpMsgAdapter.MyHolder>
{
    Context mContext;
    ArrayList<String> list;

    public InterpMsgAdapter(Context context, ArrayList<String> list)
    {
        this.list = list;
        mContext = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tvMsg;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            tvMsg = itemView.findViewById(R.id.tvMsg);
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
        String msg = list.get(i);

        if (!TextUtils.isEmpty(msg))
            myHolder.tvMsg.setText(msg);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

 /*   @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.adap_inter_msg, null);

        TextView tvMsg = vi.findViewById(R.id.tvMsg);

        String msg = list.get(position);
        if (!TextUtils.isEmpty(msg))
            tvMsg.setText(msg);

        vi.setTag(position);

        return vi;
    }*/
}
