package com.shamrock.reework.activity.foodguide;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.healthmonitoring.LabTestList;
import com.shamrock.reework.activity.reemonitor.OnparameterClick;

import java.util.ArrayList;

public class FoodGuidelistAdapter extends RecyclerView.Adapter<FoodGuidelistAdapter.MyHolder> implements Filterable
{
    Context context;
    ArrayList<FoodGuidePojo> mFilterlist;
    ArrayList<FoodGuidePojo> mArrayList;
    String occastion;
    OnparameterClick onparameterClick;
    String category,type="";
    public FoodGuidelistAdapter(Context context, ArrayList<FoodGuidePojo> list,String type)
    {
        this.context = context;
        mFilterlist = list;
        mArrayList = list;
        this.category=category;
        this.occastion=occastion;
        this.type=type;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_parametername;
        LinearLayout lay;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_parametername = itemView.findViewById(R.id.txt_parametername);
            lay = itemView.findViewById(R.id.lay);


        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_paramer_foodguide, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        FoodGuidePojo model = mFilterlist.get(i);
       myHolder.txt_parametername.setText(model.getIngredientName());

       if(type.equals("green")){
           myHolder.txt_parametername.setTextColor(context.getResources().getColor(R.color.lightgreen));
       }else if(type.equals("orange")){
           myHolder.txt_parametername.setTextColor(context.getResources().getColor(R.color.lightorange));
       }else if(type.equals("red")){
           myHolder.txt_parametername.setTextColor(ContextCompat.getColor(context,R.color.lightred));
       }




    }

    @Override
    public int getItemCount()
    {
        return mFilterlist.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilterlist = mArrayList;
                } else {

                    ArrayList<FoodGuidePojo> filterData = new ArrayList<>();
                    for (FoodGuidePojo row : mArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getIngredientName()!=null && row.getIngredientName().toLowerCase().contains(charString.toLowerCase())||row.getIngredientName()!=null && row.getIngredientName().toLowerCase().contains(charString.toLowerCase())||row.getIngredientName().toLowerCase().contains(charString.toLowerCase()))) {

                            filterData.add(row);
                        }
                    }

                    mFilterlist = filterData;
                    if(filterData.size()==0){
                        Toast.makeText(context,"No data found.",Toast.LENGTH_SHORT).show();
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                /*  list = (ArrayList<FoodTripResponse.FoodStripData>) results.values;*/
                mFilterlist = (ArrayList<FoodGuidePojo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
