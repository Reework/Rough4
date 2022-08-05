package com.shamrock.reework.activity.FoodModule.model;

import java.util.ArrayList;

public class FoodHistoryData {
    String MealType;
    double TotalCalories;
    double TotalProtein;
    double TotalCarbs;
    double TotalFat;
    double TotalFibre;
    int MaxCalories;
    private ArrayList<FoodMealData> Meals;


    public ArrayList<FoodMealData> getMeals() {
        return Meals;
    }

    public void setMeals(ArrayList<FoodMealData> meals) {
        Meals = meals;
    }

    public String getMealType() {
        return MealType;
    }

    public void setMealType(String mealType) {
        MealType = mealType;
    }

    public double getTotalCalories() {
        return TotalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        TotalCalories = totalCalories;
    }

    public double getTotalProtein() {
        return TotalProtein;
    }

    public void setTotalProtein(double totalProtein) {
        TotalProtein = totalProtein;
    }

    public double getTotalCarbs() {
        return TotalCarbs;
    }

    public void setTotalCarbs(double totalCarbs) {
        TotalCarbs = totalCarbs;
    }

    public double getTotalFat() {
        return TotalFat;
    }

    public void setTotalFat(double totalFat) {
        TotalFat = totalFat;
    }

    public double getTotalFibre() {
        return TotalFibre;
    }

    public void setTotalFibre(double totalFibre) {
        TotalFibre = totalFibre;
    }

    public int getMaxCalories() {
        return MaxCalories;
    }

    public void setMaxCalories(int maxCalories) {
        MaxCalories = maxCalories;
    }


    @Override
    public String toString() {
        return "FoodHistoryData{" +
                "MealType='" + MealType + '\'' +
                ", TotalCalories=" + TotalCalories +
                ", TotalProtein=" + TotalProtein +
                ", TotalCarbs=" + TotalCarbs +
                ", TotalFat=" + TotalFat +
                ", TotalFibre=" + TotalFibre +
                ", MaxCalories=" + MaxCalories +
                ", Meals=" + Meals +
                '}';
    }
}
