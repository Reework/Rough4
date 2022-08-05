package com.shamrock.reework.activity.AnalysisModule.activity.newregistration;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.AnalysisModule.activity.userQuestiondata.QuestionData;

import java.util.ArrayList;

public class ClsregQn {
    private String Message;

    @SerializedName("Data")
    private ArrayList<QuestionData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<QuestionData> getData() {
        return Data;
    }

    public void setData(ArrayList<QuestionData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
