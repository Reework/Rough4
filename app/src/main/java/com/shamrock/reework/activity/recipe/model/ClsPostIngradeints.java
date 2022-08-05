package com.shamrock.reework.activity.recipe.model;

public class ClsPostIngradeints {

//    IngredientId": 0,
//            "UoMId": 0,
//            "ApproximateValue": 0,
//            "Quantity": 0


    private int IngredientId;
    private int UoMId;
    private double ApproximateValue;
    private double Quantity;

    public ClsPostIngradeints(int ingredientId, int uoMId, double approximateValue, double quantity) {
        IngredientId = ingredientId;
        UoMId = uoMId;
        ApproximateValue = approximateValue;
        Quantity = quantity;
    }

    public int getIngredientId() {
        return IngredientId;
    }

    public void setIngredientId(int ingredientId) {
        IngredientId = ingredientId;
    }

    public int getUoMId() {
        return UoMId;
    }

    public void setUoMId(int uoMId) {
        UoMId = uoMId;
    }

    public double getApproximateValue() {
        return ApproximateValue;
    }

    public void setApproximateValue(double approximateValue) {
        ApproximateValue = approximateValue;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }
}
