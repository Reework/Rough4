package com.shamrock.reework.activity.FoodModule.history;

import java.util.ArrayList;

public class ClsFoodHistoryPojo {

    private String Message;

    private ArrayList<Data> Data;

    private String Code;

    private double AvgFoodCalories;


//    AvgFoodProtein,
//    AvgFoodCarbs,
//    AvgFoodFat,
//    AvgFoodFibre

    private double AvgFoodProtein;
    private double AvgFoodCarbs;
    private double AvgFoodFat;
    private double AvgFoodFibre;

    public double getAvgFoodProtein() {
        return AvgFoodProtein;
    }

    public void setAvgFoodProtein(double avgFoodProtein) {
        AvgFoodProtein = avgFoodProtein;
    }

    public double getAvgFoodCarbs() {
        return AvgFoodCarbs;
    }

    public void setAvgFoodCarbs(double avgFoodCarbs) {
        AvgFoodCarbs = avgFoodCarbs;
    }

    public double getAvgFoodFat() {
        return AvgFoodFat;
    }

    public void setAvgFoodFat(double avgFoodFat) {
        AvgFoodFat = avgFoodFat;
    }

    public double getAvgFoodFibre() {
        return AvgFoodFibre;
    }

    public void setAvgFoodFibre(double avgFoodFibre) {
        AvgFoodFibre = avgFoodFibre;
    }

    public double getAvgFoodCalories() {
        return AvgFoodCalories;
    }

    public void setAvgFoodCalories(double avgFoodCalories) {
        AvgFoodCalories = avgFoodCalories;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<com.shamrock.reework.activity.FoodModule.history.Data> getData() {
        return Data;
    }

    public void setData(ArrayList<com.shamrock.reework.activity.FoodModule.history.Data> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
