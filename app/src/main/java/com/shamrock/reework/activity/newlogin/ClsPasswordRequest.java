package com.shamrock.reework.activity.newlogin;

public class ClsPasswordRequest {

    private String Password;
    private String MobileNo;
    private String AuthToken;
    private int AuthType;

    public int getAuthType() {
        return AuthType;
    }

    public void setAuthType(int authType) {
        AuthType = authType;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }
}
