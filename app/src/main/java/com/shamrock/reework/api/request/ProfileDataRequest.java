package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class ProfileDataRequest
{
    @SerializedName("Userid")
    private int userid;

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }
}
