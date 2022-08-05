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
import com.shamrock.reework.api.response.City;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyHolder>
{

    Context context;
    ArrayList<City> cityList;
    CityListener listener;

    public interface CityListener
    {
        public void GetCityPosition(int pos, City model);
    }

    public CityAdapter(Context context, ArrayList<City> cityList, CityListener listener)
    {
        this.context = context;
        this.cityList = cityList;
        this.listener = listener;
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
            listener.GetCityPosition(getAdapterPosition(), cityList.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public CityAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.dialog_adapter, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.MyHolder myHolder, int i)
    {
        if (!TextUtils.isEmpty(cityList.get(i).getCityName()))
            myHolder.tvName.setText(cityList.get(i).getCityName());
    }

    @Override
    public int getItemCount()
    {
        return cityList.size();
    }
   /* LayoutInflater inflter;

    public CityAdapter(Context context, List<City> countryList) {
        this.context = context;
        this.countryList = countryList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public City getItem(int i) {
        return countryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.simple_spinner_item, null);
        TextView names = view.findViewById(R.id.spinner_textView);
        names.setText(countryList.get(i).getCityName());
        return view;
    }*/
}
