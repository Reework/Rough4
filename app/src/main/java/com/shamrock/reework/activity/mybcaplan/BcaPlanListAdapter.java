package com.shamrock.reework.activity.mybcaplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shamrock.R;

import java.util.ArrayList;

public class BcaPlanListAdapter extends BaseAdapter
{


    Context mContext;
    ArrayList<ClsBcaData> list;
    private static LayoutInflater inflater = null;
    OnListBcaClick onListBcaClick;
    int existingPlanID;
    public BcaPlanListAdapter(Context context, ArrayList<ClsBcaData> subscriptionFeatureArrayList, int existingPlanID)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context;
        onListBcaClick= (OnListBcaClick) context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.existingPlanID=existingPlanID;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ClsBcaData getItem(int i) {
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
            vi = inflater.inflate(R.layout.list_row_device_list_new, null);


        //LinearLayout ll_last = vi.findViewById(R.id.ll_last);


        TextView txt_device_name = vi.findViewById(R.id.txt_device_name);

        TextView txt_select_bca_plan = vi.findViewById(R.id.txt_select_bca_plan);
        TextView txt_View_bca_details = vi.findViewById(R.id.txt_View_bca_details);
        ImageView img_device_image = vi.findViewById(R.id.img_device_image);
        txt_device_name.setText(list.get(i).getPlanname());
        img_device_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_menu_my_plans));

        if (list.get(i).getPlanId()==existingPlanID){
            txt_select_bca_plan.setVisibility(View.GONE);
            txt_View_bca_details.setVisibility(View.GONE);
            img_device_image.setVisibility(View.GONE);
            txt_device_name.setVisibility(View.GONE);
        }else {
            txt_select_bca_plan.setVisibility(View.VISIBLE);
            txt_View_bca_details.setVisibility(View.VISIBLE);
            img_device_image.setVisibility(View.VISIBLE);
            txt_device_name.setVisibility(View.VISIBLE);
        }

        txt_View_bca_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListBcaClick.getClickBCaPos(i);
            }
        });
        txt_select_bca_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListBcaClick.selectPlanItem(list.get(i).getPlanId(),i);
            }
        });


//        convertView.findViewById(R.id.txt_View_bca_details).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((ListView).viewGroup)
//            }
//        });




        return vi;
    }

}
