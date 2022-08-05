package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.model.ClsSuggestion;
import com.shamrock.reework.activity.FoodModule.model.Data;
import com.shamrock.reework.api.response.FoodCulture;

import java.util.List;

public class FoodSuggestionAdapter extends BaseAdapter {

    Context context;
    List<Data> clsSuggestionList;
    LayoutInflater inflter;

    public FoodSuggestionAdapter(Context context, List<Data> clsSuggestionList) {
        this.context = context;
        this.clsSuggestionList = clsSuggestionList;
        try {
            inflter = (LayoutInflater.from(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return clsSuggestionList.size();
    }

    @Override
    public Data getItem(int i) {
        return clsSuggestionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        try {
            if (inflter!=null){
                view = inflter.inflate(R.layout.row_suggestion, null);
                TextView txt_heathcondition = view.findViewById(R.id.txt_heathcondition);
                TextView txt_does = view.findViewById(R.id.txt_does);
                TextView txt_dont = view.findViewById(R.id.txt_donts);

                txt_heathcondition.setText(clsSuggestionList.get(i).getHealthCondition());
                txt_does.setText(clsSuggestionList.get(i).getDo());
                txt_dont.setText(clsSuggestionList.get(i).getDoNot());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
