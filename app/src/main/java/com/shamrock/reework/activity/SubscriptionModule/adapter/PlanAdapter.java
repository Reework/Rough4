package com.shamrock.reework.activity.SubscriptionModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.SubscriptionModule.dialog.PlanModel;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyHolder>
{
    Context context;
    ArrayList<PlanModel> planList;
    PlanListener listener;

    public interface PlanListener
    {
        public void GetPlanPosition(int pos, PlanModel model);
    }

    public PlanAdapter(Context context, ArrayList<PlanModel> planList, PlanListener listener)
    {
        this.context = context;
        this.planList = planList;
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
            listener.GetPlanPosition(getAdapterPosition(), planList.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public PlanAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.dialog_adapter, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.MyHolder myHolder, int i)
    {
        if (!TextUtils.isEmpty(planList.get(i).getPlanName()))
            myHolder.tvName.setText(planList.get(i).getPlanName());
    }

    @Override
    public int getItemCount()
    {
        return planList.size();
    }
}
