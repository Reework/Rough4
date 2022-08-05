package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class StateRequest {

    @SerializedName("country_id")
    private int countryId;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


}
