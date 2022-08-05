package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodAnalysisRequest {

    @SerializedName("UserId")
    @Expose
    private Integer userid;
    @SerializedName("Reecoach_id")
    @Expose
    private Integer reecoachId;
    @SerializedName("days")
    @Expose
    private Integer days;

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

    public Integer getReecoachId() {
        return reecoachId;
    }

    public void setReecoachId(Integer reecoachId) {
        this.reecoachId = reecoachId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
