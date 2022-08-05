package com.shamrock.reework.model.MasterActivty;

import java.io.Serializable;

public class SubActivities implements Serializable {
    private String ActivityId;

    private String SubActivityName;

    private String SubActivityId;

    public String getActivityId() {
        return ActivityId;
    }

    public void setActivityId(String activityId) {
        ActivityId = activityId;
    }

    public String getSubActivityName() {
        return SubActivityName;
    }

    public void setSubActivityName(String subActivityName) {
        SubActivityName = subActivityName;
    }

    public String getSubActivityId() {
        return SubActivityId;
    }

    public void setSubActivityId(String subActivityId) {
        SubActivityId = subActivityId;
    }
}
