package com.shamrock.reework.util;

import com.google.gson.annotations.SerializedName;

public class ClsWeeDaysResponse {

//
//    {
//        "Code": 1,
//            "Message": "Success",
//            "Data": {
//        "Id": 1,
//                "Days": "Sat,Sun",
//                "WeekType": "1",
//                "ReeworkerId": 3040,
//                "UserMaster": null

    private int Code;
    private String Message;
    @SerializedName("Data")
    private WeekResponseData Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public WeekResponseData getData() {
        return Data;
    }

    public void setData(WeekResponseData data) {
        Data = data;
    }
}
