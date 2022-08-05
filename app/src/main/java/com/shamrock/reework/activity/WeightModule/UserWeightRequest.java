package com.shamrock.reework.activity.WeightModule;

import com.google.gson.annotations.SerializedName;

public class UserWeightRequest {


    @SerializedName("UserID")
    int userid;

    @SerializedName("CurrentWeight")
    String CurrentWeight;

    @SerializedName("CreatedOn")
    String CreatedOn;

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getCurrentWeight() {
        return CurrentWeight;
    }

    public void setCurrentWeight(String currentWeight) {
        CurrentWeight = currentWeight;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
