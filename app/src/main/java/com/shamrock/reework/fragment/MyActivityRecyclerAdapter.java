package com.shamrock.reework.fragment;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MyActivityRecyclerAdapter extends RecyclerView.Adapter<MyActivityRecyclerAdapter.MyHolder>
{
    ActivtyListListener ActivtyListListener;



    private static final String SEPARATOR = " | ";
    Context mContext;
    ArrayList<AcivityHistory> list;
    private static LayoutInflater inflater = null;
    public MyActivityRecyclerAdapter(Context context, ArrayList<AcivityHistory> subscriptionFeatureArrayList, MasterMyActivityFragment masterMyActivityFragment)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ActivtyListListener= masterMyActivityFragment;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_Activity_name, txt_Activity_duration, txt_Activity_calories_burned,txt_Activity_clock_time,txt_Activity_durationtime;
        ImageView ivFitBit,img_activity_image;
        TextView delete,edit;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);

            this.txt_Activity_durationtime = itemView.findViewById(R.id.txt_Activity_durationtime);
            this.txt_Activity_name = itemView.findViewById(R.id.txt_Activity_name);
            this.img_activity_image = itemView.findViewById(R.id.img_activity_image);
            this.txt_Activity_clock_time = itemView.findViewById(R.id.txt_Activity_clock_time);
            this.txt_Activity_duration = itemView.findViewById(R.id.txt_Activity_duration);
            this.ivFitBit = itemView.findViewById(R.id.ivFitBit);
            this.txt_Activity_calories_burned = itemView.findViewById(R.id.txt_Activity_calories_burned);
            this.delete = itemView.findViewById(R.id.imgMedicineDelete);
            this.edit = itemView.findViewById(R.id.imgMedicineEdit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivtyListListener.updateActivty(getAdapterPosition(), list.get(getAdapterPosition()));

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivtyListListener.deleteActivty(getAdapterPosition(), list.get(getAdapterPosition()));

                }
            });
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_my_activity, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i)
    {
        AcivityHistory model = list.get(i);

        if (model!=null)
        {
            if(model.isFromFitbit()){
                holder.ivFitBit.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
                holder.edit.setVisibility(View.VISIBLE);
//                holder.edit.setImageDrawable(mContext.getResources().getDrawable(R.drawable.fitbit_logo));
                holder.edit.setClickable(false);
            }else{
                holder.ivFitBit.setVisibility(View.GONE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.edit.setClickable(true);


            }
            String strduraton="";
            if (Integer.parseInt(list.get(i).getTotalMinutes())>=60){

                strduraton=formatHoursAndMinutes(Integer.parseInt(list.get(i).getTotalMinutes()));
                if (strduraton.contains("Hour")){
                    holder.txt_Activity_duration.setText(strduraton.replace(" Hour ",":"));

                }
                if (strduraton.contains("Hours")){
                    holder.txt_Activity_duration.setText(strduraton.replace(" Hours ",":"));

                }

            }else {
                holder.txt_Activity_duration.setText(list.get(i).getTotalMinutes()+" Mins");

            }


            holder.txt_Activity_calories_burned.setText(""+list.get(i).getTotalBurnedCalories()+" Cal");
            holder.txt_Activity_name.setText(list.get(i).getActivityName());
            holder.txt_Activity_clock_time.setText(""+list.get(i).getActivityTime()+"");
            holder.txt_Activity_durationtime.setText(""+formatHoursAndMinutes(Integer.parseInt(list.get(i).getTotalMinutes())));



            Glide.with(mContext)
                    .load(list.get(i).getImagePath())
                    .apply(
                            RequestOptions.circleCropTransform()
                                    .placeholder(R.drawable.defaultmedicine)
                                    .error(R.drawable.defaultmedicine)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    )
                    .into(holder.img_activity_image);



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



    public void removeItem(int position)
    {
        list.remove(position);
        notifyDataSetChanged();
    }

    private String convertMiliSecondToMin(long milliSeconds){
        String finalMilisecond= "";
        try {
            finalMilisecond = String.format("%02d",
                    TimeUnit.MILLISECONDS.toMinutes(milliSeconds));
        }catch ( Exception e){
            e.printStackTrace();
        }
        return finalMilisecond;
    }
}
