package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReetestRequest
{

    @SerializedName("Scheduled_By_UserID")
    @Expose
    private Integer scheduledByUserID;
    @SerializedName("Scheduledate")
    @Expose
    private String scheduledate;
//    @SerializedName("Scheduletime")
//    @Expose
//    private String scheduletime;
    @SerializedName("Select_Address")
    @Expose
    private Integer selectAddress;
    @SerializedName("New_Address")
    @Expose
    private String newAddress;

    @SerializedName("Pincode")
    @Expose
    private int Pincode;

    public int getPincode() {
        return Pincode;
    }

    public void setPincode(int pincode) {
        Pincode = pincode;
    }

    public Integer getScheduledByUserID() {
        return scheduledByUserID;
    }

    public void setScheduledByUserID(Integer scheduledByUserID) {
        this.scheduledByUserID = scheduledByUserID;
    }

    public String getScheduledate() {
        return scheduledate;
    }

    public void setScheduledate(String scheduledate) {
        this.scheduledate = scheduledate;
    }

//    public String getScheduletime() {
//        return scheduletime;
//    }
//
//    public void setScheduletime(String scheduletime) {
//        this.scheduletime = scheduletime;
//    }

    public Integer getSelectAddress() {
        return selectAddress;
    }

    public void setSelectAddress(Integer selectAddress) {
        this.selectAddress = selectAddress;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
}
