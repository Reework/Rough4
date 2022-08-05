package com.shamrock.reework.activity.ReminderModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.ReminderResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRemindersAdapter extends RecyclerView.Adapter<MyRemindersAdapter.MyHolder>
{
    Context context;
    ArrayList<ReminderResponse.ReminderData> list;
    int frequency = 0;
    MyReminderListener listener;

    public interface MyReminderListener
    {
        public void onDelete(int pos, ReminderResponse.ReminderData model);
        public void onEdit(int pos, ReminderResponse.ReminderData model);
    }

    public MyRemindersAdapter(Context context, ArrayList<ReminderResponse.ReminderData> list, MyReminderListener listener)
    {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemSelectedListener
    {
        TextView tvActivity, tvTime, tvFrequency;
        TextView  ivDelete, ivEdit;
        ImageView ivActivity;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvActivity = itemView.findViewById(R.id.tvActivity);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvFrequency = itemView.findViewById(R.id.tvFrequency);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivActivity = itemView.findViewById(R.id.imageView_MyReminder_icon);
            ivDelete = itemView.findViewById(R.id.ivClose);

            ivEdit.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ivEdit:
                    listener.onEdit(getAdapterPosition(), list.get(getAdapterPosition()));
                    break;

                case R.id.ivClose:
                    listener.onDelete(getAdapterPosition(), list.get(getAdapterPosition()));
                    break;
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            frequency = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_my_reminder_new, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        try {
        ReminderResponse.ReminderData model = list.get(i);
            if (model.getActivityType().equalsIgnoreCase("Medicine")){
                myHolder.ivActivity.setImageResource(R.drawable.medicinenew);

            }

        if (model.getActivityType().equalsIgnoreCase("Activity")){
            myHolder.ivActivity.setImageResource(R.drawable.reminderactivitynew);
        }
            if (model.getActivityType().equalsIgnoreCase("Water")){
                myHolder.ivActivity.setImageResource(R.drawable.reminderwaternew);
            }

            if (model.getActivityType().equalsIgnoreCase("Sleep")){
                myHolder.ivActivity.setImageResource(R.drawable.remindersleepnew);
            }

            if (model.getActivityType().equalsIgnoreCase("Food")){
                myHolder.ivActivity.setImageResource(R.drawable.reminderfoodnew);
            }
            if (model.getActivityType().equalsIgnoreCase("Covid Yoga")){
                myHolder.ivActivity.setImageResource(R.drawable.meditation);

            }
            if (model.getActivityType().equalsIgnoreCase("Weight")){
                myHolder.ivActivity.setImageResource(R.drawable.weightnew);
            }

        if (!TextUtils.isEmpty(model.getActivity()))
            myHolder.tvActivity.setSelected(true);
            myHolder.tvActivity.setText(model.getActivity());


            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");



            Date _24HourDt =null;


        List<String> strTime = model.getReminderTime();

        if(strTime!=null){
            StringBuilder sb = new StringBuilder();
           for(int j=0;j<strTime.size();j++){
              if(strTime.size()-1==j){
                  _24HourDt = _24HourSDF.parse(strTime.get(j).toString());

                  String abc[]=_12HourSDF.format(_24HourDt).split(" ");

if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
    sb.append(abc[0]+" AM");
                  }else{
    sb.append(abc[0]+" PM");
                  }


              }else{
                  _24HourDt = _24HourSDF.parse(strTime.get(j).toString());

                  String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                  if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
                      sb.append(abc[0]+" AM"+",");
                  }else{
                      sb.append(abc[0]+" PM"+",");
                  }



//                  sb.append(_12HourSDF.format(_24HourDt).toString()+",");
              }
           }
           /* String strTimeFinal = TextUtils.join(" ", strTime);*/
            myHolder.tvTime.setText(sb.toString());
        }
      /*  if (!TextUtils.isEmpty(strTime))
        {
            String time = formatTime(strTime);

            if (!TextUtils.isEmpty(time))
                myHolder.tvTime.setText(time);
        }*/


          if (model.getFrequency() > 1){
              myHolder.tvFrequency.setText(model.getFrequency() + " times");

          }else {
              myHolder.tvFrequency.setText(model.getFrequency() + " time");

          }
      }catch (Exception e){
          e.printStackTrace();
      }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public String formatTime(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("h:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "";
    }





    /*=====================================================================================================*/

   /* public interface IsEmptyListListener
    {
        void isEmptyList(boolean isEmpty);
    }

    Context mContext;
    ArrayList<ReminderItem> list;
    private static LayoutInflater inflater = null;
    int colorBlue;
    TypedArray arrayDrawable, arrayText;
    IsEmptyListListener isEmptyListListener;

    static class ViewHolder {
        CheckBox checkBox;
        ImageView icon_ReminderType, icon_Edit, icon_Delete;
        TextView textName;
        TextView textClock;
        Spinner spinner;
        LinearLayout linearLayout_DetailView, linearLayout_EditView;
        Button btnSave;
    }

    public MyRemindersAdapter(Context context, ArrayList<ReminderItem> rArrayList)
    {
        this.list = rArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        colorBlue = ContextCompat.getColor(mContext, R.color.colorRobinEggBlue);
        arrayDrawable = context.getResources().obtainTypedArray(R.array.reminder_icons);

        if (context instanceof IsEmptyListListener)
        {
            isEmptyListListener = (IsEmptyListListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnHealth1InteractionListener");
        }
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
    public View getView(final int position, View convertView, ViewGroup viewGroup)
    {

        View rowView = convertView;

        // reuse views
        ViewHolder viewHolder = new ViewHolder();
        if (rowView == null)
        {
            rowView = inflater.inflate(R.layout.list_row_my_reminder, null);

            viewHolder.textName = rowView.findViewById(R.id.textView_MyReminder_Title);
            viewHolder.icon_ReminderType = rowView.findViewById(R.id.imageView_MyReminder_icon);
            viewHolder.icon_Edit = rowView.findViewById(R.id.imgMyReminder_Edit);
            viewHolder.icon_Delete = rowView.findViewById(R.id.imgMyReminder_Delete);
            viewHolder.linearLayout_DetailView = rowView.findViewById(R.id.linLay_MyReminder_DetailView);
            viewHolder.linearLayout_EditView = rowView.findViewById(R.id.linLay_MyReminder_EditView);
            viewHolder.btnSave = rowView.findViewById(R.id.buttonMyReminder_Save);
//            viewHolder.textName = rowView.findViewById(R.id.textView_Reminder_item_name);
//            viewHolder.textClock = rowView.findViewById(R.id.textView_Reminder_item_clock);
//            viewHolder.spinner = rowView.findViewById(R.id.spinner_Reminder_item_Times);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.icon_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalViewHolder.linearLayout_EditView.setVisibility(View.GONE);
                finalViewHolder.linearLayout_DetailView.setVisibility(View.VISIBLE);
            }
        });
        viewHolder.icon_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
                if (list.isEmpty())
                {
                    sendListMessage(true);
                }
            }
        });

        viewHolder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalViewHolder.linearLayout_EditView.setVisibility(View.VISIBLE);
                finalViewHolder.linearLayout_DetailView.setVisibility(View.GONE);
            }
        });
        final String itemStr = list.get(position).getReminderName();
        int imagePosition = list.get(position).getReminderIcon();
        viewHolder.textName.setText(itemStr);
        viewHolder.icon_ReminderType.setImageDrawable(arrayDrawable.getDrawable(imagePosition));
//        viewHolder.checkBox.setTag(position);
//
//
//        final View finalRowView = rowView;
//        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean newState = !list.get(position).isChecked();
//                list.get(position).setChecked(newState);
//                if (newState) {
//                    finalRowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_stroke_blue);
//                } else {
//                    finalRowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_6dp);
//                }
////                Toast.makeText(mContext, itemStr + "setOnClickListener\nchecked: " + newState, Toast.LENGTH_LONG).show();
//            }
//        });
//        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                list.get(position).setTimes(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        viewHolder.textClock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "show Time selector(try to show 24 hrs)", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        boolean isItemChecked = isChecked(position);
//        viewHolder.checkBox.setChecked(isItemChecked);
//        if (isItemChecked) {
//            rowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_stroke_blue);
//        } else {
//            rowView.setBackgroundResource(R.drawable.bkgr_white_card_radius_6dp);
//        }
        return rowView;
    }

    public void sendListMessage(boolean isError)
    {
        if (isEmptyListListener != null)
        {
            isEmptyListListener.isEmptyList(isError);
        }
    }*/
}
