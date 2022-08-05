package com.shamrock.reework.activity.recipe.adapter;

import android.content.Context;
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
import com.shamrock.reework.activity.FoodModule.adapter.AllFoodTripNewAdapter;
import com.shamrock.reework.api.response.FoodTripResponse;

import java.util.ArrayList;

public class SeeAllFoodAdapter extends RecyclerView.Adapter<SeeAllFoodAdapter.MyHolder> implements Filterable
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






    public SeeAllFoodAdapter(Context context, ArrayList<FoodTripResponse.FoodStripData> list, Context ctx)
    {
        this.context = context;
        mFilterlist = list;
        mArrayList = list;
        listener= (FoodTripListener) ctx;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        TextView tvFoodName, tvCalories,txtcal,lablerecipe,lable_cusion;
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
            lable_cusion=itemView.findViewById(R.id.lable_cusion);


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
                inflate(R.layout.lay_seeall, viewGroup, false);
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


        myHolder.txtcal.setText(Math.round(calories)+" Calories");

        myHolder.lablerecipe.setSelected(true);
        myHolder.lablerecipe.setText(model.getRecipeName());
        myHolder.lable_cusion.setText(" "+model.getCuisine().toString()+" ");

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
