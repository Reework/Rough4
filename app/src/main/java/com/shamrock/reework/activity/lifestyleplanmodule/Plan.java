package com.shamrock.reework.activity.lifestyleplanmodule;

public class Plan {

    private String DurationInMinute;

    private String ActivityName;

    private String DayType;

    private String PlanType;

    private String Id;

    private String CreatedOn;

    private String ActivityTime;

    private String ReeworkerId;

    private boolean isAdded;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getDurationInMinute() {
        return DurationInMinute;
    }

    public void setDurationInMinute(String durationInMinute) {
        DurationInMinute = durationInMinute;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getDayType() {
        return DayType;
    }

    public void setDayType(String dayType) {
        DayType = dayType;
    }

    public String getPlanType() {
        return PlanType;
    }

    public void setPlanType(String planType) {
        PlanType = planType;
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

    public String getActivityTime() {
        return ActivityTime;
    }

    public void setActivityTime(String activityTime) {
        ActivityTime = activityTime;
    }

    public String getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(String reeworkerId) {
        ReeworkerId = reeworkerId;
    }
}
