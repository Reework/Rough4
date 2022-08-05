package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.api.response.FoodTripResponse;

import java.util.ArrayList;

public class FoodTripAdapter extends RecyclerView.Adapter<FoodTripAdapter.MyHolder> implements Filterable
{
    Context context;
    ArrayList<FoodTripResponse.FoodStripData> mFilterlist;
    ArrayList<FoodTripResponse.FoodStripData> mArrayList;
    FoodTripListener listener;
    private boolean isFavorite = false;

    public interface FoodTripListener
    {
        void GetFootTripPosition(int pos, FoodTripResponse.FoodStripData model);
        void GetFavoriteItem(int pos, boolean isDone, FoodTripResponse.FoodStripData model);
    }

    public FoodTripAdapter(Context context, ArrayList<FoodTripResponse.FoodStripData> list, FoodTripListener listener)
    {
        this.context = context;
        mFilterlist = list;
        mArrayList = list;
        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvFoodName, tvCalories;
        ImageView ivFood, ivFavorite,ivIsVegOrNonveg;
        RelativeLayout rlLayout;
        LinearLayout ll_vegnonveg;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvFoodName = itemView.findViewById(R.id.tvRecipeName);
            tvCalories = itemView.findViewById(R.id.tvCalorie);
            ivFood = itemView.findViewById(R.id.ivFood);
            ivFavorite = itemView.findViewById(R.id.ivFavrorite);
            rlLayout = itemView.findViewById(R.id.rlLayout);
            ivIsVegOrNonveg = itemView.findViewById(R.id.ivIsVegOrNonveg);
            ll_vegnonveg = itemView.findViewById(R.id.ll_vegnonveg);

            ivFavorite.setOnClickListener(this);
            rlLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ivFavrorite:
                    int test1 = getAdapterPosition();
                    boolean status = mFilterlist.get(getAdapterPosition()).getIsfavourite();

                    if (status)
                    {
                        isFavorite = false;
                        mFilterlist.get(getAdapterPosition()).setIsfavourite(false);
                        ivFavorite.setSelected(false);
                    }
                    else
                    {
                        isFavorite = true;
                        mFilterlist.get(getAdapterPosition()).setIsfavourite(true);
                        ivFavorite.setSelected(true);
                    }

                    listener.GetFavoriteItem(getAdapterPosition(), isFavorite, mFilterlist.get(getAdapterPosition()));
                    break;

                case R.id.rlLayout:
                    int test = getAdapterPosition();


                    listener.GetFootTripPosition(getAdapterPosition(), mFilterlist.get(getAdapterPosition()));
                    break;
            }
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_foodtrip, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        double calories;
        FoodTripResponse.FoodStripData model = mFilterlist.get(i);

        String reciepe = model.getRecipeName();
        if(model.getCalories()!=null) {
             calories = model.getCalories();
        }else{
            calories = 0;
        }
        String foodImageUrl = (String) model.getRecipeImagePath();

           String dietaryPreference =  (String) model.getDietaryPreference();
//        myHolder.ivIsVegOrNonveg.setVisibility(View.GONE);
//        myHolder.ll_vegnonveg.setVisibility(View.GONE);
           if(dietaryPreference!=null){

              if(dietaryPreference.equalsIgnoreCase("Non-Vegetarian") || dietaryPreference.equalsIgnoreCase("Eggetarian")){
                   myHolder.ivIsVegOrNonveg.setBackgroundResource(R.drawable.fibre_circle);
                  myHolder.ll_vegnonveg.setBackgroundResource(R.drawable.veg_border_rectangle);

              }else{
                   myHolder.ivIsVegOrNonveg.setBackgroundResource(R.drawable.veg_icon);
                   myHolder.ll_vegnonveg.setBackgroundResource(R.drawable.non_veg_border_rectangle);
               }
           }
        if (!TextUtils.isEmpty(reciepe))
            myHolder.tvFoodName.setText(reciepe);
        else
            myHolder.tvFoodName.setText("N/A");


        if (calories > 0)
            if(model.getEditId()>0){
                myHolder.tvCalories.setText("modified recipe");
            }else {
                myHolder.tvCalories.setText(calories + " Calories");
            }
        else
            myHolder.tvCalories.setText("not available");

        boolean isDone = model.getIsfavourite();
       /* myHolder.ivFavorite.setVisibility(View.GONE);*/
        if (isDone)
            myHolder.ivFavorite.setSelected(true);
        else
            myHolder.ivFavorite.setSelected(false);



       /* RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_pic_bg)
                .error(R.drawable.ic_profile_pic_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);*/
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.food_default)
                .error(R.drawable.food_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(foodImageUrl)
                .apply(options)
                .apply(RequestOptions.circleCropTransform())
                .into(myHolder.ivFood);

    }

    @Override
    public int getItemCount()
    {
        return mFilterlist.size();
    }


    //filter Data
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilterlist = mArrayList;
                } else {

                    ArrayList<FoodTripResponse.FoodStripData> filterData = new ArrayList<>();
                    for (FoodTripResponse.FoodStripData row : mArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getRecipeName()!=null && row.getRecipeName().toLowerCase().contains(charString.toLowerCase())) {

                            filterData.add(row);
                        }
                    }

                    mFilterlist = filterData;
                    if(filterData.size()==0){
                        Toast.makeText(context,"Recipe name not found",Toast.LENGTH_SHORT).show();
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
            /*  list = (ArrayList<FoodTripResponse.FoodStripData>) results.values;*/
                mFilterlist = (ArrayList<FoodTripResponse.FoodStripData>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
