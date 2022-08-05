package com.shamrock.reework.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;

import java.util.ArrayList;

public class NewCountryAdapter extends RecyclerView.Adapter<NewCountryAdapter.MyHolder>
{
    Context context;
    ArrayList<String> countryList;
    CountryListener listener;
    String from;

    public interface CountryListener
    {
        public void GetCountryPosition(int pos, String coutry, String from);
    }


    public NewCountryAdapter(Context context, ArrayList<String> countryList, CountryListener listener, String from)
    {
        this.context = context;
        this.countryList = countryList;
        this.listener = listener;
        this.from=from;
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
            listener.GetCountryPosition(getAdapterPosition(), countryList.get(getAdapterPosition()).toString(),from);
        }
    }

    @NonNull
    @Override
    public NewCountryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.dialog_adapter, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewCountryAdapter.MyHolder myHolder, int i)
    {
        if (!TextUtils.isEmpty(countryList.get(i).toString()))
            myHolder.tvName.setText(countryList.get(i).toString());
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
