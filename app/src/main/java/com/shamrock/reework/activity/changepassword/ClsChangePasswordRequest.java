package com.shamrock.reework.activity.changepassword;

import com.google.gson.annotations.SerializedName;

public class ClsChangePasswordRequest {
    private int ReeworkerId;
    private String Password;

    @SerializedName("Otp")
    private String OldPassword;

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
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
