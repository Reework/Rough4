package com.shamrock.reework.activity.newrecipe;

import java.io.Serializable;
import java.util.ArrayList;

public class EditRecipeData implements Serializable {
    private String IsMadeByChef;

    private String RecipeName;

    private String Cuisine;

    private String CookTime;

    private String Description;

    private String CreatedBy;

    private ArrayList<Ingredients> Ingredients;

    private String RecipeId;

    private String ServingUnitId;

    private String TotalCalories;

    private String TotalProtein;

    private String TotalFibre;

    private String ServingUnitName;

    private String TotalCarbs;

    private String IsVeg;

    private String VideoLink;

    private String PrepTime;

    private String ImagePath;

    private String ServingQuantitiy;

    private String IsHealthy;

    private String TotalFats;
    private String ClassificationIds;

    public String getClassificationIds() {
        return ClassificationIds;
    }

    public void setClassificationIds(String classificationIds) {
        ClassificationIds = classificationIds;
    }

    public String getIsMadeByChef() {
        return IsMadeByChef;
    }

    public void setIsMadeByChef(String isMadeByChef) {
        IsMadeByChef = isMadeByChef;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(String cuisine) {
        Cuisine = cuisine;
    }

    public String getCookTime() {
        return CookTime;
    }

    public void setCookTime(String cookTime) {
        CookTime = cookTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public ArrayList<com.shamrock.reework.activity.newrecipe.Ingredients> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(ArrayList<com.shamrock.reework.activity.newrecipe.Ingredients> ingredients) {
        Ingredients = ingredients;
    }

    public String getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(String recipeId) {
        RecipeId = recipeId;
    }

    public String getServingUnitId() {
        return ServingUnitId;
    }

    public void setServingUnitId(String servingUnitId) {
        ServingUnitId = servingUnitId;
    }

    public String getTotalCalories() {
        return TotalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        TotalCalories = totalCalories;
    }

    public String getTotalProtein() {
        return TotalProtein;
    }

    public void setTotalProtein(String totalProtein) {
        TotalProtein = totalProtein;
    }

    public String getTotalFibre() {
        return TotalFibre;
    }

    public void setTotalFibre(String totalFibre) {
        TotalFibre = totalFibre;
    }

    public String getServingUnitName() {
        return ServingUnitName;
    }

    public void setServingUnitName(String servingUnitName) {
        ServingUnitName = servingUnitName;
    }

    public String getTotalCarbs() {
        return TotalCarbs;
    }

    public void setTotalCarbs(String totalCarbs) {
        TotalCarbs = totalCarbs;
    }

    public String getIsVeg() {
        return IsVeg;
    }

    public void setIsVeg(String isVeg) {
        IsVeg = isVeg;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }

    public String getPrepTime() {
        return PrepTime;
    }

    public void setPrepTime(String prepTime) {
        PrepTime = prepTime;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getServingQuantitiy() {
        return ServingQuantitiy;
    }

    public void setServingQuantitiy(String servingQuantitiy) {
        ServingQuantitiy = servingQuantitiy;
    }

    public String getIsHealthy() {
        return IsHealthy;
    }

    public void setIsHealthy(String isHealthy) {
        IsHealthy = isHealthy;
    }

    public String getTotalFats() {
        return TotalFats;
    }

    public void setTotalFats(String totalFats) {
        TotalFats = totalFats;
    }
}
