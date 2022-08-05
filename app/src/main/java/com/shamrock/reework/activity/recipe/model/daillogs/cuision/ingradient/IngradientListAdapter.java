package com.shamrock.reework.activity.recipe.model.daillogs.cuision.ingradient;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.recipe.model.CusineList;
import com.shamrock.reework.activity.recipe.model.IngradientData;

import java.util.ArrayList;

public class
IngradientListAdapter extends RecyclerView.Adapter<IngradientListAdapter.MyHolder>
{

    Context context;
    ArrayList<IngradientData> languages;
    OnIngradientSelect listener;

    public interface OnIngradientSelect {
        public void getSelectedIngradient(IngradientData cusineList);
    }

    public IngradientListAdapter(Context context, ArrayList<IngradientData> languages, OnIngradientSelect listener)
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
            listener.getSelectedIngradient(languages.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public IngradientListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.dialog_adapter, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngradientListAdapter.MyHolder myHolder, int i)
    {
        if (!TextUtils.isEmpty(languages.get(i).getIngredientName()))
            myHolder.tvName.setText(languages.get(i).getIngredientName());
    }

    @Override
    public int getItemCount()
    {
        return languages.size();
    }
}
