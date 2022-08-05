package com.shamrock.reework.activity.MyPlansModule.addfood;

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

import java.util.ArrayList;

public class
AddFoodPlanItemAdapter extends RecyclerView.Adapter<AddFoodPlanItemAdapter.MyHolder>
{

    Context context;
    ArrayList<List> languages;
    SelectItmeListener listener;

    public interface SelectItmeListener
    {
        public void GetSelectedFoodItmePosition(int pos, List model);
    }

    public AddFoodPlanItemAdapter(Context context, ArrayList<List> languages, SelectItmeListener listener)
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
            listener.GetSelectedFoodItmePosition(getAdapterPosition(), languages.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public AddFoodPlanItemAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.dialog_adapter, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFoodPlanItemAdapter.MyHolder myHolder, int i)
    {
        if (!TextUtils.isEmpty(languages.get(i).getText()))
            myHolder.tvName.setText(languages.get(i).getText());
    }

    @Override
    public int getItemCount()
    {
        return languages.size();
    }
}
