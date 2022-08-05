package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMoodRequest {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("IsBingeOnLargeQuantity")
    @Expose
    private Integer isBingeOnLargeQuantity;
    @SerializedName("ActualMindStatus")
    @Expose
    private String actualMindStatus;
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsBingeOnLargeQuantity() {
        return isBingeOnLargeQuantity;
    }

    public void setIsBingeOnLargeQuantity(Integer isBingeOnLargeQuantity) {
        this.isBingeOnLargeQuantity = isBingeOnLargeQuantity;
    }

    public String getActualMindStatus() {
        return actualMindStatus;
    }

    public void setActualMindStatus(String actualMindStatus) {
        this.actualMindStatus = actualMindStatus;
    }
}
