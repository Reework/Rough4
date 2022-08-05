package com.shamrock.reework.activity.WeightModule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.WeightFragment;
import com.shamrock.reework.activity.WeightModule.activity.OnUpdateWeight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClsNewWeightHistoryAdapter extends BaseAdapter
{


    private static final String SEPARATOR = " | ";
    Context mContext;
    ArrayList<WeightHistory> weightHistoryArrayList;
    private static LayoutInflater inflater = null;
    OnUpdateWeight   onUpdateWeight;
    WeightFragment weightFragment;

    public ClsNewWeightHistoryAdapter(Context context, ArrayList<WeightHistory> weightHistoryArrayList, WeightFragment weightFragment)
    {
        this.weightHistoryArrayList = weightHistoryArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.weightFragment=weightFragment;
        onUpdateWeight= weightFragment;
    }

    @Override
    public int getCount() {
        return weightHistoryArrayList.size();
    }

    @Override
    public WeightHistory getItem(int i) {
        return weightHistoryArrayList.get(i);
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
            vi = inflater.inflate(R.layout.list_row_my_weight_history, null);

        TextView txt_weight = vi.findViewById(R.id.txt_weight);
        TextView txt_date = vi.findViewById(R.id.txt_date);
        ImageView imageView=vi.findViewById(R.id.img_edit_weight);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateWeight.updateweight(weightHistoryArrayList.get(i));
            }
        });


        txt_weight.setText(weightHistoryArrayList.get(i).getWeight());

        if (weightHistoryArrayList.get(i).getHealthDate().contains("T")){
            int index = weightHistoryArrayList.get(i).getHealthDate().indexOf("T");
            txt_date.setText(formatDates(weightHistoryArrayList.get(i).getHealthDate().substring(0,index)));

        }else {


            txt_date.setText(formatDates(weightHistoryArrayList.get(i).getHealthDate()));

        }


//        txt_date.setText(weightHistoryArrayList.get(i).getHealthDate());




        return vi;
    }

    public void removeItem(int position)
    {
        weightHistoryArrayList.remove(position);
        notifyDataSetChanged();
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


}
