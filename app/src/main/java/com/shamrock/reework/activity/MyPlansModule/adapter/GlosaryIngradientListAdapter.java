package com.shamrock.reework.activity.MyPlansModule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.MyPlansModule.model.GlosaryData;
import com.shamrock.reework.activity.MyPlansModule.model.IngredientList;
import com.shamrock.reework.activity.newrecipe.Ingredients;

import java.util.ArrayList;

public class GlosaryIngradientListAdapter extends RecyclerView.Adapter<GlosaryIngradientListAdapter.MyHolder>
{
    private Context context;
    private ArrayList<IngredientList> glosary_data_list;

    public GlosaryIngradientListAdapter(Context context, ArrayList<IngredientList> glosary_data_list) {
        this.context=context;
        this.glosary_data_list=glosary_data_list;
    }


    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_ingrad_measurement,txt_ingrad_name,txt_ingrad_qnty;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            txt_ingrad_name=itemView.findViewById(R.id.txt_ingrad_name);
            txt_ingrad_measurement=itemView.findViewById(R.id.txt_ingrad_measurement);
            txt_ingrad_qnty=itemView.findViewById(R.id.txt_ingrad_qnty);





        }


    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adap_myplans_glosary_ingrad, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        String lsTime, lsPlan, lsQty;

        IngredientList model = glosary_data_list.get(i);
        myHolder.txt_ingrad_name.setText(model.getIngredientName());
        myHolder.txt_ingrad_measurement.setText(model.getMeasurement());
        myHolder.txt_ingrad_qnty.setText(model.getQuantity());



    }

    @Override
    public int getItemCount()
    {
        return glosary_data_list.size();
    }


}
