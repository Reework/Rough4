package com.shamrock.reework.activity.newmind;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.service.MinMoodModel;

import java.util.ArrayList;

public class NewMindMoodAdapter extends RecyclerView.Adapter<NewMindMoodAdapter.MyHolder>
{
    private Context context;
    ArrayList<MinMoodModel> list;
    NewMindMoodAdapterListener listener;

    public interface NewMindMoodAdapterListener
    {
        public void GetMoodModel(MinMoodModel model);
    }

    public NewMindMoodAdapter(Context context, ArrayList<MinMoodModel> list, NewMindMoodAdapterListener listener)
    {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageView;
        TextView tvMood;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.ivMood);
            tvMood = itemView.findViewById(R.id.tvMood);
        }

        @Override
        public void onClick(View v)
        {
            try{
                listener.GetMoodModel(list.get(getAdapterPosition()));

            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_mind_mood, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        MinMoodModel model = list.get(i);

        String mood = model.getName();
        if (!TextUtils.isEmpty(mood))
            myHolder.tvMood.setText("Seems to be "+mood);

        myHolder.imageView.setImageResource(model.getImage());
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
