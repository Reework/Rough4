package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.api.response.MyPlanResponse;

import java.util.ArrayList;

public class MyPlanRequest
{
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("Plan_ID")
    @Expose
    private Integer planID;
    @SerializedName("Reecoach_id")
    @Expose
    private Integer reecoachId;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getPlanID() {
        return planID;
    }

    public void setPlanID(Integer planID) {
        this.planID = planID;
    }

    public Integer getReecoachId() {
        return reecoachId;
    }

    public void setReecoachId(Integer reecoachId) {
        this.reecoachId = reecoachId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
