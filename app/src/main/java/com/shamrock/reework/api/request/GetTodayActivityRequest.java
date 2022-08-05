package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTodayActivityRequest {
    @SerializedName("ReeworkerID")
    @Expose
    private Integer reeworkerID;
    @SerializedName("ReecoachID")
    @Expose
    private Integer reecoachID;

    public Integer getReeworkerID() {
        return reeworkerID;
    }

    public void setReeworkerID(Integer reeworkerID) {
        this.reeworkerID = reeworkerID;
    }

    public Integer getReecoachID() {
        return reecoachID;
    }

    public void setReecoachID(Integer reecoachID) {
        this.reecoachID = reecoachID;
    }
}
