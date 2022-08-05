package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StateResponse
{


    /**
     * Code : 1
     * Message : Ok
     * Data : [{"state_id":1,"state_name":"Mumbai"},{"state_id":2,"state_name":"Pune"},{"state_id":3,"state_name":"Delhi"}]
     */

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<State> data = null;

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

    public ArrayList<State> getData() {
        return data;
    }

    public void setData(ArrayList<State> data) {
        this.data = data;
    }

   /* @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Data")
    private List<State> Data;

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

    public List<State> getData() {
        return Data;
    }

    public void setData(List<State> data) {
        Data = data;
    }*/
}
