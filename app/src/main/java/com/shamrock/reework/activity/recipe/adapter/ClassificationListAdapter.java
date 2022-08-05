package com.shamrock.reework.activity.recipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;
import com.shamrock.reework.activity.mybcaplan.ClsBcaData;
import com.shamrock.reework.activity.mybcaplan.OnListBcaClick;

import java.util.ArrayList;

public class ClassificationListAdapter extends BaseAdapter
{


    Context mContext;
    ArrayList<HealthCatogoryData> list;
    private static LayoutInflater inflater = null;
    OnListBcaClick onListBcaClick;
    int existingPlanID;
    public ClassificationListAdapter(Context context, ArrayList<HealthCatogoryData> list)
    {
        this.list = list;
        mContext = context;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HealthCatogoryData getItem(int i) {
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
            vi = inflater.inflate(R.layout.list_row_classification, null);
        TextView txtclassification=vi.findViewById(R.id.txtclassification);
        txtclassification.setText(list.get(i).getClassification());


        //LinearLayout ll_last = vi.findViewById(R.id.ll_last);






        return vi;
    }

}
