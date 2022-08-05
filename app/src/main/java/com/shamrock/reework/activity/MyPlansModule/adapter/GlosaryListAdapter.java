package com.shamrock.reework.activity.MyPlansModule.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.MyPlansModule.activity.GlosaryListActivity;
import com.shamrock.reework.activity.MyPlansModule.model.GlosaryData;
import com.shamrock.reework.api.response.MyPlanResponse;

import java.util.ArrayList;

public class GlosaryListAdapter extends RecyclerView.Adapter<GlosaryListAdapter.MyHolder>
{
    private Context context;
    private ArrayList<GlosaryData> glosary_data_list;

    public GlosaryListAdapter(Context context, ArrayList<GlosaryData> glosary_data_list) {
        this.context=context;
        this.glosary_data_list=glosary_data_list;
    }


    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txtGlosaryname;
        RecyclerView recycler_ingradlist;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            txtGlosaryname=itemView.findViewById(R.id.txtGlosaryname);
            recycler_ingradlist=itemView.findViewById(R.id.recycler_ingradlist);



        }


    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adap_myplans_glosary, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        String lsTime, lsPlan, lsQty;

        GlosaryData model = glosary_data_list.get(i);
        myHolder.txtGlosaryname.setText(model.getReceiprName());
        myHolder.recycler_ingradlist.setAdapter(new GlosaryIngradientListAdapter(context,model.getIngredientList()));



    }

    @Override
    public int getItemCount()
    {
        return glosary_data_list.size();
    }


}
