package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CountryResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<Country> data = null;

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

    public ArrayList<Country> getData() {
        return data;
    }

    public void setData(ArrayList<Country> data) {
        this.data = data;
    }

    /*@SerializedName("Code")
    private int serverResponseCode;
    @SerializedName("Message")
    private String serverResponseMessage;
    @SerializedName("Data")
    private List<Country> countryList;

    public int getCode() {
        return serverResponseCode;
    }

    public void setCode(int serverResponseCode) {
        this.serverResponseCode = serverResponseCode;
    }

    public String getMessage() {
        return serverResponseMessage;
    }

    public void setMessage(String serverResponseMessage) {
        this.serverResponseMessage = serverResponseMessage;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }*/
}
