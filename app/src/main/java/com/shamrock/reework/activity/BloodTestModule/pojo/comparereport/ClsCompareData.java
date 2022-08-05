package com.shamrock.reework.activity.BloodTestModule.pojo.comparereport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClsCompareData {
    private String Message;
    private String Code;
    private Map<String, String> Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Map<String, String> getData() {
        return Data;
    }

    public void setData(Map<String, String> data) {
        Data = data;
    }
}
