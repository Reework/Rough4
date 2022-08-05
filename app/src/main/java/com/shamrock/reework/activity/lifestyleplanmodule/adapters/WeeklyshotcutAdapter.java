package com.shamrock.reework.activity.lifestyleplanmodule.adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.shamrock.R;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanActivity;
import com.shamrock.reework.activity.lifestyleplanmodule.Plan;
import com.shamrock.reework.api.response.MyPlanMastersResponse;

import java.util.ArrayList;
import java.util.Calendar;

public class WeeklyshotcutAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Plan> list;
    LayoutInflater inflter;

    public WeeklyshotcutAdapter(Context context, ArrayList<Plan> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = inflter.inflate(R.layout.row_shotcut_week_activity, null);

        final TextView txt_edit_weekyl_shot_cut_time=convertView.findViewById(R.id.txt_edit_weekyl_shot_cut_time);
        txt_edit_weekyl_shot_cut_time.setText(list.get(position).getActivityTime());
        txt_edit_weekyl_shot_cut_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(txt_edit_weekyl_shot_cut_time,position);
            }
        });



        final TextView txt_activity_shotcut_add = convertView.findViewById(R.id.txt_activity_shotcut_add);
        TextView txt_activity_shotcut_name = convertView.findViewById(R.id.txt_activity_shotcut_name);
        txt_activity_shotcut_name.setSelected(true);
        String name = list.get(position).getActivityName();

        txt_activity_shotcut_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!list.get(position).isAdded()){
                    list.get(position).setAdded(true);
                    txt_activity_shotcut_add.setBackgroundColor(context.getResources().getColor(R.color.dark_grey_blue));
                    txt_activity_shotcut_add.setTextColor(context.getResources().getColor(R.color.white));


                }else {
                    list.get(position).setAdded(false);
                    txt_activity_shotcut_add.setBackgroundColor(context.getResources().getColor(R.color.line_color));
                    txt_activity_shotcut_add.setTextColor(context.getResources().getColor(R.color.black));

                }
            }
        });


        if (!TextUtils.isEmpty(name))
            txt_activity_shotcut_name.setText(name);
        return convertView;
    }

    private void showTimePicker(final TextView txt_edit_weekyl_shot_cut_time, final int position) {
        final int[] mHour = new int[1];
        final int[] mMinute = new int[1];

        final Calendar c = Calendar.getInstance();
        mHour[0] = c.get(Calendar.HOUR_OF_DAY);
        mMinute[0] = c.get(Calendar.MINUTE);
        final String[] timeFormat = new String[1];
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            timeFormat[0] = "AM";
                        } else if (hourOfDay == 12) {
                            timeFormat[0] = "PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            timeFormat[0] = "PM";
                        } else {
                            timeFormat[0] = "AM";
                        }

                        if (minute<10){
                            String selectedTime = hourOfDay + ":0" + minute + " " + timeFormat[0];
                            txt_edit_weekyl_shot_cut_time.setText(selectedTime);
                            list.get(position).setActivityTime(selectedTime);
                        }else {
                            String selectedTime = hourOfDay + ":" + minute + " " + timeFormat[0];
                            txt_edit_weekyl_shot_cut_time.setText(selectedTime);
                            list.get(position).setActivityTime(selectedTime);

                        }




                    }
                }, mHour[0], mMinute[0], false);
        timePickerDialog.show();
    }
}
