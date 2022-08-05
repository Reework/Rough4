package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeResponse implements Serializable {
    @SerializedName("Code")
    @Expose    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<Recipee> data = null;

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

    public ArrayList<Recipee> getData() {
        return data;
    }

    public void setData(ArrayList<Recipee> data) {
        this.data = data;
    }

    public class Recipee implements Serializable {
        @SerializedName("RecipeImagePath")
        @Expose
        private String RecipeImagePath;
        @SerializedName("RecipeId")
        @Expose
        private Integer recipeId;


        @SerializedName("RecipeName")
        @Expose
        private String recipeName;
        @SerializedName("RecipeDescription")
        @Expose
        private String recipeDescription;
        @SerializedName("RecipeImage")
        @Expose
        private String recipeImage;
        @SerializedName("PrepTime")
        @Expose
        private Integer prepTime;
        @SerializedName("CookTime")
        @Expose
        private Integer cookTime;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("Serving_Quantity")
        @Expose
        private String servingQuantity;
        @SerializedName("Dietary_preference")
        @Expose
        private String dietaryPreference;
        @SerializedName("Cuisine")
        @Expose
        private String cuisine;
        @SerializedName("Meal_Type")
        @Expose
        private String mealType;
        @SerializedName("Staple_Diet")
        @Expose
        private String stapleDiet;
        @SerializedName("Classification")
        @Expose
        private String classification;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("RecipeIngrdients")
        @Expose
        private List<RecipeIngrdient> recipeIngrdients = null;
        @SerializedName("EditId")
        @Expose
        private Integer editId;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
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
        @SerializedName("ModifiedOn")
        @Expose
        private String modifiedOn;
        @SerializedName("calories")
        @Expose
        private double calories;
        @SerializedName("quantity")
        @Expose
        private double quantity;
        @SerializedName("Total_Calories")
        @Expose
        private String totalCalories;
        @SerializedName("Burned_Calories")
        @Expose
        private String burnedCalories;
        @SerializedName("UserMealId")
        @Expose
        private Integer userMealId;
        @SerializedName("p_value")
        @Expose
        private Integer pValue;
        @SerializedName("p_text")
        @Expose
        private String pText;
        @SerializedName("days")
        @Expose
        private Integer days;
        @SerializedName("Reecoach_id")
        @Expose
        private Integer reecoachId;

        @SerializedName("VideoLink")
        @Expose
        private String VideoLink;

        @SerializedName("Protein")
        @Expose
        private String Protein;
        @SerializedName("Carbs")
        @Expose
        private String Carbs;

        @SerializedName("Fat")
        @Expose
        private String Fat;

        @SerializedName("Fibre")
        @Expose
        private String Fibre;


        public String getProtein() {
            return Protein;
        }
        public void setProtein(String protein) {
            Protein = protein;
        }

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

        public String getFibre() {
            return Fibre;
        }

        public void setFibre(String fibre) {
            Fibre = fibre;
        }
        //        Carbs":60.5,"Protein":31.5,"Fat":12.0,"Fibre

        public Integer getpValue() {
            return pValue;
        }
        public String getRecipeImagePath() {
            return RecipeImagePath;
        }

        public void setRecipeImagePath(String recipeImagePath) {
            RecipeImagePath = recipeImagePath;
        }
        public void setpValue(Integer pValue) {
            this.pValue = pValue;
        }

        public String getpText() {
            return pText;
        }

        public void setpText(String pText) {
            this.pText = pText;
        }

        public String getVideoLink() {
            return VideoLink;
        }

        public void setVideoLink(String videoLink) {
            VideoLink = videoLink;
        }
        //        @SerializedName("Total_Calories")
//        @Expose
//        private double Total_Calories;

//        public double getTotal_Calories() {
//            return Total_Calories;
//        }

//        public void setTotal_Calories(double total_Calories) {
//            Total_Calories = total_Calories;
//        }

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

        public String getRecipeDescription() {
            return recipeDescription;
        }

        public void setRecipeDescription(String recipeDescription) {
            this.recipeDescription = recipeDescription;
        }

        public String getRecipeImage() {
            return recipeImage;
        }

        public void setRecipeImage(String recipeImage) {
            this.recipeImage = recipeImage;
        }

        public Integer getPrepTime() {
            return prepTime;
        }

        public void setPrepTime(Integer prepTime) {
            this.prepTime = prepTime;
        }

        public Integer getCookTime() {
            return cookTime;
        }

        public void setCookTime(Integer cookTime) {
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

        public void setServingQuantity(String servingQuantity) {
            this.servingQuantity = servingQuantity;
        }

        public String getDietaryPreference() {
            return dietaryPreference;
        }

        public void setDietaryPreference(String dietaryPreference) {
            this.dietaryPreference = dietaryPreference;
        }

        public String getCuisine() {
            return cuisine;
        }

        public void setCuisine(String cuisine) {
            this.cuisine = cuisine;
        }

        public String getMealType() {
            return mealType;
        }

        public void setMealType(String mealType) {
            this.mealType = mealType;
        }

        public String getStapleDiet() {
            return stapleDiet;
        }

        public void setStapleDiet(String stapleDiet) {
            this.stapleDiet = stapleDiet;
        }

        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public List<RecipeIngrdient> getRecipeIngrdients() {
            return recipeIngrdients;
        }

        public void setRecipeIngrdients(List<RecipeIngrdient> recipeIngrdients) {
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

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

//        public Integer getCalories() {
//            return calories;
//        }

//        public void setCalories(Integer calories) {
//            this.calories = calories;
//        }


        public double getCalories() {
            return calories;
        }

        public void setCalories(double calories) {
            this.calories = calories;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public Object getTotalCalories() {
            return totalCalories;
        }

        public void setTotalCalories(String totalCalories) {
            this.totalCalories = totalCalories;
        }

        public Object getBurnedCalories() {
            return burnedCalories;
        }

        public void setBurnedCalories(String burnedCalories) {
            this.burnedCalories = burnedCalories;
        }

        public Integer getUserMealId() {
            return userMealId;
        }

        public void setUserMealId(Integer userMealId) {
            this.userMealId = userMealId;
        }

        public Integer getPValue() {
            return pValue;
        }

        public void setPValue(Integer pValue) {
            this.pValue = pValue;
        }

        public Object getPText() {
            return pText;
        }

        public void setPText(String pText) {
            this.pText = pText;
        }

        public Integer getDays() {
            return days;
        }

        public void setDays(Integer days) {
            this.days = days;
        }

        public Integer getReecoachId() {
            return reecoachId;
        }

        public void setReecoachId(Integer reecoachId) {
            this.reecoachId = reecoachId;
        }
    }

    public class RecipeIngrdient implements Serializable {
        @SerializedName("IngredientId")
        @Expose
        private Integer ingredientId;
        @SerializedName("Ingredients")
        @Expose
        private String ingredients;
        @SerializedName("RecipeId")
        @Expose
        private Integer recipeId;
        @SerializedName("Serving_Quantity")
        @Expose
        private String servingQuantity;
        @SerializedName("Dietary_preference")
        @Expose
        private String dietaryPreference;
        @SerializedName("Cuisine")
        @Expose
        private String cuisine;
        @SerializedName("Meal_Type")
        @Expose
        private String mealType;
        @SerializedName("Staple_Diet")
        @Expose
        private String stapleDiet;
        @SerializedName("Classification")
        @Expose
        private String classification;
        @SerializedName("Quantity")
        @Expose
        private double quantity;
        @SerializedName("Unit")
        @Expose
        private String unit;


        private String Remark;

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public Integer getIngredientId() {
            return ingredientId;
        }

        public void setIngredientId(Integer ingredientId) {
            this.ingredientId = ingredientId;
        }

        public String getIngredients() {
            return ingredients;
        }

        public void setIngredients(String ingredients) {
            this.ingredients = ingredients;
        }

        public Integer getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(Integer recipeId) {
            this.recipeId = recipeId;
        }

        public String getServingQuantity() {
            return servingQuantity;
        }

        public void setServingQuantity(String servingQuantity) {
            this.servingQuantity = servingQuantity;
        }

        public String getDietaryPreference() {
            return dietaryPreference;
        }

        public void setDietaryPreference(String dietaryPreference) {
            this.dietaryPreference = dietaryPreference;
        }

        public String getCuisine() {
            return cuisine;
        }

        public void setCuisine(String cuisine) {
            this.cuisine = cuisine;
        }

        public String getMealType() {
            return mealType;
        }

        public void setMealType(String mealType) {
            this.mealType = mealType;
        }

        public String getStapleDiet() {
            return stapleDiet;
        }

        public void setStapleDiet(String stapleDiet) {
            this.stapleDiet = stapleDiet;
        }

        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

    }
}
