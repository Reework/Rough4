package com.shamrock.reework.activity.FoodModule.model;

public class FoodUnitMasterData {
    private String Measurement;

    private String IsActive;

    private String[] t_AddUserMeal;

    private double ValueInGram;

    private String Id;

    private String ImagePath;

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String[] getT_AddUserMeal() {
        return t_AddUserMeal;
    }

    public void setT_AddUserMeal(String[] t_AddUserMeal) {
        this.t_AddUserMeal = t_AddUserMeal;
    }

    public double getValueInGram() {
        return ValueInGram;
    }

    public void setValueInGram(double valueInGram) {
        ValueInGram = valueInGram;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
