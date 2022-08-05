package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.api.response.FoodTripResponse;

import java.util.ArrayList;

public class AllFoodTripNewAdapter extends RecyclerView.Adapter<AllFoodTripNewAdapter.MyHolder>
{
    Context context;
    ArrayList<FoodTripResponse.FoodStripData> mFilterlist;
    ArrayList<FoodTripResponse.FoodStripData> mArrayList;
    FoodTripListener listener;
    private boolean isFavorite = false;

    int sizeshow;
    public interface FoodTripListener
    {
        void GetFootTripPosition(int pos, FoodTripResponse.FoodStripData model);
        void GetFavoriteItem(int pos, boolean isDone, FoodTripResponse.FoodStripData model);
    }

    public AllFoodTripNewAdapter(Context context, ArrayList<FoodTripResponse.FoodStripData> list, Context ctx,int sizeshow)
    {
        this.context = context;
        mFilterlist = list;
        mArrayList = list;
        listener= (FoodTripListener) ctx;
        this.sizeshow=sizeshow;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvFoodName, tvCalories,txtcal,lablerecipe,txt_cusion;
        ImageView ivFood, ivFavorite,ivIsVegOrNonveg,img_recipe;
        RelativeLayout rlLayout;
        LinearLayout ll_vegnonveg;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            img_recipe=itemView.findViewById(R.id.img_recipe);
            txtcal=itemView.findViewById(R.id.txtcal);
            lablerecipe=itemView.findViewById(R.id.lablerecipe);
            txt_cusion=itemView.findViewById(R.id.txt_cusion);


        }

        @Override
        public void onClick(View v)
        {
            listener.GetFootTripPosition(getAdapterPosition(), mFilterlist.get(getAdapterPosition()));

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_foodtrip_new, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        double calories;
        FoodTripResponse.FoodStripData model = mFilterlist.get(i);



        String foodImageUrl = (String) model.getRecipeImagePath();


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.food_default)
                .error(R.drawable.food_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(foodImageUrl)
                .apply(options)
                .into(myHolder.img_recipe);

        String reciepe = model.getRecipeName();
        if(model.getCalories()!=null) {
            calories = model.getCalories();
        }else{
            calories = 0;
        }


        myHolder.txtcal.setText(Math.round(calories)+" - Calories");
        myHolder.lablerecipe.setSelected(true);

        myHolder.lablerecipe.setText(model.getRecipeName());
        myHolder.txt_cusion.setText(" "+model.getCuisine().toString()+" ");

    }

    @Override
    public int getItemCount()
    {
        return mFilterlist.size();
    }


    //filter Data
}
