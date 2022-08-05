package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodTripFavoriteUpdateRequest
{
    @SerializedName("Isfavourite")
    @Expose
    private Integer isfavourite;
    @SerializedName("RecipeId")
    @Expose
    private Integer recipeId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("EditId")
    @Expose
    private Integer editId;

    public Integer getIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(Integer isfavourite) {
        this.isfavourite = isfavourite;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEditId() {
        return editId;
    }

    public void setEditId(Integer editId) {
        this.editId = editId;
    }
}
