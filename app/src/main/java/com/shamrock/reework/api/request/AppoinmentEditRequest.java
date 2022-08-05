package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppoinmentEditRequest
{
    @SerializedName("ApptID")
    @Expose
    private Integer apptID;

    @SerializedName("UserID")
    @Expose
    private Integer userID;

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("Time")
    @Expose
    private String time;



    @SerializedName("VenuType")
    @Expose
    private Integer VenuType;


    @SerializedName("Comment")
    @Expose
    private String comment;

    @SerializedName("EndTime")
    @Expose
    private String endTime;

    @SerializedName("ReeCoachId")
    @Expose
    private Integer reeCoachId;

    public Integer getApptID() {
        return apptID;
    }

    public void setApptID(Integer apptID) {
        this.apptID = apptID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getVenuType() {
        return VenuType;
    }

    public void setVenuType(Integer venuType) {
        VenuType = venuType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getReeCoachId() {
        return reeCoachId;
    }

    public void setReeCoachId(Integer reeCoachId) {
        this.reeCoachId = reeCoachId;
    }
}
