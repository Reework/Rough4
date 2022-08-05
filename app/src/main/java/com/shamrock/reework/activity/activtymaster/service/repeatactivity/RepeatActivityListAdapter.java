package com.shamrock.reework.activity.activtymaster.service.repeatactivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.common.DurationTimePickDialog;
import com.shamrock.reework.fragment.AddActivtyDialogFragment;
import com.shamrock.reework.fragment.MasterMyActivityFragment;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;

import org.jetbrains.annotations.NotNull;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RepeatActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TimePickerDialog.OnTimeSetListener {
    private Context context;
    MasterMyActivityFragment masterMyActivityFragment;
    ArrayList<AcivityHistory> stringArrayList;
    private DurationTimePickDialog timepickerdialog;
    int pos;
    TextView text_time_acrtivty;
    TextView text_time_clock_activity;
    private String strFromActivityTime="";
    private TimePickerDialog timepickerdialog_activity_time;
    AddActivtyDialogFragment masterMyActivityFragments;
    public RepeatActivityListAdapter(Context context) {
        this.context=context;
    }

    public RepeatActivityListAdapter(AddActivtyDialogFragment masterMyActivityFragments, ArrayList<AcivityHistory> stringArrayList) {
//        this.masterMyActivityFragment=masterMyActivityFragment;
        this.masterMyActivityFragments=masterMyActivityFragments;
        this.stringArrayList=stringArrayList;
        context=masterMyActivityFragments.getContext();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_repeat_activity, parent, false);
        return new RowViewHolder(view);
    }
    private void calltimepikerrepeat(int adapterPosition) {
        pos=adapterPosition;
        timepickerdialog = new DurationTimePickDialog(context, RepeatActivityListAdapter.this,
                0,
                1,
                true);
        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });
        timepickerdialog.setTitle("Update Time Spend in Activity");
        timepickerdialog.show();

    }



    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, final int position) {






        ((RowViewHolder) holder).txt_repeat_activity_name.setText(stringArrayList.get(position).getActivityName());
        ((RowViewHolder) holder).txt_repeat_subactivity_name.setText(stringArrayList.get(position).getSubActivityName());

        String strduraton=formatHoursAndMinutes(Integer.parseInt(stringArrayList.get(position).getTotalMinutes()));

        ((RowViewHolder) holder).txt_repeat_activity_duration.setText(strduraton);
        ((RowViewHolder) holder).txt_repeat_activity_time.setText(stringArrayList.get(position).getActivityTime());



        if (stringArrayList.get(position).isSelect()){
            ((RowViewHolder) holder). img_edit_repeat_activity_time.setBackground(context.getResources().getDrawable(R.drawable.bg_green_button_new));
            ((RowViewHolder) holder). img_edit_repeat_activity_time.setTextColor(context.getResources().getColor(R.color.white));
            ((RowViewHolder) holder). img_edit_repeat_activity_time.setText(" ADDED ");


        }else {
            ((RowViewHolder) holder). img_edit_repeat_activity_time.setBackground(context.getResources().getDrawable(R.drawable.bkgr_button_green_bordered));
            ((RowViewHolder) holder). img_edit_repeat_activity_time.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            ((RowViewHolder) holder). img_edit_repeat_activity_time.setText("   ADD   ");

        }






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
        return (totalMinutes / 60) + strhours + minutes+" Mins";
    }


    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if (strFromActivityTime.equalsIgnoreCase("yes")){

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            Format formatter;
            formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
            String lsTimeFrom = formatter.format(cal.getTime());

            text_time_clock_activity.setText(lsTimeFrom);

            stringArrayList.get(pos).setActivityTime(lsTimeFrom);
            notifyDataSetChanged();

            return;


        }else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            Format formatter;
            formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
            String lsTimeFrom = formatter.format(cal.getTime());

            int strMinutes=(hourOfDay*60)+minute;
            String toatalMin= String.valueOf((hourOfDay*60)+minute);
            stringArrayList.get(pos).setTotalMinutes(toatalMin);

             text_time_acrtivty.setText(toatalMin+" Mins");

            notifyDataSetChanged();
        }



    }

    public  class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_repeat_activity_time,txt_repeat_activity_duration,txt_repeat_subactivity_name,txt_repeat_activity_name;

        TextView btn_add_repeat_activity,img_edit_repeat_activity_time,img_edit_repeat_activity_duration;
        public RowViewHolder(View itemView) {
            super(itemView);
            txt_repeat_activity_time = itemView.findViewById(R.id.txt_repeat_activity_time);
            txt_repeat_activity_duration = itemView.findViewById(R.id.txt_repeat_activity_duration);
            txt_repeat_subactivity_name = itemView.findViewById(R.id.txt_repeat_subactivity_name);
            txt_repeat_activity_name = itemView.findViewById(R.id.txt_repeat_activity_name);
            img_edit_repeat_activity_duration = itemView.findViewById(R.id.img_edit_repeat_activity_duration);
            img_edit_repeat_activity_time = itemView.findViewById(R.id.img_edit_repeat_activity_time);
            btn_add_repeat_activity = itemView.findViewById(R.id.btn_add_repeat_activity);

            btn_add_repeat_activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!stringArrayList.get(getAdapterPosition()).isSelect()){
//                        btn_add_repeat_activity.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
//
//                        btn_add_repeat_activity.setTextColor(context.getResources().getColor(R.color.white));
                        stringArrayList.get(getAdapterPosition()).setSelect(true);
                    }else {
//                        btn_add_repeat_activity.setBackgroundColor(context.getResources().getColor(R.color.line_color));
//                        btn_add_repeat_activity.setTextColor(context.getResources().getColor(R.color.black));
                        stringArrayList.get(getAdapterPosition()).setSelect(false);
                    }

                    notifyDataSetChanged();
                }
            });
            img_edit_repeat_activity_duration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pos=getAdapterPosition();

                    final Dialog dialog = new Dialog(context,R.style.DialogTheme1);

                    dialog.setContentView(R.layout.edit_repeat_activity);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.BOTTOM;
