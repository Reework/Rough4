package com.shamrock.reework.activity.HealthModule.service;

import com.google.gson.annotations.SerializedName;

public class HealthRequest {
    @SerializedName("UserId")
    private int UserId;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
