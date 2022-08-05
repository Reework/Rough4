package com.shamrock.reework.activity.FoodModule.history;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.fragment.FoodHistoryFragment;
import com.shamrock.reework.activity.recipe.model.daillogs.cuision.mealtype.MealTypeListAdapter;
import com.shamrock.reework.adapter.DifferentRowAdapter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealTypeHistorySubAdapter extends RecyclerView.Adapter<MealTypeHistorySubAdapter.MyHolder>
{
    private ArrayList<MealIntakeByMealType> list;
    Context context;
    private MealListHistorySubAdapter activityHistorySubAdapter;
    FoodHistoryFragment foodHistoryFragmen;
    public MealTypeHistorySubAdapter(Context context, ArrayList<MealIntakeByMealType> list, FoodHistoryFragment foodHistoryFragmen)
    {
        this.list = list;
        this.context = context;
        this.foodHistoryFragmen=foodHistoryFragmen;
    }


    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView text_AllMeal_TypeName,text_AllMeal_Cal_Min,text_AllMeal_Cal_Max;
        RecyclerView recyler_meal_list;
        ImageView delete,edit,ivFitBit;
        private ProgressBar progressBarAllMeal;
        View viewt;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);

            this.text_AllMeal_TypeName = itemView.findViewById(R.id.text_AllMeal_TypeName);
            this.recyler_meal_list = itemView.findViewById(R.id.recyler_meal_list);
            this.text_AllMeal_Cal_Min = itemView.findViewById(R.id.text_AllMeal_Cal_Min);
            this.text_AllMeal_Cal_Max = itemView.findViewById(R.id.text_AllMeal_Cal_Max);
            this.progressBarAllMeal = itemView.findViewById(R.id.progressBarAllMeal);
            this.viewt = itemView.findViewById(R.id.v);



        }
    }

    @NonNull
    @Override
    public MealTypeHistorySubAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_history_meal_type, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealTypeHistorySubAdapter.MyHolder holder, int i)
    {
        MealIntakeByMealType model = list.get(i);
        if (model!=null)
        {
            if (list.get(i).getMealType()!=null){






                if(list.get(i).getMealType().equals("Post Wakeup")){
                    holder.text_AllMeal_TypeName.setText("Post wakeup " );

                }
                else  if(list.get(i).getMealType().equals("Mid-Morning")){
                    holder.text_AllMeal_TypeName.setText("Mid-morning ");
                }
                else  if(list.get(i).getMealType().equals("Beverages & Snacks")){
                    holder.text_AllMeal_TypeName.setText("Beverages & snacks ");
                }
                else  if(list.get(i).getMealType().equals("Pre-Dinner")){
                    holder.text_AllMeal_TypeName.setText("Pre-dinner " );
                }

                else  if(list.get(i).getMealType().equals("1 Hour After Dinner")){
                    holder.text_AllMeal_TypeName.setText("1 Hour after dinner ");
                }
                else  if(list.get(i).getMealType().equals("Before sleep")){
                    holder.text_AllMeal_TypeName.setText("Before sleep ");
                }
                else  if(list.get(i).getMealType().equals("Combo (Breakfast+Lunch etc.)")){
                    holder.text_AllMeal_TypeName.setText(list.get(i).getMealType());
                }
                else  if(list.get(i).getMealType().equals("Pre-Workout")){
                    holder.text_AllMeal_TypeName.setText("Pre-workout ");
                }
                else  if(list.get(i).getMealType().equals("Post-Workout")){
                    holder.text_AllMeal_TypeName.setText("Post-Workout ");
                }

                else{
                    holder.text_AllMeal_TypeName.setText(list.get(i).getMealType());
                }




//                holder.text_AllMeal_TypeName.setText(list.get(i).getMealType());









                int max= (int) list.get(i).getMaxCalories();
                double min= Double.parseDouble(String.valueOf(list.get(i).getTotalCalories()));
                if (min<max){
                    holder.progressBarAllMeal.setMax(max);
                    holder.progressBarAllMeal.setProgress(Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(String.valueOf(list.get(i).getTotalCalories()))))));

                    DecimalFormat decimalFormat = new DecimalFormat("0.0");
                    String showschedulestr = decimalFormat.format(list.get(i).getTotalCalories());
                    if (showschedulestr.endsWith(".0")) {
                        holder.text_AllMeal_Cal_Min.setText(String.valueOf(Math.round((list.get(i).getTotalCalories()))));
                    } else {
                        holder.text_AllMeal_Cal_Min.setText(showschedulestr);
                    }



                    DecimalFormat decimalFormat1 = new DecimalFormat("0.0");
                    String showschedulestr1 = decimalFormat.format(list.get(i).getMaxCalories());
                    if (showschedulestr1.endsWith(".0")) {
                        holder.text_AllMeal_Cal_Max.setText(String.valueOf(Math.round((list.get(i).getMaxCalories()))));
                    } else {
                        holder.text_AllMeal_Cal_Max.setText(showschedulestr1);
                    }



//                    holder.text_AllMeal_Cal_Min.setText(String.valueOf(list.get(i).getTotalCalories()));
//                    holder.text_AllMeal_Cal_Max.setText(String.valueOf(list.get(i).getMaxCalories()));
                }else {
                    holder.progressBarAllMeal.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_red));
                    holder.progressBarAllMeal.setMax((int) min);
                    holder.progressBarAllMeal.setProgress(Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(String.valueOf(list.get(i).getMaxCalories()))))));
