package com.shamrock.reework.activity.WeightModule.activity;

public class ClWtUpdateRequest {

    private int HealthParamID;
    private int Weight;
    private  String CreatedOn;

    public int getHealthParamID() {
        return HealthParamID;
    }

    public void setHealthParamID(int healthParamID) {
        HealthParamID = healthParamID;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
