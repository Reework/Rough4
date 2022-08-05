package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReecoachDetailsRequest
{
   /* {
        "Userid": "1066",
            "Email": "hadi.k@intelegain.com"
    }*/

    @SerializedName("Userid")
    @Expose
    private Integer userid;
    @SerializedName("Email")
    @Expose
    private String email;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
