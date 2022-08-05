package com.shamrock.reework.activity.changepassword;

import com.google.gson.annotations.SerializedName;

public class ChangepaawordbyMobrequest {
    private String MobileNo;
    private String Password;

    @SerializedName("Otp")
    private String OldPassword;


    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }
}
