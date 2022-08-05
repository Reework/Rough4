package com.shamrock.reework.activity.weighthistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.activity.WeightModule.activity.Data;
import com.shamrock.reework.activity.WeightModule.adapters.WeightHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClsWeightHistoryAdapter extends BaseAdapter
{

    public interface MedicineListListener {
        void updateMedicine(int position, MyMedicine myMedicine);
        void deleteMedicine(int position, MyMedicine myMedicine);
    }

    private static final String SEPARATOR = " | ";
    Context mContext;
    ArrayList<Data> list;
    private static LayoutInflater inflater = null;
    OnEditWeightClick onEditWaterClick;

    public ClsWeightHistoryAdapter(Context context, ArrayList<WeightHistory> subscriptionFeatureArrayList)
    {
//        this.list = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onEditWaterClick= (OnEditWeightClick) context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Data getItem(int i) {
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
            vi = inflater.inflate(R.layout.list_row_my_sleep_history, null);

        TextView txt_actual_sleep_hous = vi.findViewById(R.id.txt_actual_sleep_hous);
        TextView txt_schedule_sleep_hous = vi.findViewById(R.id.txt_schedule_sleep_hous);
        txt_schedule_sleep_hous.setVisibility(View.GONE);
        TextView sleep_date = vi.findViewById(R.id.sleep_date);
        ImageView img_edit_sleep = vi.findViewById(R.id.img_edit_sleep_new);
        final Data song = list.get(i);
        String actualhours = "";












                   sleep_date.setText("Date    : "+formatDates(song.getCreatedOn()));












        txt_actual_sleep_hous.setText("Weight : "+song.getWeight());

        img_edit_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditWaterClick.getEditWtPosition(song);
            }
        });

        StringBuilder sb = new StringBuilder();

//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(mContext, "Edit " + i, Toast.LENGTH_SHORT).show();
//                if (mContext instanceof MedicineListListener) {
//
////                    ((MedicineListListener) mContext).updateMedicine(i, list.get(i));
//                }
//            }
//        });

//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(mContext, "Delete = " + i, Toast.LENGTH_SHORT).show();
//                if (mContext instanceof MedicineListListener) {
////                    ((MedicineListListener) mContext).deleteMedicine(i, list.get(i));
//                }
//            }
//        });
        vi.setTag(i);

        return vi;
    }

    public void removeItem(int position)
    {
        list.remove(position);
        notifyDataSetChanged();
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }


}
