package com.shamrock.reework.activity.recipeanalytics;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.recipe.model.RecipeNutritionData;

public class ClsRecipeAnalyticDataMain {
    private String Message;

    @SerializedName("Data")
    private RecipeAnalyticData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public RecipeAnalyticData getData() {
        return Data;
    }

    public void setData(RecipeAnalyticData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
