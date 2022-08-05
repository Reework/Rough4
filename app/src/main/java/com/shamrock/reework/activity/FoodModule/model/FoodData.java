package com.shamrock.reework.activity.FoodModule.model;

import java.util.ArrayList;

public class FoodData {

    private String CreatedOn;
    private double TotalCaloriesIntake;
    private double TotalProteinIntake;
    private double TotalCarbsIntake;
    private double TotalFatIntake;
    private double TotalFibreIntake;
    private double GrandTotal;

    private ArrayList<FoodHistoryData> MealIntakeByMealType;


    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public double getTotalCaloriesIntake() {
        return TotalCaloriesIntake;
    }

    public void setTotalCaloriesIntake(double totalCaloriesIntake) {
        TotalCaloriesIntake = totalCaloriesIntake;
    }

    public double getTotalProteinIntake() {
        return TotalProteinIntake;
    }

    public void setTotalProteinIntake(double totalProteinIntake) {
        TotalProteinIntake = totalProteinIntake;
    }

    public double getTotalCarbsIntake() {
        return TotalCarbsIntake;
    }

    public void setTotalCarbsIntake(double totalCarbsIntake) {
        TotalCarbsIntake = totalCarbsIntake;
    }

    public double getTotalFatIntake() {
        return TotalFatIntake;
    }

    public void setTotalFatIntake(double totalFatIntake) {
        TotalFatIntake = totalFatIntake;
    }

    public double getTotalFibreIntake() {
        return TotalFibreIntake;
    }

    public void setTotalFibreIntake(double totalFibreIntake) {
        TotalFibreIntake = totalFibreIntake;
    }

    public double getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        GrandTotal = grandTotal;
    }

    public ArrayList<FoodHistoryData> getMealIntakeByMealType() {
        return MealIntakeByMealType;
    }

    public void setMealIntakeByMealType(ArrayList<FoodHistoryData> mealIntakeByMealType) {
        MealIntakeByMealType = mealIntakeByMealType;
    }

    @Override
    public String toString() {
        return "FoodData{" +
                "CreatedOn='" + CreatedOn + '\'' +
                ", TotalCaloriesIntake=" + TotalCaloriesIntake +
                ", TotalProteinIntake=" + TotalProteinIntake +
                ", TotalCarbsIntake=" + TotalCarbsIntake +
                ", TotalFatIntake=" + TotalFatIntake +
                ", TotalFibreIntake=" + TotalFibreIntake +
                ", GrandTotal=" + GrandTotal +
                ", MealIntakeByMealType=" + MealIntakeByMealType +
                '}';
    }
}
