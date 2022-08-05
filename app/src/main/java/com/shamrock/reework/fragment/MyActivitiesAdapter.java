package com.shamrock.reework.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;

import java.util.ArrayList;

public class MyActivitiesAdapter extends BaseAdapter
{
    ActivtyListListener ActivtyListListener;

//    public interface ActivtyListListener {
//        void updateActivty(int position, AcivityHistory myMedicine);
//        void deleteActivty(int position, AcivityHistory myMedicine);
//    }

    private static final String SEPARATOR = " | ";
    Context mContext;
    ArrayList<AcivityHistory> list;
    private static LayoutInflater inflater = null;

    public MyActivitiesAdapter(Context context, ArrayList<AcivityHistory> subscriptionFeatureArrayList, MasterMyActivityFragment masterMyActivityFragment)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ActivtyListListener= masterMyActivityFragment;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AcivityHistory getItem(int i) {
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
            vi = inflater.inflate(R.layout.list_row_my_activity, null);

        TextView txt_Activity_calories_burned = vi.findViewById(R.id.txt_Activity_calories_burned);
        TextView txt_Activity_duration = vi.findViewById(R.id.txt_Activity_duration);
        TextView txt_Activity_name = vi.findViewById(R.id.txt_Activity_name);
        ImageView edit = vi.findViewById(R.id.imgMedicineEdit);
        ImageView delete = vi.findViewById(R.id.imgMedicineDelete);


        txt_Activity_calories_burned.setText(list.get(i).getTotalBurnedCalories());
        txt_Activity_duration.setText(list.get(i).getTotalMinutes()+"min");
        txt_Activity_name.setText(list.get(i).getActivityName());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivtyListListener.updateActivty(i, list.get(i));

                //Toast.makeText(mContext, "Edit " + i, Toast.LENGTH_SHORT).show();
//                if (mContext instanceof ActivtyListListener) {
//                    ((ActivtyListListener) mContext).updateActivty(i, list.get(i));
//                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Delete = " + i, Toast.LENGTH_SHORT).show();
                ActivtyListListener.deleteActivty(i, list.get(i));

            }
        });
        vi.setTag(i);

        return vi;
    }

    public void removeItem(int position)
    {
        list.remove(position);
        notifyDataSetChanged();
    }

}
