package com.shamrock.reework.activity.AnalysisModule.activity.food;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.AnalysisModule.activity.sleepNap.Naps;

public class FoodNapData {

    @SerializedName("MealIntakes")
    private MealIntakes MealIntakes;

    private String CreatedOn;

    public MealIntakes getMealIntakes() {
        return MealIntakes;
    }

    public void setMealIntakes(MealIntakes mealIntakes) {
        MealIntakes = mealIntakes;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
