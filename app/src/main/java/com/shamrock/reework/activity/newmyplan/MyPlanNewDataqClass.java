package com.shamrock.reework.activity.newmyplan;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.MyPlansModule.model.DefaultAlternatePlan;
import com.shamrock.reework.activity.MyPlansModule.model.DefaultPlanItems;

import java.util.ArrayList;

public class MyPlanNewDataqClass {
    @SerializedName("result")
    private ClsTodaysPlanNewResult result;

    private ArrayList<DefaultPlanItems> DefaultPlanItems;

    private ArrayList<TodayAlternatePlan> TodayAlternatePlan;


    private ArrayList<TodayPlantItems> TodayPlantItems;

    private ArrayList<DefaultAlternatePlan>  DefaultAlternatePlan;

    public ClsTodaysPlanNewResult getResult() {
        return result;
    }

    public void setResult(ClsTodaysPlanNewResult result) {
        this.result = result;
    }

    public ArrayList<com.shamrock.reework.activity.MyPlansModule.model.DefaultPlanItems> getDefaultPlanItems() {
        return DefaultPlanItems;
    }

    public void setDefaultPlanItems(ArrayList<com.shamrock.reework.activity.MyPlansModule.model.DefaultPlanItems> defaultPlanItems) {
        DefaultPlanItems = defaultPlanItems;
    }

    public ArrayList<com.shamrock.reework.activity.newmyplan.TodayAlternatePlan> getTodayAlternatePlan() {
        return TodayAlternatePlan;
    }

    public void setTodayAlternatePlan(ArrayList<com.shamrock.reework.activity.newmyplan.TodayAlternatePlan> todayAlternatePlan) {
        TodayAlternatePlan = todayAlternatePlan;
    }

    public ArrayList<com.shamrock.reework.activity.newmyplan.TodayPlantItems> getTodayPlantItems() {
        return TodayPlantItems;
    }

    public void setTodayPlantItems(ArrayList<com.shamrock.reework.activity.newmyplan.TodayPlantItems> todayPlantItems) {
        TodayPlantItems = todayPlantItems;
    }

    public ArrayList<com.shamrock.reework.activity.MyPlansModule.model.DefaultAlternatePlan> getDefaultAlternatePlan() {
        return DefaultAlternatePlan;
    }

    public void setDefaultAlternatePlan(ArrayList<com.shamrock.reework.activity.MyPlansModule.model.DefaultAlternatePlan> defaultAlternatePlan) {
        DefaultAlternatePlan = defaultAlternatePlan;
    }
}
