package com.shamrock.reework.activity.unfreezeData;

import com.google.gson.annotations.SerializedName;

public class ClsUnfreezeData {
    private String MobileInfo;

    @SerializedName("EmailInfo")
    private String emailinfo;

    private String Id;

    private String MessageInfo;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMessageInfo() {
        return MessageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        MessageInfo = messageInfo;
    }

    public String getEmailinfo() {
        return emailinfo;
    }

    public void setEmailinfo(String emailinfo) {
        this.emailinfo = emailinfo;
    }

    public String getMobileInfo() {
        return MobileInfo;
    }

    public void setMobileInfo(String mobileInfo) {
        MobileInfo = mobileInfo;
    }
}
