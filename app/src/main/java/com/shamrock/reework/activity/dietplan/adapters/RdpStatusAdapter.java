package com.shamrock.reework.activity.dietplan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.dietplan.pojo.ClsCustomRDpStatus;
import com.shamrock.reework.activity.dietplan.pojo.PathReport;

import java.util.ArrayList;

public class RdpStatusAdapter extends RecyclerView.Adapter<RdpStatusAdapter.MyHolder>
{
    Context context;

    ArrayList<ClsCustomRDpStatus> arylst_pathReport;
    String occastion;
    String category;
    public RdpStatusAdapter(Context context, ArrayList<ClsCustomRDpStatus> arylst_pathReport)
    {
        this.context = context;
      this.arylst_pathReport=arylst_pathReport;

    }


    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView txt_existing_engery,txt_ideal_engery,txt_plan_engery,txt_rdp_name;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_existing_engery = itemView.findViewById(R.id.txt_existing_engery);
            txt_ideal_engery = itemView.findViewById(R.id.txt_ideal_engery);
            txt_plan_engery = itemView.findViewById(R.id.txt_plan_engery);
            txt_rdp_name = itemView.findViewById(R.id.txt_rdp_name);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_rdp_staus, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        ClsCustomRDpStatus model = arylst_pathReport.get(i);

//        myHolder.txt_parmaeter_name.setText("Parameter : " +model.getParameterName());


        if (model.getExisting()!=null){

            myHolder.txt_existing_engery.setText(model.getExisting());

        }
        if (model.getIdeal()!=null){

            if (!model.getIdeal().equalsIgnoreCase("null")){
                myHolder.txt_ideal_engery.setText(model.getIdeal());

            }else {
                myHolder.txt_ideal_engery.setText("0.0");

            }

        }
        if (model.getPlan()!=null){
            myHolder.txt_plan_engery.setText(model.getPlan());

        }

        myHolder.txt_rdp_name.setText(model.getName());









    }

    @Override
    public int getItemCount()
    {
        return arylst_pathReport.size();
    }


}
