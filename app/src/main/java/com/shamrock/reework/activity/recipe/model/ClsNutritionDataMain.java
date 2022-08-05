package com.shamrock.reework.activity.recipe.model;

import com.google.gson.annotations.SerializedName;

public class ClsNutritionDataMain {
    private String Message;

    @SerializedName("Data")
    private RecipeNutritionData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public RecipeNutritionData getData() {
        return Data;
    }

    public void setData(RecipeNutritionData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
