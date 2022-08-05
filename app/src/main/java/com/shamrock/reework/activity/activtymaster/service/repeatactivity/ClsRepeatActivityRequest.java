package com.shamrock.reework.activity.activtymaster.service.repeatactivity;

public class ClsRepeatActivityRequest {
    private int TotalMinutes;

    private int UserId;

    private int ActivityId;

    private int Id;

    private String CreatedOn;

    private String ActivityTime;

    private int SubActivityId;

    public ClsRepeatActivityRequest(int totalMinutes, int userId, int activityId, int id, String createdOn, String activityTime, int subActivityId) {
        TotalMinutes = totalMinutes;
        UserId = userId;
        ActivityId = activityId;
        Id = id;
        CreatedOn = createdOn;
        ActivityTime = activityTime;
        SubActivityId = subActivityId;
    }

    public int getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        TotalMinutes = totalMinutes;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int activityId) {
        ActivityId = activityId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public int getSubActivityId() {
        return SubActivityId;
    }

    public void setSubActivityId(int subActivityId) {
        SubActivityId = subActivityId;
    }
}
