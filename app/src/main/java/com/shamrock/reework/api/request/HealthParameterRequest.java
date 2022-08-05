package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class HealthParameterRequest
{
    @SerializedName("UserID")
    int UserID;
    @SerializedName("Health_And_Wellness")
    int Health_And_Wellness;
    @SerializedName("Weight_Control")
    int Weight_Control;
    @SerializedName("weight_Gain")
    int weight_Gain;
    @SerializedName("Easy_Monitoring_of_Body_Parameters")
    int Easy_Monitoring_of_Body_Parameters;
    @SerializedName("body_shape")
    int body_shape;
    @SerializedName("Height")
    int Height;
    @SerializedName("Weight")
    double Weight;
    @SerializedName("FoodtypeID")
    int FoodtypeID;
    @SerializedName("FoodcultureID")
    int FoodcultureID;
    @SerializedName("ActivityStatus")
    int ActivityStatus;
    @SerializedName("Avgsleep")
    int Avgsleep;
    @SerializedName("Waterintake")
    int Waterintake;
    @SerializedName("Drink")
    int Drink;
    @SerializedName("Smoke")
    int Smoke;
    @SerializedName("MedicalConditionID")
    String MedicalConditionID;
    @SerializedName("body_Type")
    int bodyType;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getHealth_And_Wellness() {
        return Health_And_Wellness;
    }

    public void setHealth_And_Wellness(int health_And_Wellness) {
        Health_And_Wellness = health_And_Wellness;
    }

    public int getWeight_Control() {
        return Weight_Control;
    }

    public void setWeight_Control(int weight_Control) {
        Weight_Control = weight_Control;
    }

    public int getWeight_Gain() {
        return weight_Gain;
    }

    public void setWeight_Gain(int weight_Gain) {
        this.weight_Gain = weight_Gain;
    }

    public int getEasy_Monitoring_of_Body_Parameters() {
        return Easy_Monitoring_of_Body_Parameters;
    }

    public void setEasy_Monitoring_of_Body_Parameters(int easy_Monitoring_of_Body_Parameters) {
        Easy_Monitoring_of_Body_Parameters = easy_Monitoring_of_Body_Parameters;
    }

    public int getBody_shape() {
        return body_shape;
    }

    public void setBody_shape(int body_shape) {
        this.body_shape = body_shape;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public int getFoodtypeID() {
        return FoodtypeID;
    }

    public void setFoodtypeID(int foodtypeID) {
        FoodtypeID = foodtypeID;
    }

    public int getFoodcultureID() {
        return FoodcultureID;
    }

    public void setFoodcultureID(int foodcultureID) {
        FoodcultureID = foodcultureID;
    }

    public int getActivityStatus() {
        return ActivityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        ActivityStatus = activityStatus;
    }

    public int getAvgsleep() {
        return Avgsleep;
    }

    public void setAvgsleep(int avgsleep) {
        Avgsleep = avgsleep;
    }

    public int getWaterintake() {
        return Waterintake;
    }

    public void setWaterintake(int waterintake) {
        Waterintake = waterintake;
    }

    public int getDrink() {
        return Drink;
    }

    public void setDrink(int drink) {
        Drink = drink;
    }

    public int getSmoke() {
        return Smoke;
    }

    public void setSmoke(int smoke) {
        Smoke = smoke;
    }

    public String getMedicalConditionID() {
        return MedicalConditionID;
    }

    public void setMedicalConditionID(String medicalConditionID) {
        MedicalConditionID = medicalConditionID;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public int getBodyType() {
        return bodyType;
    }
}