//                    lp.windowAnimations = R.style.DialogAnimation1;
                    dialog.getWindow().setAttributes(lp);

                    Button but_add_activity=dialog.findViewById(R.id.but_add_activity);
                    but_add_activity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    TextView time1,duration1,name1;
                    time1=dialog.findViewById(R.id.time1);
                    duration1=dialog.findViewById(R.id.duration1);
                    name1=dialog.findViewById(R.id.name1);
                    name1.setText(stringArrayList.get(pos).getActivityName()+"("+stringArrayList.get(pos).getSubActivityName()+")");
                    time1.setText(stringArrayList.get(pos).getActivityTime()+" Mins");
                    duration1.setText(stringArrayList.get(pos).getTotalMinutes());

                    text_time_acrtivty=dialog.findViewById(R.id.text_time_acrtivty);
                     text_time_clock_activity=dialog.findViewById(R.id.text_time_clock_activity);

                    text_time_clock_activity.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             strFromActivityTime="yes";
                             timepickerdialog_activity_time = new TimePickerDialog(context, RepeatActivityListAdapter.this,
                                     Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                     Calendar.getInstance().get(Calendar.MINUTE),
                                     false);
                             timepickerdialog_activity_time.show();

                         }
                     });

                    text_time_acrtivty.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            strFromActivityTime="durartion";
                            calltimepikerrepeat(getAdapterPosition());
                        }
                    });

                    dialog.show();









                }
            });

            img_edit_repeat_activity_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pos=getAdapterPosition();


                    if (!stringArrayList.get(getAdapterPosition()).isSelect()){
//                        btn_add_repeat_activity.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
//
//                        btn_add_repeat_activity.setTextColor(context.getResources().getColor(R.color.white));
                        stringArrayList.get(getAdapterPosition()).setSelect(true);
                    }else {
//                        btn_add_repeat_activity.setBackgroundColor(context.getResources().getColor(R.color.line_color));
//                        btn_add_repeat_activity.setTextColor(context.getResources().getColor(R.color.black));
                        stringArrayList.get(getAdapterPosition()).setSelect(false);
                    }

                    notifyDataSetChanged();


                }
            });

        }



    }





}

