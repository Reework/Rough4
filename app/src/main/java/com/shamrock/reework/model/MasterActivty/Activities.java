package com.shamrock.reework.model.MasterActivty;

import java.io.Serializable;

public class Activities implements Serializable {
    private String ActivityName;

    private String ActivityId;

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getActivityId() {
        return ActivityId;
    }

    public void setActivityId(String activityId) {
        ActivityId = activityId;
    }
}
