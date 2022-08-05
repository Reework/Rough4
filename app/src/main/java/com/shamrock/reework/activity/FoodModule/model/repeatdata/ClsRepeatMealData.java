package com.shamrock.reework.activity.FoodModule.model.repeatdata;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsRepeatMealData {
    private String meal_type;

    private String UserId;

    private String meal_cal_max;

    @SerializedName("Lst_SubMealData")
    private ArrayList<ClsRepeatLst_SubMealData> Lst_SubMealData;

    private String meal_cal_min;

    private String CreatedOn;

    private String MealId;

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getMeal_cal_max() {
        return meal_cal_max;
    }

    public void setMeal_cal_max(String meal_cal_max) {
        this.meal_cal_max = meal_cal_max;
    }


    public ArrayList<ClsRepeatLst_SubMealData> getLst_SubMealData() {
        return Lst_SubMealData;
    }

    public void setLst_SubMealData(ArrayList<ClsRepeatLst_SubMealData> lst_SubMealData) {
        Lst_SubMealData = lst_SubMealData;
    }

    public String getMeal_cal_min() {
        return meal_cal_min;
    }

    public void setMeal_cal_min(String meal_cal_min) {
        this.meal_cal_min = meal_cal_min;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getMealId() {
        return MealId;
    }

    public void setMealId(String mealId) {
        MealId = mealId;
    }
}
