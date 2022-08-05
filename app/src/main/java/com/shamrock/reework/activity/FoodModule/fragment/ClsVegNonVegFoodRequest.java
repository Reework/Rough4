package com.shamrock.reework.activity.FoodModule.fragment;

import com.google.gson.annotations.SerializedName;

public class ClsVegNonVegFoodRequest {
    @SerializedName("UserID")
    private int UserId;
    @SerializedName("Dietary_preference")
    private String Dietary_preference;

    private boolean IsHealthy;

    private String Classification;

    public String getClassification() {
        return Classification;
    }

    public void setClassification(String classification) {
        Classification = classification;
    }

    public boolean isHealthy() {
        return IsHealthy;
    }

    public void setHealthy(boolean healthy) {
        IsHealthy = healthy;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getDietary_preference() {
        return Dietary_preference;
    }

    public void setDietary_preference(String dietary_preference) {
        Dietary_preference = dietary_preference;
    }
}
