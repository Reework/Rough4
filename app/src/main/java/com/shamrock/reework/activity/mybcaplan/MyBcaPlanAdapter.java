package com.shamrock.reework.activity.mybcaplan;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.CheatPlanResponse;

import java.util.ArrayList;

public class MyBcaPlanAdapter extends RecyclerView.Adapter<MyBcaPlanAdapter.MyHolder>
{
    Context context;
    ArrayList<PlanwiseListData> countryList;




    public MyBcaPlanAdapter(Context context, ArrayList<PlanwiseListData> cheatPlanList)
    {
        this.context = context;
        this.countryList = cheatPlanList;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_group_name,txt_test_name,txt_avalable;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_group_name = itemView.findViewById(R.id.txt_group_name);
            txt_test_name = itemView.findViewById(R.id.txt_test_name);
            txt_avalable = itemView.findViewById(R.id.txt_avalable);
        }


    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_recler_bca, viewGroup, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        // Log.e("Recipe",countryList.get(i).getRecipeName());

        myHolder.txt_group_name.setSelected(true);
        myHolder.txt_group_name.setText(countryList.get(i).getGroupName());
        myHolder.txt_test_name.setText(countryList.get(i).getTestName());
        if (countryList.get(i).getIsChecked().equalsIgnoreCase("true")){
            myHolder.txt_avalable.setText("Yes");
        }else {
            myHolder.txt_avalable.setText("No");

        }




    }

    @Override
    public int getItemCount()
    {
        return countryList.size();
    }

   /* Context context;
    ArrayList<Country> countryList;
    LayoutInflater inflter;

    public CountryAdapter(Context context, ArrayList<Country> countryList)
    {
        this.context = context;
        this.countryList = countryList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Country getItem(int i)
    {
        return countryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        view = inflter.inflate(R.layout.simple_spinner_item, null);

        TextView names = view.findViewById(R.id.spinner_textView);
        names.setText(countryList.get(i).getCountryName());
        return view;
    }*/
}
