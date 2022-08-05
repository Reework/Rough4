package com.shamrock.reework.activity.HealthModule.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionData;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.OnRadioBtnYesClick;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.ClsHealthobjective;

import java.util.ArrayList;
import java.util.List;

//row_plan_list
public class HealthobjplanlistAdapter extends BaseAdapter {

    Context mContext;
    List<String> list=new ArrayList<>();
    private static LayoutInflater inflater = null;
    int colorBlue;
    TypedArray arrayDrawable, arrayText;
    OnRadioBtnYesClick onRadioBtnYesClick;

    class ViewHolder {

        TextView service_tv;

    }

    public HealthobjplanlistAdapter(Context context, List<String> rArrayList) {
        mContext = context;
        list=rArrayList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        View rowView = convertView;

        // reuse views
        ViewHolder viewHolder = new ViewHolder();
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.row_plan_list, null);
            TextView service_tv=rowView.findViewById(R.id.service_tv);

            service_tv.setText(list.get(position));





        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }



        return rowView;
    }
}
