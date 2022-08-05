package com.shamrock.reework.activity.recipe.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecipeMethodListAdapter extends RecyclerView.Adapter<RecipeMethodListAdapter.MyHolder>
    {
        Context context;
        MealTypeListener listener;
        String[] countryListr;

        public interface MealTypeListener
        {
            public void GetCountryPosition(int pos, String model);
        }


    public RecipeMethodListAdapter(Context context, String[] countryListr)
        {
            this.context = context;
            this.countryListr = countryListr;
        }

        public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            TextView tvName,txtstep;

            public MyHolder(@NonNull View itemView)
            {
                super(itemView);
                itemView.setClickable(true);
                itemView.setOnClickListener(this);

                tvName = itemView.findViewById(R.id.etDesc);
                txtstep = itemView.findViewById(R.id.txtstep);
            }

            @Override
            public void onClick(View v)
            {
            }
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.row_recipe_list_method, viewGroup, false);
            return new MyHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
        {

            if (i<9){
                myHolder.txtstep.setText("Step 0"+String.valueOf(i+1));

            }else {
                myHolder.txtstep.setText("Step "+String.valueOf(i+1));

            }
            String replacetext="("+String.valueOf(i+1)+")";
                String text=countryListr[i].replace(replacetext,"");

                myHolder.tvName.setText(text.trim());
        }

        @Override
        public int getItemCount()
        {
            return countryListr.length;
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
