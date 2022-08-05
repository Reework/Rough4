package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.FoodCulture;

import java.util.List;

public class FoodCulturesAdapter extends BaseAdapter {

    Context context;
    List<FoodCulture> countryList;
    LayoutInflater inflter;

    public FoodCulturesAdapter(Context context, List<FoodCulture> countryList) {
        this.context = context;
        this.countryList = countryList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public FoodCulture getItem(int i) {
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
        names.setText(countryList.get(i).getFoodculture());
        return view;
    }
}
