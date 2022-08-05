package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State
{

    /**
     * state_id : 1
     * state_name : Maharashtra
     */

    @SerializedName("State_id")
    @Expose
    private Integer stateId;

    @SerializedName("State_name")
    @Expose
    private String stateName;

    @SerializedName("country_id")
    @Expose
    private Integer countryId;

    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /*@SerializedName("State_id")
    private int state_id;
    @SerializedName("State_name")
    private String state_name;

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }*/
}
