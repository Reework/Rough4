package com.shamrock.reework.activity.HealthModule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.entity.SubPlans;
import com.shamrock.reework.activity.mybcaplan.ClsBcaData;
import com.shamrock.reework.activity.mybcaplan.OnListBcaClick;

import java.util.ArrayList;

public class SubPlanListAdapter extends BaseAdapter
{


    Context mContext;
    ArrayList<SubPlans> list;
    private static LayoutInflater inflater = null;
    OnListBcaClick onListBcaClick;
    int existingPlanID;
    String desription;
    public SubPlanListAdapter(Context context, ArrayList<SubPlans> subscriptionFeatureArrayList, int existingPlanID, String desription)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.existingPlanID=existingPlanID;
        this.desription=desription;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SubPlans getItem(int i) {
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
            vi = inflater.inflate(R.layout.row_subplan, null);


        //LinearLayout ll_last = vi.findViewById(R.id.ll_last);


        TextView txt_device_name = vi.findViewById(R.id.txt_plan_name);
        TextView txt_plan_description = vi.findViewById(R.id.txt_plan_description);



        txt_device_name.setSelected(true);
        txt_device_name.setText(list.get(i).getSubPlanName());
        txt_plan_description.setText(desription);





//        convertView.findViewById(R.id.txt_View_bca_details).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((ListView).viewGroup)
//            }
//        });




        return vi;
    }

}
