package com.shamrock.reework.activity.recipe.model;

import java.util.ArrayList;

public class RecipeMasterData {

    private ArrayList<CusineList> CusineList;

    private ArrayList<ItemUomMaster> ItemUomMaster;

    private ArrayList<ServingUnitMaster> ServingUnitMaster;

    private ArrayList<MealTypeList> MealTypeList;

    public ArrayList<com.shamrock.reework.activity.recipe.model.CusineList> getCusineList() {
        return CusineList;
    }

    public void setCusineList(ArrayList<com.shamrock.reework.activity.recipe.model.CusineList> cusineList) {
        CusineList = cusineList;
    }

    public ArrayList<com.shamrock.reework.activity.recipe.model.ItemUomMaster> getItemUomMaster() {
        return ItemUomMaster;
    }

    public void setItemUomMaster(ArrayList<com.shamrock.reework.activity.recipe.model.ItemUomMaster> itemUomMaster) {
        ItemUomMaster = itemUomMaster;
    }

    public ArrayList<com.shamrock.reework.activity.recipe.model.ServingUnitMaster> getServingUnitMaster() {
        return ServingUnitMaster;
    }

    public void setServingUnitMaster(ArrayList<com.shamrock.reework.activity.recipe.model.ServingUnitMaster> servingUnitMaster) {
        ServingUnitMaster = servingUnitMaster;
    }

    public ArrayList<com.shamrock.reework.activity.recipe.model.MealTypeList> getMealTypeList() {
        return MealTypeList;
    }

    public void setMealTypeList(ArrayList<com.shamrock.reework.activity.recipe.model.MealTypeList> mealTypeList) {
        MealTypeList = mealTypeList;
    }
}
