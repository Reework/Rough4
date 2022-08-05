package com.shamrock.reework.activity.UpgradeModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.SubscriptionFeaturesResponse;

import java.util.ArrayList;

public class UpgradeAdapter extends RecyclerView.Adapter<UpgradeAdapter.MyHolder>
{
    Context context;
    ArrayList<SubscriptionFeaturesResponse.FeatureList> featureList;

    public UpgradeAdapter(Context context, ArrayList<SubscriptionFeaturesResponse.FeatureList> featureList)
    {
        this.context = context;
        this.featureList = featureList;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        ImageView imgMember;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            title = itemView.findViewById(R.id.text_FeatureName);
            imgMember = itemView.findViewById(R.id.imgSubscriptionMember);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_upgrade,
                viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i)
    {
        SubscriptionFeaturesResponse.FeatureList features = featureList.get(i);

        if (features != null)
        {
            if (!TextUtils.isEmpty(features.getFeatureName()))
                holder.title.setText(features.getFeatureName());

            String planIds = features.getPlanIDs().trim();
            if (!TextUtils.isEmpty(planIds))
            {
                if (planIds.contains("1"))
                {
                    holder.imgMember.setImageResource(R.drawable.right);
                }
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return featureList.size();
    }
}
