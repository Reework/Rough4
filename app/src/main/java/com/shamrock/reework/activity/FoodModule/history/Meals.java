package com.shamrock.reework.activity.FoodModule.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meals implements Serializable {
    private String MealQuantity;

    private String MealType;

    private String MealName;

    private String IntakeTime;

    private String MealTypeId;

    private String UserMealId;

    private String RecipeId;

    public String getRecipeImagePath() {
        return RecipeImagePath;
    }

    public void setRecipeImagePath(String recipeImagePath) {
        RecipeImagePath = recipeImagePath;
    }

    private String CreatedOn;

    private String Calories;
     private double MaxCalories;

     private String Measurement;

    private String RecipeImagePath;
    private int UomId;

    public int getUomId() {
        return UomId;
    }

    public void setUomId(int uomId) {
        UomId = uomId;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public double getMaxCalories() {
        return MaxCalories;
    }

    public void setMaxCalories(double maxCalories) {
        MaxCalories = maxCalories;
    }

    public String getMealQuantity() {
        return MealQuantity;
    }

    public void setMealQuantity(String mealQuantity) {
        MealQuantity = mealQuantity;
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

    public String getIntakeTime() {
        return IntakeTime;
    }

    public void setIntakeTime(String intakeTime) {
        IntakeTime = intakeTime;
    }

    public String getMealTypeId() {
        return MealTypeId;
    }

    public void setMealTypeId(String mealTypeId) {
        MealTypeId = mealTypeId;
    }

    public String getUserMealId() {
        return UserMealId;
    }

    public void setUserMealId(String userMealId) {
        UserMealId = userMealId;
    }

    public String getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(String recipeId) {
        RecipeId = recipeId;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }
}
