package com.shamrock.reework.activity.dietplan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.cheatplan.pojo.Plans;
import com.shamrock.reework.activity.dietplan.pojo.ClsPathoReportMain;
import com.shamrock.reework.activity.dietplan.pojo.ClspathoReportData;
import com.shamrock.reework.activity.dietplan.pojo.PathReport;

import java.util.ArrayList;

import retrofit2.Callback;

public class PathoPlanAdapter extends RecyclerView.Adapter<PathoPlanAdapter.MyHolder>
{
    Context context;

    ArrayList<PathReport> arylst_pathReport;
    String occastion;
    String category;
    public PathoPlanAdapter(Context context,ArrayList<PathReport> arylst_pathReport)
    {
        this.context = context;
      this.arylst_pathReport=arylst_pathReport;

    }


    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_result,txt_bio_ref,txt_score,txt_alternat_cheat_item,txt_item_remrk;

        TextView txt_parmaeter_name,txt_ditory_advice;
        TextView txt_Category,txt_report_type;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_result = itemView.findViewById(R.id.txt_cheat_item_name);
            txt_bio_ref = itemView.findViewById(R.id.txt_cheat_item_quantity);
            txt_score = itemView.findViewById(R.id.txt_cheat_item_energy);
            txt_alternat_cheat_item = itemView.findViewById(R.id.txt_alternat_cheat_item);
            txt_item_remrk = itemView.findViewById(R.id.txt_cheat_item_remrk);
            txt_parmaeter_name=itemView.findViewById(R.id.txt_occation);
            txt_Category=itemView.findViewById(R.id.txt_Category);
            txt_report_type=itemView.findViewById(R.id.txt_report_type);
            txt_ditory_advice=itemView.findViewById(R.id.txt_ditory_advice);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_diet_plan, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        PathReport model = arylst_pathReport.get(i);

//        myHolder.txt_parmaeter_name.setText("Parameter : " +model.getParameterName());

        myHolder.txt_result.setText("Result : "+model.getResult());
        myHolder.txt_bio_ref.setText ("Bio. Ref : "+model.getBioRef());
        myHolder.txt_score.setText("Score : "+model.getScore());
        if (model.getRemark()!=null){
            myHolder.txt_item_remrk.setText("Remark : "+model.getRemark());

        }else {
            myHolder.txt_item_remrk.setText("Remark : ");

        }
        if(model.getSpecificDietAdivsory()!=null){
            myHolder.txt_ditory_advice.setText("Diet Advice : "+model.getSpecificDietAdivsory());

        }else {
            myHolder.txt_ditory_advice.setText("Diet Advice : ");

        }
        myHolder.txt_report_type.setText("Parameter : " +model.getParameterName()+" ("+model.getReportType()+")");






    }

    @Override
    public int getItemCount()
    {
        return arylst_pathReport.size();
    }


}
