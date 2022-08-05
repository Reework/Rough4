package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.reevaluate.ReevaluateActivity;
import com.shamrock.reework.adapter.DifferentRowAdapter;
import com.shamrock.reework.api.response.FoodListByMealType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class AddMealIntoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CITY_TYPE = 1;
    private static final int ROW_TYPE = 2;
    private static final int FOOTER_TYPE = 3;
    public List<FoodListByMealType.Datum> mList;
    private List<FoodListByMealType.Datum> filtermList;
    OnMealRemoveListner listner;
    private Context context;

    public interface  OnMealRemoveListner{
        public void onRemove(int position,FoodListByMealType.Datum model);
    }
    public AddMealIntoListAdapter(Context context , List<FoodListByMealType.Datum> list) {
        this.mList = list;
        this.listner=(OnMealRemoveListner)context;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add_mean_new_row, parent, false);
        return new RowViewHolder(view);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {




        try {


            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_home_food)
                    .error(R.drawable.ic_home_food)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .priority(Priority.HIGH);
            if (mList.get(position).getRecipeImagePath()!=null){
                Glide.with(context)
                        .load(mList.get(position).getRecipeImagePath())
                        .apply(options.circleCrop())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                           DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(((RowViewHolder) holder).mImage);
            }


        }catch (Exception e){
            e.printStackTrace();
        }






        if (position!=0){
            if (mList.get(position-1).getMealType().toString().equalsIgnoreCase(mList.get(position).getMealType().toString())){
                ((RowViewHolder) holder).txt_mealtype_main_header.setVisibility(View.GONE);
            }else {
                ((RowViewHolder) holder).txt_mealtype_main_header.setVisibility(View.VISIBLE);

                ((RowViewHolder) holder).txt_mealtype_main_header.setText(mList.get(position).getMealType().toString());

            }
        }else {
            ((RowViewHolder) holder).txt_mealtype_main_header.setVisibility(View.VISIBLE);

            if ((mList.get(position).getMealType()!=null)){
                ((RowViewHolder) holder).txt_mealtype_main_header.setText(mList.get(position).getMealType().toString());

            }

        }


        ((RowViewHolder) holder).mTitle.setText(mList.get(position).getRecipeName());
        ((RowViewHolder) holder).mTitle.setSelected(true);

        ((RowViewHolder) holder).txt_mealtype_new.setText(mList.get(position).getMealType().toString());



        try {
            String strqty = String.valueOf(mList.get(position).getQuantity());
            String[] strNewQty = strqty.split("\\.");
            boolean isFound = false;


            if (strNewQty[1].equals("0")) {
                isFound = true;

            }
            if (isFound) {
                isFound = false;
//            ((DifferentRowAdapter.RowViewHolder) holder).mQuantity.setText(""+strNewQty[0].toString());
                ((RowViewHolder) holder).mQuantity.setText("" + strNewQty[0].toString());

            } else {
                ((RowViewHolder) holder).mQuantity.setText("" + mList.get(position).getQuantity());
            }

        }catch (Exception e){
            ((RowViewHolder) holder).mQuantity.setText("" + mList.get(position).getQuantity());

        }


        DecimalFormat decimalFormat=new DecimalFormat("0.00");

        double singlecal=Double.parseDouble(mList.get(position).getCalories());

        double valuegram=mList.get(position).getValueInGram();
        if (valuegram==0){
            valuegram=1.0;
        }else {
            valuegram=mList.get(position).getValueInGram();
        }

        double calnew=singlecal*mList.get(position).getQuantity()*valuegram;

        ((RowViewHolder) holder).mCalories.setText(String.valueOf(decimalFormat.format(calnew)));
        ((RowViewHolder) holder).cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onRemove(position,mList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }
    public  class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mCalories, mQuantity,cancle,textAllMealQty,txt_mealtype_new,txt_mealtype_main_header;
        private ImageView mImage;

        public RowViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textAllMealRowName);
            mImage = itemView.findViewById(R.id.imgAllMeal_item_image);
            mQuantity = itemView.findViewById(R.id.textAllMealQty);
            mCalories = itemView.findViewById(R.id.textAllMealCalories);
            cancle = itemView.findViewById(R.id.cancle);
            txt_mealtype_new = itemView.findViewById(R.id.txt_mealtype_new);
            txt_mealtype_main_header = itemView.findViewById(R.id.txt_mealtype_main_header);
        }



    }
    public void customArrrylsit(ArrayList<FoodListByMealType.Datum> commonAddmealList){



    }
}
