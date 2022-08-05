package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityResponse
{


    /**
     * Code : 1
     * Message : Ok
     * Data : [{"city_id":1,"city_name":"Mumbai"},{"city_id":2,"city_name":"Pune"},{"city_id":3,"city_name":"Delhi"}]
     */

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<City> data = null;

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

    public ArrayList<City> getData() {
        return data;
    }

    public void setData(ArrayList<City> data) {
        this.data = data;
    }

  /*  @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Data")
    private List<City> Data;

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

    public List<City> getData() {
        return Data;
    }

    public void setData(List<City> data) {
        Data = data;
    }*/
}
