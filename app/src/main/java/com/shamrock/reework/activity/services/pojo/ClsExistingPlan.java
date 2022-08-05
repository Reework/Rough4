package com.shamrock.reework.activity.services.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionData;

import java.util.List;

public class ClsExistingPlan {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ExistingPlanData data = null;

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

    public ExistingPlanData getData() {
        return data;
    }

    public void setData(ExistingPlanData data) {
        this.data = data;
    }
}
