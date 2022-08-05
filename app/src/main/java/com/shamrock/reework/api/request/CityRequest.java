package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class CityRequest {

    @SerializedName("state_id")
    private int stateId;

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }


}
