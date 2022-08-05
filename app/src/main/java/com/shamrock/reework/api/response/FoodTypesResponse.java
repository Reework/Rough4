package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodTypesResponse
{

    /**
     * Code : 1
     * Message : Ok
     * Data : [{"FoodtypeID":1,"Foodtype":"Vegan No Dairy AND Non-Veg"},{"FoodtypeID":2,"Foodtype":"Vegetarian"},{"FoodtypeID":3,"Foodtype":"Jain"},{"FoodtypeID":4,"Foodtype":"Eggetarian Only egg and Vegetarian Foods"},{"FoodtypeID":5,"Foodtype":"Non-Vegetarian"}]
     */

    /*@SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    private List<FoodType> Data;

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

    public List<FoodType> getData() {
        return Data;
    }

    public void setData(List<FoodType> Data) {
        this.Data = Data;
    }*/

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<FoodType> data = null;

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

    public List<FoodType> getData() {
        return data;
    }

    public void setData(List<FoodType> data) {
        this.data = data;
    }


}
