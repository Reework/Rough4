package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodCulturesResponse {

    /**
     * Code : 1
     * Message : Ok
     * Data : [{"FoodcultureID":1,"Foodculture":"Punjabi"},{"FoodcultureID":2,"Foodculture":"Bengoli"},{"FoodcultureID":3,"Foodculture":"Marathi"},{"FoodcultureID":4,"Foodculture":"Gujrati"}]
     */

   /* @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Data")
    private List<FoodCulture> Data;

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

    public List<FoodCulture> getData() {
        return Data;
    }

    public void setData(List<FoodCulture> Data) {
        this.Data = Data;
    }
*/

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<FoodCulture> data = null;

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

    public List<FoodCulture> getData() {
        return data;
    }

    public void setData(List<FoodCulture> data) {
        this.data = data;
    }
}
