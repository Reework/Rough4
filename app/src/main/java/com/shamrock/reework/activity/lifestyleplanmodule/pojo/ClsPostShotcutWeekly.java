package com.shamrock.reework.activity.lifestyleplanmodule.pojo;

public class ClsPostShotcutWeekly {


    ;
    private String PlanType;
    private String ActivityName;
    private String ActivityTime;
    private String CreatedOn;
    private int DayType;
    private  int DurationInMinute;
    private int Id;
    private int ReeworkerId;

    public ClsPostShotcutWeekly(String planType, String activityName, String activityTime, String createdOn, int dayType, int durationInMinute, int id, int reeworkerId) {
        PlanType = planType;
        ActivityName = activityName;
        ActivityTime = activityTime;
        CreatedOn = createdOn;
        DayType = dayType;
        DurationInMinute = durationInMinute;
        Id = id;
        ReeworkerId = reeworkerId;
    }

    public String getPlanType() {
        return PlanType;
    }

    public void setPlanType(String planType) {
        PlanType = planType;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getActivityTime() {
        return ActivityTime;
    }

    public void setActivityTime(String activityTime) {
        ActivityTime = activityTime;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getDayType() {
        return DayType;
    }

    public void setDayType(int dayType) {
        DayType = dayType;
    }

    public int getDurationInMinute() {
        return DurationInMinute;
    }

    public void setDurationInMinute(int durationInMinute) {
        DurationInMinute = durationInMinute;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }
}
