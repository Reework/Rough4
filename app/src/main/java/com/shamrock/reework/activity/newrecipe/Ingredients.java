package com.shamrock.reework.activity.newrecipe;

public class Ingredients {
    private String IngredientName;

    private String IngredientId;

    private String Quantity;

    private String Calories;

    private String Protein;

    private String UomId;

    private String Remark;

    private String MappingId;

    private String Carbs;

    private String Fibre;

    private String ApproxValue;

    private String Fats;

    private String UomName;


    public Ingredients(String ingredientName, String ingredientId, String quantity, String uomId, String remark, String mappingId) {
        IngredientName = ingredientName;
        IngredientId = ingredientId;
        Quantity = quantity;
        UomId = uomId;
        Remark = remark;
        MappingId = mappingId;
    }

    public String getIngredientName() {
        return IngredientName;
    }

    public void setIngredientName(String ingredientName) {
        IngredientName = ingredientName;
    }

    public String getIngredientId() {
        return IngredientId;
    }

    public void setIngredientId(String ingredientId) {
        IngredientId = ingredientId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }

    public String getUomId() {
        return UomId;
    }

    public void setUomId(String uomId) {
        UomId = uomId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getMappingId() {
        return MappingId;
    }

    public void setMappingId(String mappingId) {
        MappingId = mappingId;
    }

    public String getCarbs() {
        return Carbs;
    }

    public void setCarbs(String carbs) {
        Carbs = carbs;
    }

    public String getFibre() {
        return Fibre;
    }

    public void setFibre(String fibre) {
        Fibre = fibre;
    }

    public String getApproxValue() {
        return ApproxValue;
    }

    public void setApproxValue(String approxValue) {
        ApproxValue = approxValue;
    }

    public String getFats() {
        return Fats;
    }

    public void setFats(String fats) {
        Fats = fats;
    }

    public String getUomName() {
        return UomName;
    }

    public void setUomName(String uomName) {
        UomName = uomName;
    }
}
