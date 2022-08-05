package com.shamrock.reework.activity.cheatplan.reeplaceitem;

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

import java.util.ArrayList;

public class ReeplaceItemAdapter extends RecyclerView.Adapter<ReeplaceItemAdapter.MyHolder> implements Filterable
{
    Context context;
    ArrayList<CheatPlanData> mFilterlist;
    ArrayList<CheatPlanData> mArrayList;
    String occastion;
    String category;
    public ReeplaceItemAdapter(Context context, ArrayList<CheatPlanData> list)
    {
        this.context = context;
        mFilterlist = list;
        mArrayList = list;
        this.category=category;
        this.occastion=occastion;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView itemname,replaceitem,energy;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            itemname = itemView.findViewById(R.id.itemname);
            replaceitem = itemView.findViewById(R.id.replaceitem);
            energy = itemView.findViewById(R.id.energy);









        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_newcheat, viewGroup, false);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        CheatPlanData model = mFilterlist.get(i);
        myHolder.itemname.setSelected(true);
        myHolder.replaceitem.setSelected(true);
        myHolder.energy.setSelected(true);
        myHolder.itemname.setText(model.getItemName());
        myHolder.replaceitem.setText(model.getReeplace_Item());
        myHolder.energy.setText(model.getEnergy_Saved_per100kg());




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

                    ArrayList<CheatPlanData> filterData = new ArrayList<>();
                    for (CheatPlanData row : mArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getItemName()!=null && row.getItemName().toLowerCase().contains(charString.toLowerCase())||row.getReeplace_Item()!=null && row.getReeplace_Item().toLowerCase().contains(charString.toLowerCase())||row.getReeplace_Item().toLowerCase().contains(charString.toLowerCase()))) {

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
                mFilterlist = (ArrayList<CheatPlanData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
