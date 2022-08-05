package com.shamrock.reework.activity.WeightModule.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.WeightFragment;
import com.shamrock.reework.activity.WeightModule.activity.OnUpdateWeight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClsMyNewWeightAdapter extends RecyclerView.Adapter<ClsMyNewWeightAdapter.MyHolder>
{

    private static final String SEPARATOR = " | ";
    Context mContext;
    ArrayList<WeightHistory> weightHistoryArrayList;
    private static LayoutInflater inflater = null;
    OnUpdateWeight onUpdateWeight;
    WeightFragment weightFragment;

    public ClsMyNewWeightAdapter(Context context, ArrayList<WeightHistory> weightHistoryArrayList, WeightFragment weightFragment)
    {
        this.weightHistoryArrayList = weightHistoryArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.weightFragment=weightFragment;
        onUpdateWeight= weightFragment;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView txt_weight;
        TextView txt_date ;
        ImageView imageView;

        public MyHolder(@NonNull View vi)
        {
            super(vi);
             txt_weight = vi.findViewById(R.id.txt_weight);
             txt_date = vi.findViewById(R.id.txt_date);
             imageView=vi.findViewById(R.id.img_edit_weight);
             imageView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     onUpdateWeight.updateweight(weightHistoryArrayList.get(getAdapterPosition()));
                 }
             });
        }

        @Override
        public void onClick(View v)
        {
        }
    }

    @NonNull
    @Override
    public ClsMyNewWeightAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.list_row_my_weight_history, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClsMyNewWeightAdapter.MyHolder holder, final int i)
    {
        holder.txt_weight.setText("Weight(Kg) : "+weightHistoryArrayList.get(i).getWeight());

        if (weightHistoryArrayList.get(i).getHealthDate().contains("T")){
            int index = weightHistoryArrayList.get(i).getHealthDate().indexOf("T");
            holder.txt_date.setText("  Date : "+formatDates(weightHistoryArrayList.get(i).getHealthDate().substring(0,index)));

        }else {


           holder.txt_date.setText("  Date : "+formatDates(weightHistoryArrayList.get(i).getHealthDate()));

        }



    }



    @Override
    public int getItemCount()
    {
        return weightHistoryArrayList.size();
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }

    public  String formatHoursAndMinutesnew(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" Hr ";
        }else {
            strhours=" Hrs ";

        }
        return (totalMinutes / 60) + strhours + minutes+" Mins";
    }

}
