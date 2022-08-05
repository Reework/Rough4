package com.shamrock.reework.activity.dietplan.pojo;

public class AddFoodPlanRequest {
    private int MealTypeId;

    private double Quantity;

    private int RdpId;

    private double Calories;

    private double Protein;

    private int UomId;

    private String Remark;

    private double Carb;

    private int FoodPlanId;

    private double Fat;

    private double Fibre;

    private int MealId;

    private String MealTime;

    public int getMealTypeId() {
        return MealTypeId;
    }

    public void setMealTypeId(int mealTypeId) {
        MealTypeId = mealTypeId;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public int getRdpId() {
        return RdpId;
    }

    public void setRdpId(int rdpId) {
        RdpId = rdpId;
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

    public double getFibre() {
        return Fibre;
    }

    public void setFibre(double fibre) {
        Fibre = fibre;
    }

    public int getMealId() {
        return MealId;
    }

    public void setMealId(int mealId) {
        MealId = mealId;
    }

    public String getMealTime() {
        return MealTime;
    }

    public void setMealTime(String mealTime) {
        MealTime = mealTime;
    }
}
