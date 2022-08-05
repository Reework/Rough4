package com.shamrock.reework.activity.todaysplan.model;

import java.util.ArrayList;

public class ReeworkerPlan {

    private String MealType;

    private boolean Notify;

    private String MealTypeId;

    private String PlanType;


    public ArrayList<Groups> getGroups() {
        return Groups;
    }

    public void setGroups(ArrayList<Groups> groups) {
        Groups = groups;
    }

    //    private ArrayList<PlanItems> PlanItems;
    private ArrayList<Groups> Groups;

    private String Id;

    private String PlanTime;

    private String DailyPlanMasterId;

    private String Remark;
    private boolean IsAdded;
    private String msgcount;

    public String getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(String msgcount) {
        this.msgcount = msgcount;
    }

    public boolean isAdded() {
        return IsAdded;
    }

    public void setAdded(boolean added) {
        IsAdded = added;
    }

    public String getMealType() {
        return MealType;
    }

    public void setMealType(String mealType) {
        MealType = mealType;
    }

    public boolean isNotify() {
        return Notify;
    }

    public void setNotify(boolean notify) {
        Notify = notify;
    }

    public String getMealTypeId() {
        return MealTypeId;
    }

    public void setMealTypeId(String mealTypeId) {
        MealTypeId = mealTypeId;
    }

    public String getPlanType() {
        return PlanType;
    }

    public void setPlanType(String planType) {
        PlanType = planType;
    }

//    public ArrayList<com.shamrock.reework.activity.todaysplan.model.PlanItems> getPlanItems() {
//        return PlanItems;
//    }
//
//    public void setPlanItems(ArrayList<com.shamrock.reework.activity.todaysplan.model.PlanItems> planItems) {
//        PlanItems = planItems;
//    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPlanTime() {
        return PlanTime;
    }

    public void setPlanTime(String planTime) {
        PlanTime = planTime;
    }

    public String getDailyPlanMasterId() {
        return DailyPlanMasterId;
    }

    public void setDailyPlanMasterId(String dailyPlanMasterId) {
        DailyPlanMasterId = dailyPlanMasterId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
