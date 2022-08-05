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
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.api.response.Language;

import java.util.ArrayList;

public class
UOMAdapter extends RecyclerView.Adapter<UOMAdapter.MyHolder>
{

    Context context;
    ArrayList<FoodUnitMasterData> languages;
    UOMListener listener;

    public interface UOMListener
    {
        public void GetUOMPosition(int pos, FoodUnitMasterData model);
    }

    public UOMAdapter(Context context, ArrayList<FoodUnitMasterData> languages, UOMListener listener)
    {
        this.context = context;
        this.languages = languages;
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
            listener.GetUOMPosition(getAdapterPosition(), languages.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public UOMAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.dialog_adapter, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UOMAdapter.MyHolder myHolder, int i)
    {
        if (!TextUtils.isEmpty(languages.get(i).getMeasurement()))
            myHolder.tvName.setText(languages.get(i).getMeasurement());
    }

    @Override
    public int getItemCount()
    {
        return languages.size();
    }
}
