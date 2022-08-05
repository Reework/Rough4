package com.shamrock.reework.activity.AppoinmentModule.pojo;

public class ReecoachAppointmentData {
  boolean IsPay;
    String PayLink;

    public boolean isPay() {
        return IsPay;
    }

    public void setPay(boolean pay) {
        IsPay = pay;
    }

    public String getPayLink() {
        return PayLink;
    }

    public void setPayLink(String payLink) {
        PayLink = payLink;
    }
}
