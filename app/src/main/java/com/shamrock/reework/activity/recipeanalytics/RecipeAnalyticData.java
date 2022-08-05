package com.shamrock.reework.activity.recipeanalytics;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeAnalyticData {
    private String Category;

    private String SearchText;

    @SerializedName("Result")
    private ArrayList<RecipeAnalyticResult> Result;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSearchText() {
        return SearchText;
    }

    public void setSearchText(String searchText) {
        SearchText = searchText;
    }

    public ArrayList<RecipeAnalyticResult> getResult() {
        return Result;
    }

    public void setResult(ArrayList<RecipeAnalyticResult> result) {
        Result = result;
    }
}