//package com.shamrock.reework.activity.activtymaster.service.repeatactivity;
//
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.TimePicker;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.shamrock.R;
//import com.shamrock.reework.common.DurationTimePickDialog;
//import com.shamrock.reework.fragment.AddActivtyDialogFragment;
//import com.shamrock.reework.fragment.MasterMyActivityFragment;
//import com.shamrock.reework.model.MasterActivty.AcivityHistory;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.text.Format;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Locale;
//
//public class RepeatActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TimePickerDialog.OnTimeSetListener {
//    private Context context;
//    MasterMyActivityFragment masterMyActivityFragment;
//    ArrayList<AcivityHistory> stringArrayList;
//    private DurationTimePickDialog timepickerdialog;
//    int pos;
//    private String strFromActivityTime="";
//    private TimePickerDialog timepickerdialog_activity_time;
//    AddActivtyDialogFragment masterMyActivityFragments;
//    public RepeatActivityListAdapter(Context context) {
//        this.context=context;
//    }
//
//    public RepeatActivityListAdapter(AddActivtyDialogFragment masterMyActivityFragments, ArrayList<AcivityHistory> stringArrayList) {
////        this.masterMyActivityFragment=masterMyActivityFragment;
//        this.masterMyActivityFragments=masterMyActivityFragments;
//        this.stringArrayList=stringArrayList;
//        context=masterMyActivityFragments.getContext();
//
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_repeat_activity, parent, false);
//        return new RowViewHolder(view);
//    }
//    private void calltimepikerrepeat(int adapterPosition) {
//        pos=adapterPosition;
//        timepickerdialog = new DurationTimePickDialog(context, RepeatActivityListAdapter.this,
//                0,
//                1,
//                true);
//        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
//                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//            }
//        });
//        timepickerdialog.setTitle("Update Time Spend in Activity");
//        timepickerdialog.show();
//
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, final int position) {
//
//
//
//
//
//
//        ((RowViewHolder) holder).txt_repeat_activity_name.setText(stringArrayList.get(position).getActivityName());
//        ((RowViewHolder) holder).txt_repeat_subactivity_name.setText(stringArrayList.get(position).getSubActivityName());
//
//        String strduraton=formatHoursAndMinutes(Integer.parseInt(stringArrayList.get(position).getTotalMinutes()));
//
//        ((RowViewHolder) holder).txt_repeat_activity_duration.setText(strduraton);
//        ((RowViewHolder) holder).txt_repeat_activity_time.setText(stringArrayList.get(position).getActivityTime());
//
//
//
//        if (stringArrayList.get(position).isSelect()){
//            ((RowViewHolder) holder). btn_add_repeat_activity.setBackground(context.getResources().getDrawable(R.drawable.bg_green_button_new));
//            ((RowViewHolder) holder). btn_add_repeat_activity.setTextColor(context.getResources().getColor(R.color.white));
//            ((RowViewHolder) holder). btn_add_repeat_activity.setText(" ADDED ");
//
//
//        }else {
//            ((RowViewHolder) holder). btn_add_repeat_activity.setBackground(context.getResources().getDrawable(R.drawable.bkgr_button_green_bordered));
//            ((RowViewHolder) holder). btn_add_repeat_activity.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
//            ((RowViewHolder) holder). btn_add_repeat_activity.setText("   ADD   ");
//
//        }
//
//
//
//
//
//
//        }
//
//
//
//
//    public  String formatHoursAndMinutes(int totalMinutes) {
//        String minutes = Integer.toString(totalMinutes % 60);
//        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
//        String strhours="";
//        int hoursstr=(totalMinutes / 60);
//        if (hoursstr==1){
//            strhours=" Hour ";
//        }else {
//            strhours=" Hours ";
//
//        }
//        return (totalMinutes / 60) + strhours + minutes+" Mins";
//    }
//
//
//    @Override
//    public int getItemCount() {
//      return stringArrayList.size();
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//       if (strFromActivityTime.equalsIgnoreCase("yes")){
//
//            Calendar cal = Calendar.getInstance();
//            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
//            cal.set(Calendar.MINUTE, minute);
//            Format formatter;
//            formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
//            String lsTimeFrom = formatter.format(cal.getTime());
//
//            stringArrayList.get(pos).setActivityTime(lsTimeFrom);
//            notifyDataSetChanged();
//
//            return;
//
//
//        }else {
//            Calendar cal = Calendar.getInstance();
//            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
//            cal.set(Calendar.MINUTE, minute);
//            Format formatter;
//            formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
//            String lsTimeFrom = formatter.format(cal.getTime());
//
//            int strMinutes=(hourOfDay*60)+minute;
//            String toatalMin= String.valueOf((hourOfDay*60)+minute);
//            stringArrayList.get(pos).setTotalMinutes(toatalMin);
//            notifyDataSetChanged();
//        }
//
//
//
//    }
//
//    public  class RowViewHolder extends RecyclerView.ViewHolder {
//        private TextView txt_repeat_activity_time,txt_repeat_activity_duration,txt_repeat_subactivity_name,txt_repeat_activity_name;
//
//        TextView btn_add_repeat_activity,img_edit_repeat_activity_time,img_edit_repeat_activity_duration;
//        public RowViewHolder(View itemView) {
//            super(itemView);
//            txt_repeat_activity_time = itemView.findViewById(R.id.txt_repeat_activity_time);
//            txt_repeat_activity_duration = itemView.findViewById(R.id.txt_repeat_activity_duration);
//            txt_repeat_subactivity_name = itemView.findViewById(R.id.txt_repeat_subactivity_name);
//            txt_repeat_activity_name = itemView.findViewById(R.id.txt_repeat_activity_name);
//            img_edit_repeat_activity_duration = itemView.findViewById(R.id.img_edit_repeat_activity_duration);
//            img_edit_repeat_activity_time = itemView.findViewById(R.id.img_edit_repeat_activity_time);
//            btn_add_repeat_activity = itemView.findViewById(R.id.btn_add_repeat_activity);
//
//            btn_add_repeat_activity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    if (!stringArrayList.get(getAdapterPosition()).isSelect()){
////                        btn_add_repeat_activity.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
////
////                        btn_add_repeat_activity.setTextColor(context.getResources().getColor(R.color.white));
//                        stringArrayList.get(getAdapterPosition()).setSelect(true);
//                    }else {
////                        btn_add_repeat_activity.setBackgroundColor(context.getResources().getColor(R.color.line_color));
////                        btn_add_repeat_activity.setTextColor(context.getResources().getColor(R.color.black));
//                        stringArrayList.get(getAdapterPosition()).setSelect(false);
//                    }
//
//                    notifyDataSetChanged();
//                }
//            });
//            img_edit_repeat_activity_duration.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    strFromActivityTime="durartion";
//                    calltimepikerrepeat(getAdapterPosition());
//
////                    View modelBottomSheet = LayoutInflater.from(context).inflate(R.layout.dialog_fragment_update_activity, null);
////                    BottomSheetDialog dialog = new BottomSheetDialog(context);
////                    dialog.setContentView(modelBottomSheet);
////                    dialog.show();
//
//
//                }
//            });
//
//            img_edit_repeat_activity_time.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    pos=getAdapterPosition();
//
//                    strFromActivityTime="yes";
//                    timepickerdialog_activity_time = new TimePickerDialog(context, RepeatActivityListAdapter.this,
//                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
//                            Calendar.getInstance().get(Calendar.MINUTE),
//                            false);
//                    timepickerdialog_activity_time.show();
//
//                }
//            });
//
//        }
//
//
//
//    }
//
//
//
//
//
//}
