package com.shamrock.reework.activity.dietplan.pojo;

import android.widget.ScrollView;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodPlan  implements Serializable {
    private double Carb;

    private String MealType;

    private String MealName;

    private String UOM;

    private int FoodPlanId;

    private double Fat;

    private double Quantity;

    private double Fibre;

    private double Calories;

    private double Protein;

    private String MealTime;
    private String Remark;
    private double ValueInGram;

    @SerializedName("FinishedProductId")
    private int FinishedProductId;
    private int RecipeId;

    public int getFinishedProductId() {
        return FinishedProductId;
    }

    public void setFinishedProductId(int finishedProductId) {
        FinishedProductId = finishedProductId;
    }

    private  int UomId;

    public double getValueInGram() {
        return ValueInGram;
    }

    public void setValueInGram(double valueInGram) {
        ValueInGram = valueInGram;
    }

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int recipeId) {
        RecipeId = recipeId;
    }

    public int getUomId() {
        return UomId;
    }

    public void setUomId(int uomId) {
        UomId = uomId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public double getCarb() {
        return Carb;
    }

    public void setCarb(double carb) {
        Carb = carb;
    }

    public String getMealType() {
        return MealType;
    }

    public void setMealType(String mealType) {
        MealType = mealType;
    }

    public String getMealName() {
        return MealName;
    }

    public void setMealName(String mealName) {
        MealName = mealName;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public int getFoodPlanId() {
        return FoodPlanId;
    }

    public void setFoodPlanId(int foodPlanId) {
        FoodPlanId = foodPlanId;
    }

    public double getFat() {
        return Fat;
    }

    public void setFat(double fat) {
        Fat = fat;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public double getFibre() {
        return Fibre;
    }

    public void setFibre(double fibre) {
        Fibre = fibre;
    }

    public double getCalories() {
        return Calories;
    }

    public void setCalories(double calories) {
        Calories = calories;
    }

    public double getProtein() {
        return Protein;
    }

    public void setProtein(double protein) {
        Protein = protein;
    }

    public String getMealTime() {
        return MealTime;
    }

    public void setMealTime(String mealTime) {
        MealTime = mealTime;
    }
}
