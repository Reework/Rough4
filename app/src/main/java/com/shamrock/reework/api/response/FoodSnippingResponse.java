package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodSnippingResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<SnippingData> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SnippingData> getData() {
        return data;
    }

    public void setData(ArrayList<SnippingData> data) {
        this.data = data;
    }

    public class SnippingData
    {
        @SerializedName("RecipeId")
        @Expose
        private Integer recipeId;
        @SerializedName("RecipeName")
        @Expose
        private String recipeName;
        @SerializedName("RecipeDescription")
        @Expose
        private Object recipeDescription;
        @SerializedName("RecipeImage")
        @Expose
        private Object recipeImage;
        @SerializedName("PrepTime")
        @Expose
        private Object prepTime;
        @SerializedName("CookTime")
        @Expose
        private Object cookTime;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("Serving_Quantity")
        @Expose
        private Object servingQuantity;
        @SerializedName("Dietary_preference")
        @Expose
        private Object dietaryPreference;
        @SerializedName("Cuisine")
        @Expose
        private Object cuisine;
        @SerializedName("MealTypeId")
        @Expose
        private Object mealTypeId;
        @SerializedName("Meal_Type")
        @Expose
        private Object mealType;
        @SerializedName("Staple_Diet")
        @Expose
        private Object stapleDiet;
        @SerializedName("Classification")
        @Expose
        private Object classification;
        @SerializedName("Unit")
        @Expose
        private Object unit;
        @SerializedName("RecipeIngrdients")
        @Expose
        private Object recipeIngrdients;
        @SerializedName("EditId")
        @Expose
        private Integer editId;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("ModifiedRecipe")
        @Expose
        private Object modifiedRecipe;
        @SerializedName("Isfavourite")
        @Expose
        private Boolean isfavourite;
        @SerializedName("UserPrepTime")
        @Expose
        private Object userPrepTime;
        @SerializedName("UserCookTime")
        @Expose
        private Object userCookTime;
        @SerializedName("ModifiedOn")
        @Expose
        private Object modifiedOn;
        @SerializedName("calories")
        @Expose
        private Integer calories;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("Total_Calories")
        @Expose
        private Object totalCalories;
        @SerializedName("Burned_Calories")
        @Expose
        private Object burnedCalories;
        @SerializedName("UserMealId")
        @Expose
        private Integer userMealId;

        public Integer getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(Integer recipeId) {
            this.recipeId = recipeId;
        }

        public String getRecipeName() {
            return recipeName;
        }

        public void setRecipeName(String recipeName) {
            this.recipeName = recipeName;
        }

        public Object getRecipeDescription() {
            return recipeDescription;
        }

        public void setRecipeDescription(Object recipeDescription) {
            this.recipeDescription = recipeDescription;
        }

        public Object getRecipeImage() {
            return recipeImage;
        }

        public void setRecipeImage(Object recipeImage) {
            this.recipeImage = recipeImage;
        }

        public Object getPrepTime() {
            return prepTime;
        }

        public void setPrepTime(Object prepTime) {
            this.prepTime = prepTime;
        }

        public Object getCookTime() {
            return cookTime;
        }

        public void setCookTime(Object cookTime) {
            this.cookTime = cookTime;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Object getServingQuantity() {
            return servingQuantity;
        }

        public void setServingQuantity(Object servingQuantity) {
            this.servingQuantity = servingQuantity;
        }

        public Object getDietaryPreference() {
            return dietaryPreference;
        }

        public void setDietaryPreference(Object dietaryPreference) {
            this.dietaryPreference = dietaryPreference;
        }

        public Object getCuisine() {
            return cuisine;
        }

        public void setCuisine(Object cuisine) {
            this.cuisine = cuisine;
        }

        public Object getMealTypeId() {
            return mealTypeId;
        }

        public void setMealTypeId(Object mealTypeId) {
            this.mealTypeId = mealTypeId;
        }

        public Object getMealType() {
            return mealType;
        }

        public void setMealType(Object mealType) {
            this.mealType = mealType;
        }

        public Object getStapleDiet() {
            return stapleDiet;
        }

        public void setStapleDiet(Object stapleDiet) {
            this.stapleDiet = stapleDiet;
        }

        public Object getClassification() {
            return classification;
        }

        public void setClassification(Object classification) {
            this.classification = classification;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public Object getRecipeIngrdients() {
            return recipeIngrdients;
        }

        public void setRecipeIngrdients(Object recipeIngrdients) {
            this.recipeIngrdients = recipeIngrdients;
        }

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

        public Object getModifiedRecipe() {
            return modifiedRecipe;
        }

        public void setModifiedRecipe(Object modifiedRecipe) {
            this.modifiedRecipe = modifiedRecipe;
        }

        public Boolean getIsfavourite() {
            return isfavourite;
        }

        public void setIsfavourite(Boolean isfavourite) {
            this.isfavourite = isfavourite;
        }

        public Object getUserPrepTime() {
            return userPrepTime;
        }

        public void setUserPrepTime(Object userPrepTime) {
            this.userPrepTime = userPrepTime;
        }

        public Object getUserCookTime() {
            return userCookTime;
        }

        public void setUserCookTime(Object userCookTime) {
            this.userCookTime = userCookTime;
        }

        public Object getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(Object modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public Integer getCalories() {
            return calories;
        }

        public void setCalories(Integer calories) {
            this.calories = calories;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Object getTotalCalories() {
            return totalCalories;
        }

        public void setTotalCalories(Object totalCalories) {
            this.totalCalories = totalCalories;
        }

        public Object getBurnedCalories() {
            return burnedCalories;
        }

        public void setBurnedCalories(Object burnedCalories) {
            this.burnedCalories = burnedCalories;
        }

        public Integer getUserMealId() {
            return userMealId;
        }

        public void setUserMealId(Integer userMealId) {
            this.userMealId = userMealId;
        }
    }
}
