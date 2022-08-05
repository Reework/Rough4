package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodTestReportResponse {
    /**
     * Code : 1
     * Message : Ok
     * Data : [{"UploadDate":"19/10/2018","ReportLink":"http://shamrockreework.dweb.in/api/UserBloodTestSchedule/PostBloodSchedule"}]
     */

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
    private List<BloodReportItem> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<BloodReportItem> getData() {
        return Data;
    }

    public void setData(List<BloodReportItem> Data) {
        this.Data = Data;
    }

}
