package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodTripRequest
{

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("p_value")
    @Expose
    private Integer pValue;
    @SerializedName("p_text")
    @Expose
    private String pText;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPValue() {
        return pValue;
    }

    public void setPValue(Integer pValue) {
        this.pValue = pValue;
    }

    public String getPText() {
        return pText;
    }

    public void setPText(String pText) {
        this.pText = pText;
    }
}
