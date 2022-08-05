package com.shamrock.reework.activity.todaysplan.model.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.todaysplan.model.ClsPlanName;
import com.shamrock.reework.api.response.MyPlanMastersResponse;

import java.util.ArrayList;

public class MyPlanNameMasterAdapter extends BaseAdapter
{
    Context context;
    ArrayList<ClsPlanName> list;
    LayoutInflater inflter;

    public MyPlanNameMasterAdapter(Context context, ArrayList<ClsPlanName> list) {
        this.context = context;
        this.list = list;
        inflter = (LayoutInflater.from(context));
    }

    @Override
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
        convertView = inflter.inflate(R.layout.simple_spinner_item, null);
        TextView names = convertView.findViewById(R.id.spinner_textView);

        String name = list.get(position).getName();

        if (!TextUtils.isEmpty(name))
            names.setText(name);
        return convertView;
    }
}
