package com.shamrock.reework.activity.HealthModule.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul on 06,June,2020
 */
public class PaidSubscriptionList {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<PaidSubscriptionData> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PaidSubscriptionData> getData() {
        return data;
    }

    public void setData(List<PaidSubscriptionData> data) {
        this.data = data;
    }

}
