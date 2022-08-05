package com.shamrock.reework.model.MasterActivty;

public class AddActivityRequest {

    private String TotalMinutes;

    private String CreatedOn;


    private String UserId;

    private String ActivityId;

    private String Id;

    private String SubActivityId;
    private String ActivityTime;


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

    public String getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(String totalMinutes) {
        TotalMinutes = totalMinutes;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getActivityId() {
        return ActivityId;
    }

    public void setActivityId(String activityId) {
        ActivityId = activityId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSubActivityId() {
        return SubActivityId;
    }

    public void setSubActivityId(String subActivityId) {
        SubActivityId = subActivityId;
    }
}
