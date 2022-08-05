package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LanguageResponse {

    /**
     * Code : 1
     * Message : Ok
     * Data : [{"lang_code":"hi","lang_name":"Hindi"},{"lang_code":"mr","lang_name":"Marathi"},{"lang_code":"en","lang_name":"English"},{"lang_code":"pj","lang_name":"Pujabi"}]
     */
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<Language> data = null;

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

    public ArrayList<Language> getData() {
        return data;
    }

    public void setData(ArrayList<Language> data) {
        this.data = data;
    }

/*
    @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Data")
    private List<Language> Data;

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

    public List<Language> getData() {
        return Data;
    }

    public void setData(List<Language> Data) {
        this.Data = Data;
    }
*/

}
