package com.shamrock.reework.activity.cheatplan.pojo;

import java.util.ArrayList;

public class ClsMainPlansClass {

    private String Message;

    ArrayList<Data> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<com.shamrock.reework.activity.cheatplan.pojo.Data> getData() {
        return Data;
    }

    public void setData(ArrayList<com.shamrock.reework.activity.cheatplan.pojo.Data> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
