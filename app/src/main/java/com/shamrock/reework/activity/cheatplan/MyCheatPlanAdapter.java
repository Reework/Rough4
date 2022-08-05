package com.shamrock.reework.activity.cheatplan;

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

public class MyCheatPlanAdapter extends RecyclerView.Adapter<MyCheatPlanAdapter.MyHolder> implements Filterable
{
    Context context;
    ArrayList<Plans> mFilterlist;
    ArrayList<Plans> mArrayList;
    String occastion;
    String category;
    public MyCheatPlanAdapter(Context context, ArrayList<Plans> list, String occastion, String category)
    {
        this.context = context;
        mFilterlist = list;
        mArrayList = list;
        this.category=category;
        this.occastion=occastion;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_cheat_item_name,txt_cheat_item_quantity,txt_cheat_item_energy,txt_alternat_cheat_item,txt_cheat_item_remrk;

        TextView txt_occation;
        TextView txt_Category,txt_alternate_itme_cal,txt_alternate_itme;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_cheat_item_name = itemView.findViewById(R.id.txt_cheat_item_name);
            txt_cheat_item_quantity = itemView.findViewById(R.id.txt_cheat_item_quantity);
            txt_cheat_item_energy = itemView.findViewById(R.id.txt_cheat_item_energy);
            txt_alternat_cheat_item = itemView.findViewById(R.id.txt_alternat_cheat_item);
            txt_cheat_item_remrk = itemView.findViewById(R.id.txt_cheat_item_remrk);
            txt_occation=itemView.findViewById(R.id.txt_occation);
            txt_Category=itemView.findViewById(R.id.txt_Category);
            txt_alternate_itme=itemView.findViewById(R.id.txt_alternate_itme);
            txt_alternate_itme_cal=itemView.findViewById(R.id.txt_alternate_itme_cal);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_cheat_plan, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        Plans model = mFilterlist.get(i);
        myHolder.txt_occation.setText(occastion+" :  " +category);
        myHolder.txt_cheat_item_name.setText("Cheat item : "+model.getCheatItem());
        myHolder.txt_cheat_item_quantity.setText ("Quantity   : "+model.getQuantity()+" ("+model.getUoM()+")");
        myHolder.txt_cheat_item_energy.setText("Energy(kcal) :"+model.getCalories());
        myHolder.txt_cheat_item_remrk.setText("Remark : "+model.getRemarks());
        myHolder.txt_alternate_itme.setText("Alternate Item : "+model.getAlternateItem()+"("+model.getAlternateItemCalories()+" kcal)");
        myHolder.txt_alternate_itme_cal.setText("Alternate Item Energy(kcal):"+model.getAlternateItemCalories());






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

                    ArrayList<Plans> filterData = new ArrayList<>();
                    for (Plans row : mArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getCheatItem()!=null && row.getCheatItem().toLowerCase().contains(charString.toLowerCase())||row.getOccasion()!=null && row.getOccasion().toLowerCase().contains(charString.toLowerCase())||row.getAlternateItem().toLowerCase().contains(charString.toLowerCase()))) {

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
                mFilterlist = (ArrayList<Plans>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
