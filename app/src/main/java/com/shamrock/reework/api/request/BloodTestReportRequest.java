package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class BloodTestReportRequest {

    /**
     * UserID : 7
     */

    @SerializedName("UserID")
    private String UserID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }
}
