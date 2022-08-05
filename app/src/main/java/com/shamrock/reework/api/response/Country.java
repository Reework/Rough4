package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country
{
    @SerializedName("country_id")
    @Expose
    private Integer countryId;

    @SerializedName("country_name")
    @Expose
    private String countryName;

    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /*@SerializedName("country_id")
    private int id;
    @SerializedName("country_name")
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }*/
}
