package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    /**
     * city_id : 1
     * city_name : Mumbai
     */

    @SerializedName("city_id")
    @Expose
    private Integer cityId;

    @SerializedName("city_name")
    @Expose
    private String cityName;

    @SerializedName("State_id")
    @Expose
    private Integer stateId;

    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


   /* @SerializedName("city_id")
    private int city_id;
    @SerializedName("city_name")
    private String city_name;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }*/
}
