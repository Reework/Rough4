package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class TempReqForBeforeAfter
{
    @SerializedName("UserID")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
