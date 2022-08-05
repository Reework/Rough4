package com.shamrock.reework.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.FoodListByMealType;

import java.util.ArrayList;

public class MealListByTypeAdapter extends RecyclerView.Adapter<MealListByTypeAdapter.MyHolder>
{
    Context context;
    ArrayList<FoodListByMealType.Datum> countryList;
    MealListByTypeListener listener;

    public interface MealListByTypeListener
    {
        public void GetMealPosition(int pos, FoodListByMealType.Datum model);
    }


    public MealListByTypeAdapter(Context context1, ArrayList<FoodListByMealType.Datum> countryList, Context context)
    {
        this.context = context;
        this.countryList = countryList;
        listener= (MealListByTypeListener) context;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvName;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvName = itemView.findViewById(R.id.spinner_textView);
        }

        @Override
        public void onClick(View v)
        {
            listener.GetMealPosition(getAdapterPosition(), countryList.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.dialog_adapter_meal, viewGroup, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
       // Log.e("Recipe",countryList.get(i).getRecipeName());
        if (!TextUtils.isEmpty(countryList.get(i).getRecipeName()))
            myHolder.tvName.setText(countryList.get(i).getRecipeName());
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
