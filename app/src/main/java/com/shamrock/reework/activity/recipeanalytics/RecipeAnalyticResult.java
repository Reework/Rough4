package com.shamrock.reework.activity.recipeanalytics;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeAnalyticResult implements Serializable {
    private String Category;

    private String Description;

    private String Image;

    private String Protein;

    private String Calories;

    private String IsItem;

    private String IsVeg;

    @SerializedName("Classification")
    private ArrayList<String> Classification;

    private String Carbs;

    public ArrayList<String> getClassification() {
        return Classification;
    }

    public void setClassification(ArrayList<String> classification) {
        Classification = classification;
    }

    private String Fat;

    private String ItemName;

    private String Fibre;

    private String Id;

    private String IsHealthy;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public String getIsItem() {
        return IsItem;
    }

    public void setIsItem(String isItem) {
        IsItem = isItem;
    }

    public String getIsVeg() {
        return IsVeg;
    }

    public void setIsVeg(String isVeg) {
        IsVeg = isVeg;
    }

//    public ArrayList<com.shamrock.reework.activity.recipeanalytics.Classification> getClassification() {
//        return Classification;
//    }
//
//    public void setClassification(ArrayList<com.shamrock.reework.activity.recipeanalytics.Classification> classification) {
//        Classification = classification;
//    }

    public String getCarbs() {
        return Carbs;
    }

    public void setCarbs(String carbs) {
        Carbs = carbs;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getFibre() {
        return Fibre;
    }

    public void setFibre(String fibre) {
        Fibre = fibre;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIsHealthy() {
        return IsHealthy;
    }

    public void setIsHealthy(String isHealthy) {
        IsHealthy = isHealthy;
    }
}
