package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeeklyAnalysisRequest {
    @SerializedName("Userid")
    @Expose
    private Integer userid;
    @SerializedName("Reecoach_id")
    @Expose
    private String reecoachId;

    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;


    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getReecoachId() {
        return reecoachId;
    }

    public void setReecoachId(String reecoachId) {
        this.reecoachId = reecoachId;
    }
}