//                    holder.text_AllMeal_Cal_Min.setText(String.valueOf(list.get(i).getMaxCalories()));
//                    holder.text_AllMeal_Cal_Max.setText(String.valueOf(list.get(i).getTotalCalories()));


                    DecimalFormat decimalFormat = new DecimalFormat("0.0");
                    String showschedulestr = decimalFormat.format(list.get(i).getMaxCalories());
                    if (showschedulestr.endsWith(".0")) {
                        holder.text_AllMeal_Cal_Min.setText(String.valueOf(Math.round((list.get(i).getTotalCalories()))));
                    } else {
                        holder.text_AllMeal_Cal_Min.setText(showschedulestr);
                    }



                    DecimalFormat decimalFormat1 = new DecimalFormat("0.0");
                    String showschedulestr1 = decimalFormat.format(list.get(i).getTotalCalories());
                    if (showschedulestr1.endsWith(".0")) {
                        holder.text_AllMeal_Cal_Max.setText(String.valueOf(Math.round((list.get(i).getTotalCalories()))));
                    } else {
                        holder.text_AllMeal_Cal_Max.setText(showschedulestr);
                    }



                    holder.text_AllMeal_Cal_Max.setTextColor(context.getResources().getColor(R.color.dark_red));
                    holder.text_AllMeal_Cal_Min.setTextColor(context.getResources().getColor(R.color.black));
                }
//                holder.text_AllMeal_Cal_Min.setText(String.valueOf(list.get(i).getTotalCalories()));
//                holder.text_AllMeal_Cal_Max.setText(String.valueOf(list.get(i).getMaxCalories()));


//
//                int data= (int) list.get(i).getMaxCalories();
//       holder.progressBarAllMeal.setMax(data);
//                holder.progressBarAllMeal.setProgress((int) Math.round(list.get(i).getTotalCalories()));

                activityHistorySubAdapter = new MealListHistorySubAdapter(context, model.getMeals(),foodHistoryFragmen);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                holder.recyler_meal_list.setLayoutManager(layoutManager);
                holder.recyler_meal_list.setItemAnimator(new DefaultItemAnimator());
                holder.recyler_meal_list.setAdapter(activityHistorySubAdapter);


            }


            if(i== list.size()-1){
                holder.viewt.setVisibility(View.GONE);
            }



        }





    }
    public  String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" Hour ";
        }else {
            strhours=" Hours ";

        }
        return (totalMinutes / 60) + strhours + minutes+" Mins";
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }



    public String formatDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
}
