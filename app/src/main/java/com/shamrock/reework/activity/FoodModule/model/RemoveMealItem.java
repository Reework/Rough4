package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveMealItem {
    @SerializedName("UserMealId")
    @Expose
    private Integer userMealId;

    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public Integer getUserMealId() {
        return userMealId;
    }

    public void setUserMealId(Integer userMealId) {
        this.userMealId = userMealId;
    }
}
