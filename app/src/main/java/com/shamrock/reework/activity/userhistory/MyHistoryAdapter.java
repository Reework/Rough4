package com.shamrock.reework.activity.userhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.activity.sleepmodule.OnEditSleepClick;
import com.shamrock.reework.activity.sleepmodule.SleepActivities;
import com.shamrock.reework.activity.sleepmodule.SleepActivityCustom;
import com.shamrock.reework.fragment.MasterSleepFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyHistoryAdapter extends BaseAdapter
{

    public interface MedicineListListener {
        void updateMedicine(int position, MyMedicine myMedicine);
        void deleteMedicine(int position, MyMedicine myMedicine);
    }

    private static final String SEPARATOR = " | ";
    Context mContext;
//    ArrayList<Data> list;
    ArrayList<SleepActivityCustom> list;
    private static LayoutInflater inflater = null;
    OnEditSleepClick OnEditSleepClick_lstner;

    public MyHistoryAdapter(MasterSleepFragment context, ArrayList<SleepActivityCustom> subscriptionFeatureArrayList)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context.getContext();
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        OnEditSleepClick_lstner= context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SleepActivityCustom getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public  String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" Hour ";
        }else {
            strhours=" Hours ";

        }


        String submittime="";
        if (hoursstr>0){
            submittime= (totalMinutes / 60) + strhours + minutes+" Mins";

        }else {

            submittime=  minutes+" Mins";

        }


        return submittime;


    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.row_new_sleep,null);

        TextView txt_actual_sleep_hous = vi.findViewById(R.id.txt_actual_sleep_hous);
        TextView txt_schedule_sleep_hous = vi.findViewById(R.id.txt_schedule_sleep_hous);
        TextView sleep_date = vi.findViewById(R.id.sleep_date);
        ImageView img_edit_sleep = vi.findViewById(R.id.img_edit_sleep_new);
        ImageView img_delete_sleep_new = vi.findViewById(R.id.img_delete_sleep_new);
        final SleepActivityCustom song = list.get(i);
        String actualhours = song.getStartTime();
        String schedulehours = song.getEndTime();

                sleep_date.setText(song.getDateSleepMain());
//                   sleep_date.setText(  "Date                                 " +
//                           ": "+formatDates(song);

        txt_actual_sleep_hous.setText(  "Start Time : "+actualhours);
        txt_schedule_sleep_hous.setText("End Time   :  "+schedulehours);

        img_edit_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnEditSleepClick_lstner.getEditSleepPosition(song);
            }
        });
        img_delete_sleep_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnEditSleepClick_lstner.onDeleteSleep(song);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }




}
