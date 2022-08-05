package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticationRequest
{
    @SerializedName("Mobile_No")
    @Expose
    private String mobileNo;
    @SerializedName("fcm_deviceToken")
    @Expose
    private String fcmDeviceToken;
    @SerializedName("DeviceTypeID")
    @Expose
    private Integer deviceTypeID;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getFcmDeviceToken() {
        return fcmDeviceToken;
    }

    public void setFcmDeviceToken(String fcmDeviceToken) {
        this.fcmDeviceToken = fcmDeviceToken;
    }

    public Integer getDeviceTypeID() {
        return deviceTypeID;
    }

    public void setDeviceTypeID(Integer deviceTypeID) {
        this.deviceTypeID = deviceTypeID;
    }
}
