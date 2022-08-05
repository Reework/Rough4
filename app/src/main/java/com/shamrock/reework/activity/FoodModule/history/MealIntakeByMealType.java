package com.shamrock.reework.activity.FoodModule.history;

import java.util.ArrayList;

public class MealIntakeByMealType {
    private String MealType;
    private double TotalCalories;
    private ArrayList<Meals> Meals;
    private double MaxCalories;

    public double getMaxCalories() {
        return MaxCalories;
    }

    public void setMaxCalories(double maxCalories) {
        MaxCalories = maxCalories;
    }

    public double getTotalCalories() {
        return TotalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        TotalCalories = totalCalories;
    }

    public String getMealType() {
        return MealType;
    }

    public void setMealType(String mealType) {
        MealType = mealType;
    }

    public ArrayList<com.shamrock.reework.activity.FoodModule.history.Meals> getMeals() {
        return Meals;
    }

    public void setMeals(ArrayList<com.shamrock.reework.activity.FoodModule.history.Meals> meals) {
        Meals = meals;
    }
}
