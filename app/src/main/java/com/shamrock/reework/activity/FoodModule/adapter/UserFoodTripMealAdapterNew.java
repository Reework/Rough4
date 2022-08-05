package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.fragment.MasterFoodFragmentFoodTrip;
import com.shamrock.reework.activity.FoodModule.model.FoodMealData;
import com.shamrock.reework.activity.FoodModule.model.foodtripuser.UserFoodTripData;
import com.shamrock.reework.activity.FoodModule.service.OnFavoriteClick;

import java.util.List;

public class UserFoodTripMealAdapterNew extends RecyclerView.Adapter<UserFoodTripMealAdapterNew.MyHolder>
{
    Context context;
    //    ArrayList<UserFoodTripData> mFilterlist;
    List<FoodMealData> mArrayList;
    private boolean isFavorite = false;

    MasterFoodFragmentFoodTrip masterFoodFragmentFoodTrip;
    OnFavoriteClick onFavoriteClick;

//    public UserFoodTripAdapterNew(Context context, ArrayList<UserFoodTripData> list, MasterFoodFragmentFoodTrip masterFoodFragmentFoodTrip)
//    {
//        this.context = context;
//        mFilterlist = list;
//        mArrayList = list;
//        this.masterFoodFragmentFoodTrip=masterFoodFragmentFoodTrip;
//        onFavoriteClick= (OnFavoriteClick) masterFoodFragmentFoodTrip;
////        this.listener = listener;
//    }

    public UserFoodTripMealAdapterNew(Context context, List<FoodMealData> list) {

        this.context = context;
        mArrayList = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tvFoodName, tvCalories;
        ImageView ivFood, ivFavorite,ivIsVegOrNonveg;
        RelativeLayout rlLayout;
        LinearLayout ll_vegnonveg;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
//            itemView.setOnClickListener(this);

            tvFoodName = itemView.findViewById(R.id.tvRecipeName);
            tvCalories = itemView.findViewById(R.id.tvCalorie);
            ivFood = itemView.findViewById(R.id.ivFood);
            ivFavorite = itemView.findViewById(R.id.ivFavrorite);
            rlLayout = itemView.findViewById(R.id.rlLayout);
            ivIsVegOrNonveg = itemView.findViewById(R.id.ivIsVegOrNonveg);
            ll_vegnonveg = itemView.findViewById(R.id.ll_vegnonveg);

//            ivFavorite.setOnClickListener(this);
//            rlLayout.setOnClickListener(this);
//            ivFood.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDetailsDailog(mArrayList.get(getAdapterPosition()));
//                }
//            });
        }

//        @Override
//        public void onClick(View v)
//        {
//            switch (v.getId())
//            {
//                case R.id.ivFavrorite:
//                    int test1 = getAdapterPosition();
//                    boolean status = mFilterlist.get(getAdapterPosition()).isFav();
//
//                    if (status)
//                    {
//                        isFavorite = false;
//                        mFilterlist.get(getAdapterPosition()).setFav(false);
//                        ivFavorite.setSelected(false);
//                    }
//                    else
//                    {
//                        isFavorite = true;
//                        mFilterlist.get(getAdapterPosition()).setFav(true);
//                        ivFavorite.setSelected(true);
//                    }
//
//                    onFavoriteClick.getFavorite(Integer.parseInt(mArrayList.get(getAdapterPosition()).getUserMealId()), isFavorite);
////                    listener.GetFavoriteItem(getAdapterPosition(), isFavorite, mFilterlist.get(getAdapterPosition()));
//                    break;
//
//                case R.id.rlLayout:
//                    int test = getAdapterPosition();
//
//
////                    listener.GetFootTripPosition(getAdapterPosition(), mFilterlist.get(getAdapterPosition()));
//                    break;
//            }
//        }
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
        String calories;
        FoodMealData model = mArrayList.get(i);
        myHolder.ivIsVegOrNonveg.setVisibility(View.GONE);
        myHolder.ll_vegnonveg.setVisibility(View.GONE);
        myHolder.ivFavorite.setVisibility(View.GONE);
        String reciepe = model.getMealName();
//        if(model.getMeal_calory()) {
        calories = String.valueOf(model.getCalories());
//        }else{
//            calories = 0;
//        }
        String foodImageUrl = (String) model.getRecipeImagePath();


        if (!TextUtils.isEmpty(reciepe))
            myHolder.tvFoodName.setText(reciepe);
        else
            myHolder.tvFoodName.setText("N/A");


        myHolder.tvCalories.setText(calories + " kcalories");


//        boolean isDone = model.isFav();
        /* myHolder.ivFavorite.setVisibility(View.GONE);*/
//        if (isDone)
//            myHolder.ivFavorite.setSelected(true);
//        else
//            myHolder.ivFavorite.setSelected(false);



        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.food_default)
                .error(R.drawable.food_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions.circleCropTransform())
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
        return mArrayList.size();
    }


    public void showDetailsDailog(UserFoodTripData userFoodTripData){
//        UserFoodTripDetailsDialogFragmet addFoodDialogFragmet=new UserFoodTripDetailsDialogFragmet(UserFoodTripAdapterNew.this);
//        Bundle bundle=new Bundle();
//        bundle.putString("imagepath",userFoodTripData.getRecipeImagePath());
//        bundle.putSerializable("Data",userFoodTripData);
////        bundle.putString("meal_name",mList.get(getAdapterPosition()).getRecipeName());
////        bundle.putString("meal_type", (String) mList.get(getAdapterPosition()).getMealType());
////        bundle.putSerializable("nutrionData",mList.get(getAdapterPosition()));
////        bundle.putSerializable("COUNTRY_LIST", foodUnitMasterDataArrayList);
////        bundle.putSerializable("quantity",String.valueOf(mList.get(getAdapterPosition()).getQuantity()));
////        bundle.putSerializable("uom",String.valueOf(mList.get(getAdapterPosition()).getUomId()));
////        Log.d("uomid", String.valueOf(mList.get(getAdapterPosition()).getUomId()));
//
//        addFoodDialogFragmet.setArguments(bundle);
//
//
//        addFoodDialogFragmet.show(masterFoodFragmentFoodTrip.getActivity().getSupportFragmentManager(), addFoodDialogFragmet.getTag());

    }
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    mFilterlist = mArrayList;
//                } else {
//
//                    ArrayList<UserFoodTripData> filterData = new ArrayList<>();
//                    for (UserFoodTripData row : mArrayList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getItemName()!=null && row.getItemName().toLowerCase().contains(charString.toLowerCase())) {
//
//                            filterData.add(row);
//                        }
//                    }
//
//                    mFilterlist = filterData;
//                    if(filterData.size()==0){
//                        Toast.makeText(context,"Recipe name not found",Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mFilterlist;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                /*  list = (ArrayList<FoodTripResponse.FoodStripData>) results.values;*/
//                mFilterlist = (ArrayList<UserFoodTripData>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }


}
