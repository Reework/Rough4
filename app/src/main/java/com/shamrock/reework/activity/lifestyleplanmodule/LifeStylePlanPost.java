package com.shamrock.reework.activity.lifestyleplanmodule;

/**
 * Created by Rahul on 05,June,2020
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LifeStylePlanPost {

    @SerializedName("Id")
    @Expose
    private Integer id;


    @SerializedName("PlanType")
    @Expose
    private String PlanType;
    @SerializedName("ReeworkerId")
    @Expose
    private Integer reeworkerId;
    @SerializedName("ActivityName")
    @Expose
    private String activityName;
    @SerializedName("ActivityTime")
    @Expose
    private String activityTime;
    @SerializedName("DurationInMinute")
    @Expose
    private Integer durationInMinute;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("DayType")
    @Expose
    private Integer dayType;

    public String getPlanType() {
        return PlanType;
    }

    public void setPlanType(String planType) {
        PlanType = planType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReeworkerId() {
        return reeworkerId;
    }

    public void setReeworkerId(Integer reeworkerId) {
        this.reeworkerId = reeworkerId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public Integer getDurationInMinute() {
        return durationInMinute;
    }

    public void setDurationInMinute(Integer durationInMinute) {
        this.durationInMinute = durationInMinute;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getDayType() {
        return dayType;
    }

    public void setDayType(Integer dayType) {
        this.dayType = dayType;
    }

    public LifeStylePlanPost(Integer id, Integer reeworkerId, String activityName, String activityTime, Integer durationInMinute, String createdOn, Integer dayType,String PlanType) {
        this.id = id;
        this.reeworkerId = reeworkerId;
        this.activityName = activityName;
        this.activityTime = activityTime;
        this.durationInMinute = durationInMinute;
        this.createdOn = createdOn;
        this.dayType = dayType;
        this.PlanType=PlanType;
    }
}
