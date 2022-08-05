package com.shamrock.reework.activity.HealthModule.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserHealthResponse  implements Serializable {
    @SerializedName("Message")
    @Expose
    private String Message;

//    @SerializedName("Data")
//    @Expose
//    private Data Data;

    @SerializedName("Code")
    @Expose
    private String Code;



    @SerializedName("Data")
    @Expose
    public Data Data;

    public Data getData() {
        return Data;
    }

    public void setData(Data data) {
        Data = data;
    }

    public class Data implements Serializable{


        private String ActivityStatus;

        private String FoodcultureID;

        private String HealthGoal;

        private String WeightUnit;

        private String Drink;

        private String Smoke;

        private String weight_Gain;

        private String CurrentScore;

        private String UserID;

        private String Height;

        private String Easy_Monitoring_of_Body_Parameters;

        private String Weight_Control;

        private String Healthdate;

        private String MedicalConditionID;

        private String Ideal_Weight;

        private String Weight;

        private String HealthParamID;

        private String FoodtypeID;

        private String Avgsleep;

        private String  FutureDailyScore;

        private String body_shape;

        private String body_Type;

        private String Health_And_Wellness;

        private String Waterintake;

        private String DailyScore;

        public String getActivityStatus() {
            return ActivityStatus;
        }

        public void setActivityStatus(String activityStatus) {
            ActivityStatus = activityStatus;
        }

        public String getFoodcultureID() {
            return FoodcultureID;
        }

        public void setFoodcultureID(String foodcultureID) {
            FoodcultureID = foodcultureID;
        }

        public String getHealthGoal() {
            return HealthGoal;
        }

        public void setHealthGoal(String healthGoal) {
            HealthGoal = healthGoal;
        }

        public String getWeightUnit() {
            return WeightUnit;
        }

        public void setWeightUnit(String weightUnit) {
            WeightUnit = weightUnit;
        }

        public String getDrink() {
            return Drink;
        }

        public void setDrink(String drink) {
            Drink = drink;
        }

        public String getSmoke() {
            return Smoke;
        }

        public void setSmoke(String smoke) {
            Smoke = smoke;
        }

        public String getWeight_Gain() {
            return weight_Gain;
        }

        public void setWeight_Gain(String weight_Gain) {
            this.weight_Gain = weight_Gain;
        }

        public String getCurrentScore() {
            return CurrentScore;
        }

        public void setCurrentScore(String currentScore) {
            CurrentScore = currentScore;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }

        public String getHeight() {
            return Height;
        }

        public void setHeight(String height) {
            Height = height;
        }

        public String getEasy_Monitoring_of_Body_Parameters() {
            return Easy_Monitoring_of_Body_Parameters;
        }

        public void setEasy_Monitoring_of_Body_Parameters(String easy_Monitoring_of_Body_Parameters) {
            Easy_Monitoring_of_Body_Parameters = easy_Monitoring_of_Body_Parameters;
        }

        public String getWeight_Control() {
            return Weight_Control;
        }

        public void setWeight_Control(String weight_Control) {
            Weight_Control = weight_Control;
        }

        public String getHealthdate() {
            return Healthdate;
        }

        public void setHealthdate(String healthdate) {
            Healthdate = healthdate;
        }

        public String getMedicalConditionID() {
            return MedicalConditionID;
        }

        public void setMedicalConditionID(String medicalConditionID) {
            MedicalConditionID = medicalConditionID;
        }

        public String getIdeal_Weight() {
            return Ideal_Weight;
        }

        public void setIdeal_Weight(String ideal_Weight) {
            Ideal_Weight = ideal_Weight;
        }

        public String getWeight() {
            return Weight;
        }

        public void setWeight(String weight) {
            Weight = weight;
        }

        public String getHealthParamID() {
            return HealthParamID;
        }

        public void setHealthParamID(String healthParamID) {
            HealthParamID = healthParamID;
        }

        public String getFoodtypeID() {
            return FoodtypeID;
        }

        public void setFoodtypeID(String foodtypeID) {
            FoodtypeID = foodtypeID;
        }

        public String getAvgsleep() {
            return Avgsleep;
        }

        public void setAvgsleep(String avgsleep) {
            Avgsleep = avgsleep;
        }

        public String getFutureDailyScore() {
            return FutureDailyScore;
        }

        public void setFutureDailyScore(String futureDailyScore) {
            FutureDailyScore = futureDailyScore;
        }

        public String getBody_shape() {
            return body_shape;
        }

        public void setBody_shape(String body_shape) {
            this.body_shape = body_shape;
        }

        public String getBody_Type() {
            return body_Type;
        }

        public void setBody_Type(String body_Type) {
            this.body_Type = body_Type;
        }

        public String getHealth_And_Wellness() {
            return Health_And_Wellness;
        }

        public void setHealth_And_Wellness(String health_And_Wellness) {
            Health_And_Wellness = health_And_Wellness;
        }

        public String getWaterintake() {
            return Waterintake;
        }

        public void setWaterintake(String waterintake) {
            Waterintake = waterintake;
        }

        public String getDailyScore() {
            return DailyScore;
        }

        public void setDailyScore(String dailyScore) {
            DailyScore = dailyScore;
        }
    }




    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }



}
