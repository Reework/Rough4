package com.shamrock.reework.activity.mindhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.common.Data;
import com.shamrock.reework.fragment.MasterMindFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClsMindHistoryAdapter extends BaseAdapter
{


    Context mContext;
    ArrayList<Data> list;
    private static LayoutInflater inflater = null;
    OnEditMindClick onEditWaterClick;

    public ClsMindHistoryAdapter(MasterMindFragment context, ArrayList<Data> subscriptionFeatureArrayList)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context.getContext();
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onEditWaterClick= (OnEditMindClick) context;
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
        TextView sleep_date = vi.findViewById(R.id.sleep_date);
        ImageView img_edit_sleep = vi.findViewById(R.id.img_edit_sleep_new);
        final Data song = list.get(i);
        String actualhours = "";
        if (song.getActualMindStatus()!=null){
            actualhours=    song.getActualMindStatus();

        }
        String schedulehours ="";
        if (song.getScheduledMindStatus()!=null){
            schedulehours=    song.getScheduledMindStatus();

        }


                   sleep_date.setText(  "Date                                 " +
                           ": "+formatDates(song.getStatusDate()));

        if (actualhours.equalsIgnoreCase("neutral")){
            actualhours="Neutral";
        }

        if (actualhours.equalsIgnoreCase("happy")){
            actualhours="Happy";
        }

        if (actualhours.equalsIgnoreCase("stress")){
            actualhours="Stressed";
        }
        txt_schedule_sleep_hous.setVisibility(View.GONE);
        txt_actual_sleep_hous.setText(  "Actual Mind Status        :  "+actualhours);
        txt_schedule_sleep_hous.setText("Schedule Mind Status   :  "+schedulehours);

        img_edit_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditWaterClick.getEditMindPosition(song);
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
