package com.shamrock.reework.activity.todaysplan.model;

import java.util.ArrayList;

public class Groups {
    private String GroupName;

    private ArrayList<PlanItems> PlanItems;
    private ArrayList<TodayPlanItems> TodayPlanItems;

    private ArrayList<AlternateItems> AlternateItems;
    private ArrayList<TodayAlternateItems> TodayAlternateItems;

    public ArrayList<com.shamrock.reework.activity.todaysplan.model.TodayPlanItems> getTodayPlanItems() {
        return TodayPlanItems;
    }

    public void setTodayPlanItems(ArrayList<com.shamrock.reework.activity.todaysplan.model.TodayPlanItems> todayPlanItems) {
        TodayPlanItems = todayPlanItems;
    }

    public ArrayList<com.shamrock.reework.activity.todaysplan.model.TodayAlternateItems> getTodayAlternateItems() {
        return TodayAlternateItems;
    }

    public void setTodayAlternateItems(ArrayList<com.shamrock.reework.activity.todaysplan.model.TodayAlternateItems> todayAlternateItems) {
        TodayAlternateItems = todayAlternateItems;
    }

    private int Id;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public ArrayList<com.shamrock.reework.activity.todaysplan.model.AlternateItems> getAlternateItems() {
        return AlternateItems;
    }

    public void setAlternateItems(ArrayList<com.shamrock.reework.activity.todaysplan.model.AlternateItems> alternateItems) {
        AlternateItems = alternateItems;
    }

    public ArrayList<com.shamrock.reework.activity.todaysplan.model.PlanItems> getPlanItems() {
        return PlanItems;
    }

    public void setPlanItems(ArrayList<com.shamrock.reework.activity.todaysplan.model.PlanItems> planItems) {
        PlanItems = planItems;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
