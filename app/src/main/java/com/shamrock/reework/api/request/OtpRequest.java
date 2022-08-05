package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class OtpRequest
{
//    @SerializedName("Email")
    @SerializedName("Mobile_No")
    String Email;
    @SerializedName("OTPNo")
    String Otp;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }
}
