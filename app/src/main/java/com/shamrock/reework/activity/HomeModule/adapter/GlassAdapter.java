package com.shamrock.reework.activity.HomeModule.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.service.GlassModel;

import java.util.ArrayList;

public class GlassAdapter extends RecyclerView.Adapter<GlassAdapter.MyHolder>
{
    Context context;
    ArrayList<GlassModel> list;
    GlassInterface listener;
    boolean halfglass;

    public interface GlassInterface
    {
        public void glassPosition(int pos);
    }

    public GlassAdapter(Context context, ArrayList<GlassModel> list, GlassInterface listener)
    {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }
    public GlassAdapter(Context context, ArrayList<GlassModel> list, GlassInterface listener, boolean halfglass)
    {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.halfglass=halfglass;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView ImageView;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            ImageView = itemView.findViewById(R.id.ivGlass);
        }

        @Override
        public void onClick(View v)
        {
            listener.glassPosition(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_glass, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position)
    {
        GlassModel model = list.get(position);

        int sheduleCount = model.getScheduleWaterIntake();
        myHolder.ImageView.setImageResource(R.drawable.water_animation);

        int actualCount = model.getActualWaterIntake();
        if (actualCount > 0)
        {

            inTakeWater(myHolder.ImageView,position);
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    private void inTakeWater(ImageView imgGlass, int position)
    {

        if (halfglass&&(getsize()==position)){
        imgGlass.setImageDrawable(context.getResources().getDrawable(R.drawable.half_water_animation));
        AnimationDrawable myAnimationDrawable = (AnimationDrawable) imgGlass.getDrawable();
        imgGlass.setImageDrawable(myAnimationDrawable);
        myAnimationDrawable.start();
    }else {
            imgGlass.setImageDrawable(context.getResources().getDrawable(R.drawable.water_animation));
            AnimationDrawable myAnimationDrawable = (AnimationDrawable) imgGlass.getDrawable();
            imgGlass.setImageDrawable(myAnimationDrawable);
            myAnimationDrawable.start();
        }


    }

    public int getsize(){
        int size=0;
        int mainpos = 0;
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getActualWaterIntake()>0){
                size++;
                mainpos=i;
            }

        }
        return mainpos;
    }

    private void HalfinTakeWater(ImageView imgGlass)
    {
        imgGlass.setImageDrawable(context.getResources().getDrawable(R.drawable.half_water_animation));
        AnimationDrawable myAnimationDrawable = (AnimationDrawable) imgGlass.getDrawable();
        imgGlass.setImageDrawable(myAnimationDrawable);
        myAnimationDrawable.start();
    }
}
