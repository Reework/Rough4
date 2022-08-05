package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditMealRequest {

    @SerializedName("meal_type")
    @Expose
    private String mealType;

//    @SerializedName("recipeName")
//    @Expose
//    private String recipeName;


    @SerializedName("UomId")
    @Expose
    private int UomId;


    @SerializedName("ValueInGram")
    @Expose
    private double ValueInGram;


    @SerializedName("UserMealId")
    @Expose
    private Integer userId;
    @SerializedName("RecipeId")
    @Expose
    private Integer foodId;
    @SerializedName("meal_qty")
    @Expose
    private double mealQty;

    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;

    @SerializedName("IntakeTime")
    @Expose
    private String IntakeTime;

    public int getUomId() {
        return UomId;
    }

    public void setUomId(int uomId) {
        UomId = uomId;
    }

    public double getValueInGram() {
        return ValueInGram;
    }

    public void setValueInGram(double valueInGram) {
        ValueInGram = valueInGram;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public double getMealQty() {
        return mealQty;
    }

    public void setMealQty(double mealQty) {
        this.mealQty = mealQty;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

//    public String getRecipeName() {
//        return recipeName;
//    }
//
//    public void setRecipeName(String recipeName) {
//        this.recipeName = recipeName;
//    }
}
