package com.shamrock.reework.activity.MyPlansModule.score;

import java.util.ArrayList;

public class Score {
    public int PlanTypeId ;
    public String PlanName ;
    public ArrayList<Items> Items ;
    public int TotalItem ;
    public String TotalSelected ;
    public String TotalScore ;

    public int getPlanTypeId() {
        return PlanTypeId;
    }

    public void setPlanTypeId(int planTypeId) {
        PlanTypeId = planTypeId;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public ArrayList<Items> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Items> items) {
        Items = items;
    }

    public int getTotalItem() {
        return TotalItem;
    }

    public void setTotalItem(int totalItem) {
        TotalItem = totalItem;
    }

    public String getTotalSelected() {
        return TotalSelected;
    }

    public void setTotalSelected(String totalSelected) {
        TotalSelected = totalSelected;
    }

    public String getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(String totalScore) {
        TotalScore = totalScore;
    }
}
