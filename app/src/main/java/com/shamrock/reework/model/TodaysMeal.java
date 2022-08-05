package com.shamrock.reework.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TodaysMeal implements Serializable {
    String meal_name, meal_cal_min, meal_img, meal_calory,meal_type_name;
    int  meal_type,food_id;
    String IntakeTime;
    double meal_quantity;
    int UomId;
    double ValueInGram;
    double meal_cal_max;
    String UnitText;
    String RecipeImagePath;
    int ItemTypeId;

    public int getItemTypeId() {
        return ItemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        ItemTypeId = itemTypeId;
    }

    String protin;
    String fibre;
    String carbs;
    String fat;

    public String getProtin() {
        return protin;
    }

    public void setProtin(String protin) {
        this.protin = protin;
    }

    public String getFibre() {
        return fibre;
    }

    public void setFibre(String fibre) {
        this.fibre = fibre;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getRecipeImagePath() {
        return RecipeImagePath;
    }

    public void setRecipeImagePath(String recipeImagePath) {
        RecipeImagePath = recipeImagePath;
    }

    String CreatedOn;

    public String getUnitText() {
        return UnitText;
    }

    public void setUnitText(String unitText) {
        UnitText = unitText;
    }

    public int getUomId() {
        return UomId;
    }

    public double getValueInGram() {
        return ValueInGram;
    }

    public void setValueInGram(double valueInGram) {
        ValueInGram = valueInGram;
    }

    public void setUomId(int uomId) {
        UomId = uomId;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public Integer getUserMealId() {
        return userMealId;
    }

    public void setUserMealId(Integer userMealId) {
        this.userMealId = userMealId;
    }

    private Integer userMealId;

    public TodaysMeal() {
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getIntakeTime() {
        return IntakeTime;
    }

    public void setIntakeTime(String intakeTime) {
        IntakeTime = intakeTime;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }
    public void setMeal_type_name(String meal_type_name) {
        this.meal_type_name = meal_type_name;
    }
    public String getMeal_type_name() {
        return meal_type_name;
    }

    public String getMeal_cal_min() {
        return meal_cal_min;
    }

    public void setMeal_cal_min(String meal_cal_min) {
        this.meal_cal_min = meal_cal_min;
    }

    public double getMeal_cal_max() {
        return meal_cal_max;
    }

    public void setMeal_cal_max(double meal_cal_max) {
        this.meal_cal_max = meal_cal_max;
    }

    public int getFood_Id() {
        return food_id;
    }


    public String getMeal_img() {
        return meal_img;
    }

    public void setMeal_img(String meal_img) {
        this.meal_img = meal_img;
    }

    public String getMeal_calory() {
        return meal_calory;
    }

    public void setMeal_calory(String meal_calory) {
        this.meal_calory = meal_calory;
    }

    public double getMeal_quantity() {
        return meal_quantity;
    }

    public void setMeal_quantity(double meal_quantity) {
        this.meal_quantity = meal_quantity;
    }

    public int getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(int meal_type) {
        this.meal_type = meal_type;
    }
    public void setFood_Id(int food_id) {
        this.food_id = food_id;
    }
}
