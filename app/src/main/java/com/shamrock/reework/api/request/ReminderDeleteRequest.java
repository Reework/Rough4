package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReminderDeleteRequest
{
    @SerializedName("ReminderID")
    @Expose
    private Integer reminderID;

    public Integer getReminderID() {
        return reminderID;
    }

    public void setReminderID(Integer reminderID) {
        this.reminderID = reminderID;
    }
}
