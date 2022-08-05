package com.shamrock.reework.activity.SubscriptionModule.adapter;

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

public class SubscribeFeatureAdapter extends RecyclerView.Adapter<SubscribeFeatureAdapter.MyHolder>
{
    Context context;
    ArrayList<SubscriptionFeaturesResponse.FeatureList> featureList;

    public SubscribeFeatureAdapter(Context context, ArrayList<SubscriptionFeaturesResponse.FeatureList> featureList)
    {
        this.context = context;
        this.featureList = featureList;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        ImageView imgFree, imgMember;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            title = itemView.findViewById(R.id.text_FeatureName);
            imgFree = itemView.findViewById(R.id.imgSubscriptionFree);
            imgMember = itemView.findViewById(R.id.imgSubscriptionMember);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_subscribe_feature,
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
                if (planIds.equals("1,2"))
                {
                    holder.imgMember.setImageResource(R.drawable.right);
                    holder.imgFree.setImageResource(R.drawable.right);
                }
                else if (planIds.equals("1"))
                {
                    holder.imgMember.setImageResource(R.drawable.right);
                    holder.imgFree.setImageResource(R.drawable.cancel);
                }
                else if (planIds.equals("2"))
                {
                    holder.imgMember.setImageResource(R.drawable.cancel);
                    holder.imgFree.setImageResource(R.drawable.right);
                }
                else if (planIds.equals("2,1"))
                {
                    holder.imgMember.setImageResource(R.drawable.right);
                    holder.imgFree.setImageResource(R.drawable.right);
                }
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return featureList.size();
    }

   /* Context mContext;
    ArrayList<SubscriptionFeaturesResponse.SubData> subscriptionFeatureArrayList;
    private static LayoutInflater inflater = null;

    private int planId = 0;


    public SubscribeFeatureAdapter(Context context, ArrayList<SubscriptionFeaturesResponse.SubData> subscriptionFeatureArrayList)
    {
        this.subscriptionFeatureArrayList = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount()
    {
        return subscriptionFeatureArrayList.size();
    }

    @Override
    public SubscriptionFeaturesResponse.PlanHeadingList getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_subscribe_feature, null);

        TextView title = vi.findViewById(R.id.text_FeatureName);
        ImageView imgFree = vi.findViewById(R.id.imgSubscriptionFree);
        ImageView imgMember = vi.findViewById(R.id.imgSubscriptionMember);

        SubscriptionFeaturesResponse.PlanHeadingList heading = subscriptionFeatureArrayList.get(i).getPlanHeadingList().get(i);
        if (heading != null)
        {
            if (heading.getPlanID() == 1)
            {
                if (!TextUtils.isEmpty(heading.getPlanName()))
                    SubscriptionFeaturesActivity.tvMember.setText(heading.getPlanName());
            }
            else if (heading.getPlanID() == 2)
            {
                if (!TextUtils.isEmpty(heading.getPlanName()))
                    SubscriptionFeaturesActivity.tvFree.setText(heading.getPlanName());
            }
        }

        SubscriptionFeaturesResponse.FeatureList features = subscriptionFeatureArrayList.get(i).getFeatureList().get(i);
        if (features != null)
        {
            if (!TextUtils.isEmpty(features.getFeatureName()))
                title.setText(features.getFeatureName());

            String planIds = features.getPlanIDs().trim();
            if (!TextUtils.isEmpty(planIds))
            {
                if (planIds.equals("1"))
                {
                    imgMember.setImageResource(R.drawable.right);
                    imgFree.setImageResource(R.drawable.cancel);
                }
                else if (planIds.equals("2"))
                {
                    imgMember.setImageResource(R.drawable.cancel);
                    imgFree.setImageResource(R.drawable.right);
                }
                else if (planIds.equals("1,2"))
                {
                    imgMember.setImageResource(R.drawable.right);
                    imgFree.setImageResource(R.drawable.right);
                }
            }
        }

      *//*  SubscriptionFeaturesResponse.SubscriptionFeature song = list.get(i);

        String featureName = song.getFeatureName();

        boolean free = song.getIsFree();

        title.setText(featureName);

        if (free)
        {
            imgFree.setImageResource(R.drawable.right);
        }
        else
        {
            imgFree.setImageResource(R.drawable.cancel);
        }
//        if (member) {
            imgMember.setImageResource(R.drawable.right);
//        } else {
//            imgMember.setImageResource(R.drawable.cancel);
//        }*//*
        return vi;
    }*/
}
