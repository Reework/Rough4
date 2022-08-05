package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppoinmentRequest
{
   /* {
        "Date":"01/01/2019",
            "Time":"2:20 PM",
            "comment":"New Appintment scheduled",
            "EndTime" : "01/01/2019",
            "ReCoachID":3
            "UserId" : 1066
    }*/



    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("Time")
    @Expose
    private String time;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("EndTime")
    @Expose
    private String endTime;

    @SerializedName("ReCoachID")
    @Expose
    private Integer reCoachID;

    @SerializedName("UserId")
    @Expose
    private Integer userId;



    @SerializedName("VenuType")
    @Expose
    private Integer VenuType;

//    appointmentTypeId

    @SerializedName("appointmentTypeId")
    @Expose
    private int appointmentTypeId;

    public int getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(int appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public Integer getVenuTypeType() {
        return VenuType;
    }

    public void setVenuTypeType(Integer venuTypeType) {
        VenuType = venuTypeType;
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

    public Integer getReCoachID() {
        return reCoachID;
    }

    public void setReCoachID(Integer reCoachID) {
        this.reCoachID = reCoachID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
