package com.shamrock.reework.activity.FoodModule.model.foodtripuser;

import java.io.Serializable;

public class UserFoodTripData implements Serializable {

    private String RecipeImagePath;

    private String UserMealId;

    private String Carbs;

    private String Fat;

    private String RecipeId;

    private String ItemName;

    private String Fibre;

    private String Quantity;

    private String Mesurement;

    private boolean IsFav;

    private double Calories;

    public double getCalories() {
        return Calories;
    }

    public void setCalories(double calories) {
        Calories = calories;
    }

    private String Protein;

    public String getRecipeImagePath() {
        return RecipeImagePath;
    }

    public void setRecipeImagePath(String recipeImagePath) {
        RecipeImagePath = recipeImagePath;
    }

    public String getUserMealId() {
        return UserMealId;
    }

    public void setUserMealId(String userMealId) {
        UserMealId = userMealId;
    }

    public String getCarbs() {
        return Carbs;
    }

    public void setCarbs(String carbs) {
        Carbs = carbs;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(String recipeId) {
        RecipeId = recipeId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getFibre() {
        return Fibre;
    }

    public void setFibre(String fibre) {
        Fibre = fibre;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getMesurement() {
        return Mesurement;
    }

    public void setMesurement(String mesurement) {
        Mesurement = mesurement;
    }

    public boolean isFav() {
        return IsFav;
    }

    public void setFav(boolean fav) {
        IsFav = fav;
    }



    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }
}
