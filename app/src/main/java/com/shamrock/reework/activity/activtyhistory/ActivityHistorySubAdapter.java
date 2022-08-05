
package com.shamrock.reework.activity.activtyhistory;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.fragment.ActivityHistoryDeleteListener;
import com.shamrock.reework.fragment.ActivtyListListener;
import com.shamrock.reework.fragment.MasterMyActivityFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityHistorySubAdapter extends RecyclerView.Adapter<ActivityHistorySubAdapter.MyHolder>
{
    private ArrayList<Activities> list;
    Context context;
    ActivityHistoryDeleteListener ActivtyListListener;
    MasterMyActivityFragment masterMyActivityFragment;

    public ActivityHistorySubAdapter(Context context, ArrayList<Activities> list, MasterMyActivityFragment masterMyActivityFragment)
    {
        this.list = list;
        this.context = context;
        ActivtyListListener= masterMyActivityFragment;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_Activity_name, txt_Activity_duration, txt_Activity_calories_burned,txt_Activity_clock_time;
        ImageView delete,edit,ivFitBit,img_activity_image;
        TextView imgMedicineEdit,imgMedicineDelete,txt_Activity_durationtime;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);

            this.txt_Activity_name = itemView.findViewById(R.id.txt_Activity_name);
            this.txt_Activity_clock_time = itemView.findViewById(R.id.txt_Activity_clock_time);
            this.txt_Activity_duration = itemView.findViewById(R.id.txt_Activity_duration);
            this.txt_Activity_calories_burned = itemView.findViewById(R.id.txt_Activity_calories_burned);
            this.txt_Activity_clock_time = itemView.findViewById(R.id.txt_Activity_clock_time);
            this.img_activity_image = itemView.findViewById(R.id.img_activity_image);
            this.imgMedicineDelete = itemView.findViewById(R.id.imgMedicineDelete);
            this.imgMedicineEdit = itemView.findViewById(R.id.imgMedicineEdit);
            this.txt_Activity_durationtime = itemView.findViewById(R.id.txt_Activity_durationtime);

            imgMedicineEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivtyListListener.updateHistoryActivty(getAdapterPosition(), list.get(getAdapterPosition()));

                }
            });

            imgMedicineDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivtyListListener.deleteHistoryActivty(getAdapterPosition(), list.get(getAdapterPosition()));

                }
            });
        }
    }

    @NonNull
    @Override
    public ActivityHistorySubAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_history_sub_activity, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHistorySubAdapter.MyHolder holder, int i)
    {
        Activities model = list.get(i);
        if (model!=null)
        {




            if (list.get(i).getTotalBurnedCalories()!=null){
                holder.txt_Activity_calories_burned.setText(""+list.get(i).getTotalBurnedCalories()+" Cal");

            }
            holder.txt_Activity_name.setText(list.get(i).getActivityName());
            String duration="";
            if (Integer.parseInt(list.get(i).getTotalMinutes())>=60){
                String strduraton=formatHoursAndMinutes(Integer.parseInt(list.get(i).getTotalMinutes()));
                if (strduraton.contains("Hour")){
                    holder.txt_Activity_duration.setText(strduraton.replace(" Hour ",":"));
                }
                if (strduraton.contains("Hours")){
                    holder.txt_Activity_duration.setText(strduraton.replace(" Hours ",":"));
                }
            }else {
                holder.txt_Activity_duration.setText(list.get(i).getTotalMinutes()+" Mins");
            }

            holder.txt_Activity_clock_time.setText(""+list.get(i).getActivityTime());
            holder.txt_Activity_durationtime.setText(""+holder.txt_Activity_duration.getText().toString()+"");

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
    public int getItemCount()
    {
        return list.size();
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }



    public String formatDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
