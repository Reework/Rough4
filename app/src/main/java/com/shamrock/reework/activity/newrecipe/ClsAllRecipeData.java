package com.shamrock.reework.activity.newrecipe;

import java.io.Serializable;
import java.util.ArrayList;

public class ClsAllRecipeData implements Serializable {
    private ArrayList<IngredientList> IngredientList;

    private ArrayList<ServingUnitList> ServingUnitList;

    private ArrayList<String> CuisineList;

    private ArrayList<IngredientUnitList> IngredientUnitList;

    public ArrayList<com.shamrock.reework.activity.newrecipe.IngredientList> getIngredientList() {
        return IngredientList;
    }

    public void setIngredientList(ArrayList<com.shamrock.reework.activity.newrecipe.IngredientList> ingredientList) {
        IngredientList = ingredientList;
    }

    public ArrayList<com.shamrock.reework.activity.newrecipe.ServingUnitList> getServingUnitList() {
        return ServingUnitList;
    }

    public void setServingUnitList(ArrayList<com.shamrock.reework.activity.newrecipe.ServingUnitList> servingUnitList) {
        ServingUnitList = servingUnitList;
    }

    public ArrayList<String> getCuisineList() {
        return CuisineList;
    }

    public void setCuisineList(ArrayList<String> cuisineList) {
        CuisineList = cuisineList;
    }

    public ArrayList<com.shamrock.reework.activity.newrecipe.IngredientUnitList> getIngredientUnitList() {
        return IngredientUnitList;
    }

    public void setIngredientUnitList(ArrayList<com.shamrock.reework.activity.newrecipe.IngredientUnitList> ingredientUnitList) {
        IngredientUnitList = ingredientUnitList;
    }
}
