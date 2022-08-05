package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeforeAfterRequest
{
    @SerializedName("ID")
    @Expose
    private Integer iD;

    @SerializedName("UserID")
    @Expose
    private Integer userID;

    @SerializedName("BeforeFilePath")
    @Expose
    private String beforeFilePath;

    @SerializedName("AfterFilePath")
    @Expose
    private String afterFilePath;

    @SerializedName("IsAfter")
    @Expose
    private Boolean isAfter;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getBeforeFilePath() {
        return beforeFilePath;
    }

    public void setBeforeFilePath(String beforeFilePath) {
        this.beforeFilePath = beforeFilePath;
    }

    public String getAfterFilePath() {
        return afterFilePath;
    }

    public void setAfterFilePath(String afterFilePath) {
        this.afterFilePath = afterFilePath;
    }

    public Boolean getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(Boolean isAfter) {
        this.isAfter = isAfter;
    }
}
