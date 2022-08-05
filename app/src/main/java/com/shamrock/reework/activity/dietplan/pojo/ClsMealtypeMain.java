package com.shamrock.reework.activity.dietplan.pojo;

import java.util.ArrayList;

public class ClsMealtypeMain {

    private String Message;

    private ArrayList<MealTypeData> Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<MealTypeData> getData() {
        return Data;
    }

    public void setData(ArrayList<MealTypeData> data) {
        Data = data;
    }


    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
