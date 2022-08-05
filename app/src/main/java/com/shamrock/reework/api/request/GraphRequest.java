package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class GraphRequest {
    @SerializedName("UserID")
    private int userid;
    @SerializedName("Healthdate")
    private String monthName;

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonthName() {
        return monthName;
    }
}
