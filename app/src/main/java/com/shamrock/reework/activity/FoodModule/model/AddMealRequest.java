package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public  class AddMealRequest  implements Serializable {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("meal_type")

    @Expose
    private String mealType;
    @SerializedName("Lst_SubMealData")
    @Expose
    private List<LstSubMealDatum> lstSubMealData = null;



    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;


    @SerializedName("IntakeTime")
    @Expose
    private String IntakeTime;


    @SerializedName("ItemTypeId")
    @Expose
    private Integer ItemTypeId;

    public Integer getItemTypeId() {
        return ItemTypeId;
    }

    public void setItemTypeId(Integer itemTypeId) {
        ItemTypeId = itemTypeId;
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

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public List<LstSubMealDatum> getLstSubMealData() {
        return lstSubMealData;
    }

    public void setLstSubMealData(List<LstSubMealDatum> lstSubMealData) {
        this.lstSubMealData = lstSubMealData;
    }



    public static class LstSubMealDatum {

        @SerializedName("meal_type")
        @Expose
        private String meal_type;

        @SerializedName("UomId")
        @Expose
        private int UomId;


        @SerializedName("ValueInGram")
        @Expose
        private double ValueInGram;

        @SerializedName("RecipeId")
        @Expose
        private Integer RecipeId;
        @SerializedName("meal_qty")
        @Expose
        private String mealQty;

        @SerializedName("IntakeTime")
        @Expose
        private String IntakeTime;


        public String getMeal_type() {
            return meal_type;
        }

        public void setMeal_type(String meal_type) {
            this.meal_type = meal_type;
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

        public int getUomId() {
            return UomId;
        }

        public void setUomId(Integer uomId) {
            UomId = uomId;
        }

        public String getIntakeTime() {
            return IntakeTime;
        }

        public void setIntakeTime(String intakeTime) {
            IntakeTime = intakeTime;
        }

        public Integer getRecipeId() {
            return RecipeId;
        }

        public void setRecipeId(Integer recipeId) {
            RecipeId = recipeId;
        }

        public String getMealQty() {
            return mealQty;
        }

        public void setMealQty(String mealQty) {
            this.mealQty = mealQty;
        }

    }

}