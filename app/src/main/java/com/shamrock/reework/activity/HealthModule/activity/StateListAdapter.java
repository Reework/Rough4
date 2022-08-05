package com.shamrock.reework.activity.HealthModule.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.mybcaplan.ClsBcaData;
import com.shamrock.reework.activity.mybcaplan.OnListBcaClick;
import com.shamrock.reework.api.response.State;

import java.util.ArrayList;

public class StateListAdapter extends BaseAdapter
{


    Context mContext;
    ArrayList<State> list;
    private static LayoutInflater inflater = null;
    OnListBcaClick onListBcaClick;
    int existingPlanID;
    public StateListAdapter(Context context, ArrayList<State> subscriptionFeatureArrayList)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public State getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_state, null);




        TextView txt_device_name = vi.findViewById(R.id.txt_device_name);

        TextView txt_select_bca_plan = vi.findViewById(R.id.txt_select_bca_plan);




        return vi;
    }

}
