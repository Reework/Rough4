package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.AppoinmentModule.pojo.ReecoachAppointmentData;

public class AppoinmentResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private ReecoachAppointmentData data;

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

    public ReecoachAppointmentData getData() {
        return data;
    }

    public void setData(ReecoachAppointmentData data) {
        this.data = data;
    }
}
