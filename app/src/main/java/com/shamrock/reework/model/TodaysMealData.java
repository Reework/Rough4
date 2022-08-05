package com.shamrock.reework.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodaysMealData {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {
        @SerializedName("IntakeTime")
        @Expose
        private String IntakeTime;

        @SerializedName("CreatedOn")
        @Expose
        private String CreatedOn;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("meal_type")
        @Expose
        private String mealType;
        @SerializedName("meal_cal_min")
        @Expose
        private double mealCalMin;
        @SerializedName("meal_cal_max")
        @Expose
        private double mealCalMax;

        public String getIntakeTime() {
            return IntakeTime;
        }

        public void setIntakeTime(String intakeTime) {
            IntakeTime = intakeTime;
        }

        @SerializedName("Lst_SubMealData")
        @Expose

        private List<LstSubMealDatum> lstSubMealData = null;


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


        public double getMealCalMin() {
            return mealCalMin;
        }

        public void setMealCalMin(double mealCalMin) {
            this.mealCalMin = mealCalMin;
        }

        public void setMealCalMin(Integer mealCalMin) {
            this.mealCalMin = mealCalMin;
        }

        public double getMealCalMax() {
            return mealCalMax;
        }

        public void setMealCalMax(double mealCalMax) {
            this.mealCalMax = mealCalMax;
        }

        public void setMealCalMax(Integer mealCalMax) {
            this.mealCalMax = mealCalMax;
        }

        public List<LstSubMealDatum> getLstSubMealData() {
            return lstSubMealData;
        }

        public void setLstSubMealData(List<LstSubMealDatum> lstSubMealData) {
            this.lstSubMealData = lstSubMealData;
        }

    }
    public class LstSubMealDatum {


        @SerializedName("Fat")
        @Expose
        private String fat;
        @SerializedName("Carbs")
        @Expose
        private String carb;

        @SerializedName("Fibre")
        @Expose
        private String fibre;

        @SerializedName("Protein")
        @Expose
        private String protin;





        @SerializedName("RecipeImagePath")
        @Expose
        private String RecipeImagePath;


        @SerializedName("UnitText")
        @Expose
        private String UnitText;

        @SerializedName("UomId")
        @Expose
        private int UomId;


        @SerializedName("ValueInGram")
        @Expose
        private double ValueInGram;



        @SerializedName("UserMealId")
        @Expose
        private Integer userMealId;
        @SerializedName("RecipeId")
        @Expose
        private Integer RecipeId;
        @SerializedName("meal_name")
        @Expose
        private String mealName;
        @SerializedName("meal_img")
        @Expose
        private String mealImg;
        @SerializedName("meal_qty")
        @Expose
        private double mealQty;
        @SerializedName("meal_calory")
        @Expose
        private double mealCalory;
        @SerializedName("IntakeTime")
        @Expose
        private String IntakeTime;


        private int ItemTypeId;

        public int getItemTypeId() {
            return ItemTypeId;
        }

        public void setItemTypeId(int itemTypeId) {
            ItemTypeId = itemTypeId;
        }

        public String getFat() {
            return fat;
        }

        public void setFat(String fat) {
            this.fat = fat;
        }

        public String getCarb() {
            return carb;
        }

        public void setCarb(String carb) {
            this.carb = carb;
        }

        public String getFibre() {
            return fibre;
        }

        public void setFibre(String fibre) {
            this.fibre = fibre;
        }

        public String getProtin() {
            return protin;
        }

        public void setProtin(String protin) {
            this.protin = protin;
        }

        public String getUnitText() {
            return UnitText;
        }

        public void setUnitText(String unitText) {
            UnitText = unitText;
        }

        public String getIntakeTime() {
            return IntakeTime;
        }

        public void setIntakeTime(String intakeTime) {
            IntakeTime = intakeTime;
        }

        public Integer getUserMealId() {
            return userMealId;
        }

        public void setUserMealId(Integer userMealId) {
            this.userMealId = userMealId;
        }

        public Integer getRecipeId() {
            return RecipeId;
        }

        public void setRecipeId(Integer recipeId) {
            RecipeId = recipeId;
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



        public String getRecipeImagePath() {
            return RecipeImagePath;
        }

        public void setRecipeImagePath(String recipeImagePath) {
            RecipeImagePath = recipeImagePath;
        }


        //
//        public Integer getFoodId() {
//            return foodId;
//        }
//
//        public void setFoodId(Integer foodId) {
//            this.foodId = foodId;
//        }

        public String getMealName() {
            return mealName;
        }

        public void setMealName(String mealName) {
            this.mealName = mealName;
        }

        public String getMealImg() {
            return mealImg;
        }

        public void setMealImg(String mealImg) {
            this.mealImg = mealImg;
        }

        public double getMealQty() {
            return mealQty;
        }

        public void setMealQty(double mealQty) {
            this.mealQty = mealQty;
        }

        public double getMealCalory() {
            return mealCalory;
        }

        public void setMealCalory(double mealCalory) {
            this.mealCalory = mealCalory;
        }

//        public Integer getMealCalory() {
//            return mealCalory;
//        }
//
//        public void setMealCalory(Integer mealCalory) {
//            this.mealCalory = mealCalory;
//        }
//
    }
}
