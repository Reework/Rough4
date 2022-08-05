package com.shamrock.reework.activity.foodguide;

import java.util.ArrayList;

public class FoodGuidePojo {
    private String Measurement;

    private String ColorCodeId;

    private MicroNutritions MicroNutritions;

    private String IngredientName;

    private ArrayList<MacroNutritions> MacroNutritions;

    private String Quantity;

    private String CategoryName;

    private String Id;

    private String ColorCode;

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public String getColorCodeId() {
        return ColorCodeId;
    }

    public void setColorCodeId(String colorCodeId) {
        ColorCodeId = colorCodeId;
    }

    public com.shamrock.reework.activity.foodguide.MicroNutritions getMicroNutritions() {
        return MicroNutritions;
    }

    public void setMicroNutritions(com.shamrock.reework.activity.foodguide.MicroNutritions microNutritions) {
        MicroNutritions = microNutritions;
    }

    public String getIngredientName() {
        return IngredientName;
    }

    public void setIngredientName(String ingredientName) {
        IngredientName = ingredientName;
    }

    public ArrayList<com.shamrock.reework.activity.foodguide.MacroNutritions> getMacroNutritions() {
        return MacroNutritions;
    }

    public void setMacroNutritions(ArrayList<com.shamrock.reework.activity.foodguide.MacroNutritions> macroNutritions) {
        MacroNutritions = macroNutritions;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getColorCode() {
        return ColorCode;
    }

    public void setColorCode(String colorCode) {
        ColorCode = colorCode;
    }
}
