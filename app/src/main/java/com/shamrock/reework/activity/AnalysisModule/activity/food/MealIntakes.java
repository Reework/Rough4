package com.shamrock.reework.activity.AnalysisModule.activity.food;

import com.google.gson.annotations.SerializedName;

public class MealIntakes {

    @SerializedName("Before Breakfast")
    private double BeforeBreakfast;
    private double Breakfast;
    private double Lunch;
    @SerializedName("Evening Snacks")
    private double EveningSnacks;
    private double Dinner;
    private double Dessert;


    public double getBeforeBreakfast() {
        return BeforeBreakfast;
    }

    public void setBeforeBreakfast(double beforeBreakfast) {
        BeforeBreakfast = beforeBreakfast;
    }

    public double getBreakfast() {
        return Breakfast;
    }

    public void setBreakfast(double breakfast) {
        Breakfast = breakfast;
    }

    public double getLunch() {
        return Lunch;
    }

    public void setLunch(double lunch) {
        Lunch = lunch;
    }

    public double getEveningSnacks() {
        return EveningSnacks;
    }

    public void setEveningSnacks(double eveningSnacks) {
        EveningSnacks = eveningSnacks;
    }

    public double getDinner() {
        return Dinner;
    }

    public void setDinner(double dinner) {
        Dinner = dinner;
    }

    public double getDessert() {
        return Dessert;
    }

    public void setDessert(double dessert) {
        Dessert = dessert;
    }
}
