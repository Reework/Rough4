package com.shamrock.reework.adapter;

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
import com.shamrock.reework.activity.healthmonitoring.LabTestList;

import java.util.ArrayList;

public class ParameterAdapter extends RecyclerView.Adapter<ParameterAdapter.MyHolder> implements Filterable
{
    Context context;
    ArrayList<LabTestList> mFilterlist;
    ArrayList<LabTestList> mArrayList;
    String occastion;
    String category;
    public ParameterAdapter(Context context, ArrayList<LabTestList> list, String occastion, String category)
    {
        this.context = context;
        mFilterlist = list;
        mArrayList = list;
        this.category=category;
        this.occastion=occastion;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView txt_parameter;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_parameter = itemView.findViewById(R.id.txt_parameter);


        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_parameter, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        LabTestList model = mFilterlist.get(i);
        myHolder.txt_parameter.setText("" +model.getTestName());



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

                    ArrayList<LabTestList> filterData = new ArrayList<>();
                    for (LabTestList row : mArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getTestName()!=null && row.getTestName().toLowerCase().contains(charString.toLowerCase())||row.getTestName()!=null && row.getTestName().toLowerCase().contains(charString.toLowerCase())||row.getTestName().toLowerCase().contains(charString.toLowerCase()))) {

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
                mFilterlist = (ArrayList<LabTestList>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
