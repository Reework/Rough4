package com.shamrock.reework.activity.newmyplan;

import com.shamrock.reework.activity.todaysplan.model.ReeworkerPlan;

import java.util.ArrayList;

public class ClsTodaysPlanNewResult {
    private String ReeworkerName;

    private ArrayList<ReeworkerPlan> ReeworkerPlan;

    private String Title;

    private String Id;

    private String CreatedOn;

    private String Gender;

    private String ReecoachName;

    private String ReeworkerId;

    private String Remark;

    public String getReeworkerName() {
        return ReeworkerName;
    }

    public void setReeworkerName(String reeworkerName) {
        ReeworkerName = reeworkerName;
    }

    public ArrayList<ReeworkerPlan> getReeworkerPlan() {
        return ReeworkerPlan;
    }

    public void setReeworkerPlan(ArrayList<com.shamrock.reework.activity.todaysplan.model.ReeworkerPlan> reeworkerPlan) {
        ReeworkerPlan = reeworkerPlan;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getReecoachName() {
        return ReecoachName;
    }

    public void setReecoachName(String reecoachName) {
        ReecoachName = reecoachName;
    }

    public String getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(String reeworkerId) {
        ReeworkerId = reeworkerId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
