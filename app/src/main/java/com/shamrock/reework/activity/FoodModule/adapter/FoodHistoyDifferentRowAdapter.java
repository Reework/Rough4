package com.shamrock.reework.activity.FoodModule.adapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.fragment.MasterFoodFragmentAllMeals;
import com.shamrock.reework.adapter.DifferentRowAdapter;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.model.TodaysMealData;

import java.util.List;

public class FoodHistoyDifferentRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CITY_TYPE = 1;
    private static final int ROW_TYPE = 2;
    private static final int FOOTER_TYPE = 3;
    private List<TodaysMeal> mList;
    OnMealClickListner listner;
    String strApiCreatedOn;
    List<TodaysMealData.Datum> data;
    private MasterFoodFragmentAllMeals context;

    public interface  OnMealClickListner{
        public void onClickMeal(int position, TodaysMeal model);
    }
    public FoodHistoyDifferentRowAdapter(MasterFoodFragmentAllMeals context, List<TodaysMeal> list, List<TodaysMealData.Datum> data) {
        this.mList = list;
        this.listner=(OnMealClickListner)context;
        this.data=data;
        this.context=context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case CITY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_meal_header, parent, false);
                return new HeaderViewHolder(view);
            case ROW_TYPE:
            case FOOTER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_new_all_meal_row, parent, false);
                return new RowViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TodaysMeal object = mList.get(position);
        if (object != null) {
            switch (object.getMeal_type()) {
                case CITY_TYPE:
                    ((HeaderViewHolder) holder).mTitle.setText(object.getMeal_type_name());
                    int max= (int) object.getMeal_cal_max();
//                    ((HeaderViewHolder) holder).progressBarAllMeal.setMax(max);
//                    ((HeaderViewHolder) holder).progressBarAllMeal.setProgress(Integer.parseInt(object.getMeal_cal_min()));
//
//
//
//
//                    ((HeaderViewHolder) holder).mCalMin.setText(object.getMeal_cal_min());
//                    ((HeaderViewHolder) holder).mCalMax.setText(String.valueOf(object.getMeal_cal_max()));


//                    int max= (int) object.getMeal_cal_max();
                    double min= Double.parseDouble(object.getMeal_cal_min());
                    if (min<max){
                        ((HeaderViewHolder) holder).progressBarAllMeal.setMax(max);
                        ((HeaderViewHolder) holder).progressBarAllMeal.setProgress(Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(String.valueOf(object.getMeal_cal_min()))))));
                        ((HeaderViewHolder) holder).mCalMin.setText(String.valueOf(object.getMeal_cal_min()));
                        ((HeaderViewHolder) holder).mCalMax.setText(String.valueOf(object.getMeal_cal_max()));
                    }else {
                        ((HeaderViewHolder) holder).progressBarAllMeal.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_red));
                        ((HeaderViewHolder) holder).progressBarAllMeal.setMax((int) min);
                        ((HeaderViewHolder) holder).progressBarAllMeal.setProgress(Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(String.valueOf(object.getMeal_cal_max()))))));
                        ((HeaderViewHolder) holder).mCalMin.setText(String.valueOf(object.getMeal_cal_max()));
                        ((HeaderViewHolder) holder).mCalMax.setText(String.valueOf(object.getMeal_cal_min()));
                        ((HeaderViewHolder) holder).mCalMax.setTextColor(context.getResources().getColor(R.color.dark_red));
                        ((HeaderViewHolder) holder).mCalMin.setTextColor(context.getResources().getColor(R.color.black));
                    }


                    break;
                case ROW_TYPE:
                case FOOTER_TYPE:
                    String intaktimes="";
                    if (object.getIntakeTime()!=null){
//                        intaktimes="("+object.getIntakeTime()+")";

                    }
                    ((RowViewHolder) holder).mTitle.setText(object.getMeal_name()+intaktimes);
                    ((RowViewHolder) holder).textmealtime_all.setText(object.getIntakeTime());
                    ((RowViewHolder) holder).mQuantity.setText(""+object.getMeal_quantity());

                    ((RowViewHolder) holder).mCalories.setText(object.getMeal_calory());
                    ((RowViewHolder) holder).linItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listner.onClickMeal(position,object);
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            TodaysMeal object = mList.get(position);
            if (object != null) {
                return object.getMeal_type();
            }
        }
        return 0;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mCalMin, mCalMax,textmealtime_all;
        private ProgressBar progressBarAllMeal;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.text_AllMeal_TypeName);
            mCalMin = itemView.findViewById(R.id.text_AllMeal_Cal_Min);
            mCalMax = itemView.findViewById(R.id.text_AllMeal_Cal_Max);
            progressBarAllMeal = itemView.findViewById(R.id.progressBarAllMeal);
        }
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mCalories,textmealtime_all;
        private ImageView mImage;
        ConstraintLayout linItem;
        TextView mQuantity;

        public RowViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textAllMealRowName);
            mImage = itemView.findViewById(R.id.imgAllMeal_item_image);
            mQuantity = itemView.findViewById(R.id.textAllMealQty);
            mCalories = itemView.findViewById(R.id.textAllMealCalories);
            linItem = itemView.findViewById(R.id.linItem);
            textmealtime_all = itemView.findViewById(R.id.textmealtime_all);
        }
    }
}