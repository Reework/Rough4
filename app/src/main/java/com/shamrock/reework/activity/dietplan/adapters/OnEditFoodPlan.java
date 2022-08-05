package com.shamrock.reework.activity.dietplan.adapters;

import com.shamrock.reework.activity.dietplan.pojo.FoodPlan;

public interface OnEditFoodPlan {
    public void getEditFoodPlanData(FoodPlan foodPlan);
    public void deleteFoodPlanData(int id);

    void addToDailyDairy(FoodPlan foodPlan);
}
