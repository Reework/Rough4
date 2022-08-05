package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class MedicineListRequest {
    @SerializedName("userid")
    int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
