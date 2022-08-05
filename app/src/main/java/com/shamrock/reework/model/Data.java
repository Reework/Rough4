package com.shamrock.reework.model;

import com.shamrock.reework.model.MasterActivty.AcivityHistory;

import java.util.ArrayList;

public class Data {
    private Result result;

    private String UserId;
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
