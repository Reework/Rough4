package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BcaRequest
{
    @SerializedName("UserId")
    @Expose
    private Integer userid;
    public Integer getMeal_cal_max() {
        return meal_cal_max;
    }
    public void setMeal_cal_max(Integer meal_cal_max) {
        this.meal_cal_max = meal_cal_max;
    }
    @SerializedName("meal_cal_max")
    @Expose
    private Integer meal_cal_max;
    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;
    @SerializedName("meal_type")
    @Expose
    private String meal_type;
    public String getMeal_type() {
        return meal_type;
    }
    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }
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
}
