package com.shamrock.reework.activity.FoodModule.history;

import com.google.gson.annotations.SerializedName;

public class ClsHistrydeleteRequest {
    public int UserMealId;
    public String CreatedOn;
    public int getUserMealId() {
        return UserMealId;
    }

    public void setUserMealId(int userMealId) {
        UserMealId = userMealId;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}

