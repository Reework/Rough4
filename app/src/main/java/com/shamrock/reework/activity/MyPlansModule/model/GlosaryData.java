package com.shamrock.reework.activity.MyPlansModule.model;

import java.util.ArrayList;

public class GlosaryData {
    private String ReceiprName;

    private ArrayList<IngredientList> IngredientList;

    public String getReceiprName() {
        return ReceiprName;
    }

    public void setReceiprName(String receiprName) {
        ReceiprName = receiprName;
    }

    public ArrayList<com.shamrock.reework.activity.MyPlansModule.model.IngredientList> getIngredientList() {
        return IngredientList;
    }

    public void setIngredientList(ArrayList<com.shamrock.reework.activity.MyPlansModule.model.IngredientList> ingredientList) {
        IngredientList = ingredientList;
    }
}
