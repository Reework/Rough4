package com.shamrock.reework.activity.ReminderModule.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.ReminderModule.service.ReminderItem;

import java.util.ArrayList;

public class RemindersListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ReminderItem> list;
    private static LayoutInflater inflater = null;
    int colorBlue;
    TypedArray arrayDrawable, arrayText;

    static class ViewHolder {
        CheckBox checkBox;
        ImageView icon;
        TextView textName;
        TextView textClock;
        Spinner spinner;
    }

    public RemindersListAdapter(Context context, ArrayList<ReminderItem> rArrayList) {
        this.list = rArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        colorBlue = ContextCompat.getColor(mContext, R.color.colorRobinEggBlue);
        arrayDrawable = context.getResources().obtainTypedArray(R.array.reminder_icons);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ReminderItem getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public boolean isChecked(int position) {
        return list.get(position).isChecked();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        View rowView = convertView;

        // reuse views
        ViewHolder viewHolder = new ViewHolder();
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_row_reminder, null);

            viewHolder.checkBox = rowView.findViewById(R.id.checkBox_Reminder_item_name);
            viewHolder.icon = rowView.findViewById(R.id.imgView_Reminder_item);
            viewHolder.textName = rowView.findViewById(R.id.textView_Reminder_item_name);
            viewHolder.textClock = rowView.findViewById(R.id.textView_Reminder_item_clock);
            viewHolder.spinner = rowView.findViewById(R.id.spinner_Reminder_item_Times);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        final String itemStr = list.get(position).getReminderName();
        viewHolder.textName.setText(itemStr);
        viewHolder.checkBox.setChecked(list.get(position).isChecked());
        viewHolder.icon.setImageDrawable(arrayDrawable.getDrawable(position));
        viewHolder.checkBox.setTag(position);

            /*
            viewHolder.checkBox.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).checked = b;

                    Toast.makeText(getApplicationContext(),
                            itemStr + "onCheckedChanged\nchecked: " + b,
                            Toast.LENGTH_LONG).show();
                }
            });
            */

        final View finalRowView = rowView;
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newState = !list.get(position).isChecked();
                list.get(position).setChecked(newState);
                if (newState) {
                    finalRowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_stroke_blue);
//                    finalViewHolder.checkBox.setChecked(true);
                } else {
                    finalRowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_6dp);
//                    finalViewHolder.checkBox.setChecked(false);
                }
//                Toast.makeText(mContext, itemStr + "setOnClickListener\nchecked: " + newState, Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newState = !list.get(position).isChecked();
                list.get(position).setChecked(newState);
                if (newState) {
                    finalRowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_stroke_blue);
                    finalViewHolder.checkBox.setChecked(true);
                } else {
                    finalRowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_6dp);
                    finalViewHolder.checkBox.setChecked(false);
                }
//                Toast.makeText(mContext, itemStr + "setOnClickListener\nchecked: " + newState, Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newState = !list.get(position).isChecked();
                list.get(position).setChecked(newState);
                if (newState) {
                    finalRowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_stroke_blue);
                    finalViewHolder.checkBox.setChecked(true);
                } else {
                    finalRowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_6dp);
                    finalViewHolder.checkBox.setChecked(false);
                }
//                Toast.makeText(mContext, itemStr + "setOnClickListener\nchecked: " + newState, Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                list.get(position).setTimes(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        viewHolder.textClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "show Time selector(try to show 24 hrs)", Toast.LENGTH_SHORT).show();
            }
        });

        boolean isItemChecked = isChecked(position);
        viewHolder.checkBox.setChecked(isItemChecked);
        if (isItemChecked) {
            rowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_stroke_blue);
        } else {
            rowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_6dp);
        }
        return rowView;
    }
}
