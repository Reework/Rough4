package com.shamrock.reework.activity.todaysplan.model;

import java.util.ArrayList;

public class TodaysPlanData {
    private String ReeworkerName;

    private ArrayList<ReeworkerPlan> ReeworkerPlan;

    private String Id;

    private String CreatedOn;

    private String ReecoachName;
    private String Title;

    private String Remark;

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getReeworkerName() {
        return ReeworkerName;
    }

    public void setReeworkerName(String reeworkerName) {
        ReeworkerName = reeworkerName;
    }

    public ArrayList<ReeworkerPlan> getReeworkerPlan() {
        return ReeworkerPlan;
    }

    public void setReeworkerPlan(ArrayList<ReeworkerPlan> reeworkerPlan) {
        ReeworkerPlan = reeworkerPlan;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getReecoachName() {
        return ReecoachName;
    }

    public void setReecoachName(String reecoachName) {
        ReecoachName = reecoachName;
    }
}
