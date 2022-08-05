package com.shamrock.reework.activity.FoodModule.history;

import java.util.ArrayList;

public class Data {
    private String CreatedOn;
    private ArrayList<MealIntakeByMealType> MealIntakeByMealType;

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public ArrayList<com.shamrock.reework.activity.FoodModule.history.MealIntakeByMealType> getMealIntakeByMealType() {
        return MealIntakeByMealType;
    }

    public void setMealIntakeByMealType(ArrayList<com.shamrock.reework.activity.FoodModule.history.MealIntakeByMealType> mealIntakeByMealType) {
        MealIntakeByMealType = mealIntakeByMealType;
    }
}
