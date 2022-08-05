package com.shamrock.reework.activity.FoodModule.model;

public class FoodMealData {

    String MealType,MealName,ItemName,IntakeTime,CreatedOn,Measurement,RecipeImagePath;
    int todaystatus,MealTypeId,UserMealId,RecipeId,UomId;
    double Calories,Protein,Carbs,Fat,Fibre,MealQuantity;


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

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getIntakeTime() {
        return IntakeTime;
    }

    public void setIntakeTime(String intakeTime) {
        IntakeTime = intakeTime;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public String getRecipeImagePath() {
        return RecipeImagePath;
    }

    public void setRecipeImagePath(String recipeImagePath) {
        RecipeImagePath = recipeImagePath;
    }

    public double getMealQuantity() {
        return MealQuantity;
    }

    public void setMealQuantity(double mealQuantity) {
        MealQuantity = mealQuantity;
    }

    public int getTodaystatus() {
        return todaystatus;
    }

    public void setTodaystatus(int todaystatus) {
        this.todaystatus = todaystatus;
    }

    public int getMealTypeId() {
        return MealTypeId;
    }

    public void setMealTypeId(int mealTypeId) {
        MealTypeId = mealTypeId;
    }

    public int getUserMealId() {
        return UserMealId;
    }

    public void setUserMealId(int userMealId) {
        UserMealId = userMealId;
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

    public double getCarbs() {
        return Carbs;
    }

    public void setCarbs(double carbs) {
        Carbs = carbs;
    }

    public double getFat() {
        return Fat;
    }

    public void setFat(double fat) {
        Fat = fat;
    }

    public double getFibre() {
        return Fibre;
    }

    public void setFibre(double fibre) {
        Fibre = fibre;
    }


    @Override
    public String toString() {
        return "FoodMealData{" +
                "MealType='" + MealType + '\'' +
                ", MealName='" + MealName + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", IntakeTime='" + IntakeTime + '\'' +
                ", CreatedOn='" + CreatedOn + '\'' +
                ", Measurement='" + Measurement + '\'' +
                ", RecipeImagePath='" + RecipeImagePath + '\'' +
                ", todaystatus=" + todaystatus +
                ", MealTypeId=" + MealTypeId +
                ", UserMealId=" + UserMealId +
                ", RecipeId=" + RecipeId +
                ", UomId=" + UomId +
                ", Calories=" + Calories +
                ", Protein=" + Protein +
                ", Carbs=" + Carbs +
                ", Fat=" + Fat +
                ", Fibre=" + Fibre +
                ", MealQuantity=" + MealQuantity +
                '}';
    }
}
