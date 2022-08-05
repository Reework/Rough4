package com.shamrock.reework.activity.todaysplan.model;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.MyPlansModule.score.Score;

import java.util.ArrayList;

public class ClsTodaysPlanMain {
    private String Message;

    @SerializedName("Data")
    private TodaysPlanData Data;

    private int Code;


    private ArrayList<Score> Score ;

    public ArrayList<Score> getScore() {
        return Score;
    }

    public void setScore(ArrayList<Score> score) {
        Score = score;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public TodaysPlanData getData() {
        return Data;
    }

    public void setData(TodaysPlanData data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
