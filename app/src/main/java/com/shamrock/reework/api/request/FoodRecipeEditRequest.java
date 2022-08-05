package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodRecipeEditRequest
{


    @SerializedName("EditId")
    @Expose
    private Integer editId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("RecipeId")
    @Expose
    private String recipeId;
    @SerializedName("ModifiedRecipeName")
    @Expose
    private String modifiedRecipeName;
    @SerializedName("ModifiedRecipeMethod")
    @Expose
    private String modifiedRecipeMethod;
    @SerializedName("ModifiedIngredients")
    @Expose
    private String modifiedIngredients;
    @SerializedName("ModifiedPrepTime")
    @Expose
    private Integer modifiedPrepTime;
    @SerializedName("ModifiedCookingTime")
    @Expose
    private Integer modifiedCookingTime;
    @SerializedName("Isfavourite")
    @Expose
    private Boolean isfavourite;

    public Integer getEditId() {
        return editId;
    }

    public void setEditId(Integer editId) {
        this.editId = editId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getModifiedRecipeName() {
        return modifiedRecipeName;
    }

    public void setModifiedRecipeName(String modifiedRecipeName) {
        this.modifiedRecipeName = modifiedRecipeName;
    }

    public String getModifiedRecipeMethod() {
        return modifiedRecipeMethod;
    }

    public void setModifiedRecipeMethod(String modifiedRecipeMethod) {
        this.modifiedRecipeMethod = modifiedRecipeMethod;
    }

    public String getModifiedIngredients() {
        return modifiedIngredients;
    }

    public void setModifiedIngredients(String modifiedIngredients) {
        this.modifiedIngredients = modifiedIngredients;
    }

    public Integer getModifiedPrepTime() {
        return modifiedPrepTime;
    }

    public void setModifiedPrepTime(Integer modifiedPrepTime) {
        this.modifiedPrepTime = modifiedPrepTime;
    }

    public Integer getModifiedCookingTime() {
        return modifiedCookingTime;
    }

    public void setModifiedCookingTime(Integer modifiedCookingTime) {
        this.modifiedCookingTime = modifiedCookingTime;
    }

    public Boolean getIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(Boolean isfavourite) {
        this.isfavourite = isfavourite;
    }
}
