package com.shamrock.reework.activity.otpmodule;

public interface OtpReceivedInterface {
    void onOtpReceived(String otp);
    void onOtpTimeout();
}
