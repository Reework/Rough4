package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RescoreRequest
{
    @SerializedName("userid")
    @Expose
    private Integer userid;

    @SerializedName("Healthdate")
    @Expose
    private  String Healthdate;

    public String getHealthdate() {
        return Healthdate;
    }

    public void setHealthdate(String healthdate) {
        Healthdate = healthdate;
    }

    public Integer getUserid()
    {
        return userid;
    }

    public void setUserid(Integer userid)
    {
        this.userid = userid;
    }
}
