package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class FoodListByMealType  implements Serializable {
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public static class Datum implements Serializable {
//        Keys: Carbs,Fat,Protein,Fibre
        @SerializedName("Fat")
        @Expose
        private String fat;
        @SerializedName("Carbs")
        @Expose
        private String carb;

        @SerializedName("Fibre")
        @Expose
        private String fibre;

        @SerializedName("Protein")
        @Expose
        private String protin;



        @SerializedName("RecipeImagePath")
        @Expose
        private String RecipeImagePath;


        @SerializedName("UomId")
        @Expose
        private int UomId;



        @SerializedName("ValueInGram")
        @Expose
        private double ValueInGram;

        @SerializedName("IntakeTime")
        @Expose
        private String IntakeTime;

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
        private Object servingQuantity;
        @SerializedName("Dietary_preference")
        @Expose
        private String dietaryPreference;
        @SerializedName("Cuisine")
        @Expose
        private Object cuisine;
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
        @SerializedName("ModifiedIngredients")
        @Expose
        private Object modifiedIngredients;
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
        private Object modifiedOn;
        @SerializedName("calories")
        @Expose
        private String calories;
        @SerializedName("quantity")
        @Expose
        private double quantity;
        @SerializedName("Total_Calories")
        @Expose
        private Object totalCalories;
        @SerializedName("Burned_Calories")
        @Expose
        private Object burnedCalories;
        @SerializedName("UserMealId")
        @Expose
        private Integer userMealId;
        @SerializedName("p_value")
        @Expose
        private Integer pValue;
        @SerializedName("p_text")
        @Expose
        private Object pText;

        private boolean isSelect;


        public String getFat() {
            return fat;
        }

        public void setFat(String fat) {
            this.fat = fat;
        }

        public String getCarb() {
            return carb;
        }

        public void setCarb(String carb) {
            this.carb = carb;
        }

        public String getFibre() {
            return fibre;
        }

        public void setFibre(String fibre) {
            this.fibre = fibre;
        }

        public String getProtin() {
            return protin;
        }

        public void setProtin(String protin) {
            this.protin = protin;
        }

        public static Comparator<Datum> COMPARE_BY_PHONE = new Comparator<Datum>() {
            public int compare(Datum one, Datum other) {
                return one.mealType.toString().compareTo(other.mealType.toString());
            }
        };

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public double getValueInGram() {
            return ValueInGram;
        }

        public void setValueInGram(double valueInGram) {
            ValueInGram = valueInGram;
        }

        public int getUomId() {
            return UomId;
        }

        public void setUomId(int uomId) {
            UomId = uomId;
        }

        private String singleCal;

        public String getSingleCal() {
            return singleCal;
        }

        public void setSingleCal(String singleCal) {
            this.singleCal = singleCal;
        }

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

        public void setServingQuantity(Object servingQuantity) {
            this.servingQuantity = servingQuantity;
        }

        public String getIntakeTime() {
            return IntakeTime;
        }

        public void setIntakeTime(String intakeTime) {
            IntakeTime = intakeTime;
        }

        public String getDietaryPreference() {
            return dietaryPreference;
        }

        public void setDietaryPreference(String dietaryPreference) {
            this.dietaryPreference = dietaryPreference;
        }

        public Object getCuisine() {
            return cuisine;
        }

        public void setCuisine(Object cuisine) {
            this.cuisine = cuisine;
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

        public Object getModifiedIngredients() {
            return modifiedIngredients;
        }

        public void setModifiedIngredients(Object modifiedIngredients) {
            this.modifiedIngredients = modifiedIngredients;
        }

        public Integer getModifiedPrepTime() {
            return modifiedPrepTime;
        }

        public void setModifiedPrepTime(Integer modifiedPrepTime) {
            this.modifiedPrepTime = modifiedPrepTime;
        }

        public String getRecipeImagePath() {
            return RecipeImagePath;
        }

        public void setRecipeImagePath(String recipeImagePath) {
            RecipeImagePath = recipeImagePath;
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

        public Object getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(Object modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public String getCalories() {
            return calories;
        }

        public void setCalories(String calories) {
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

        public Integer getPValue() {
            return pValue;
        }

        public void setPValue(Integer pValue) {
            this.pValue = pValue;
        }

        public Object getPText() {
            return pText;
        }

        public void setPText(Object pText) {
            this.pText = pText;
        }



    }

}
