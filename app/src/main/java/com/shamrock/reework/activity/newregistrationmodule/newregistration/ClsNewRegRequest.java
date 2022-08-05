package com.shamrock.reework.activity.newregistrationmodule.newregistration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsNewRegRequest {


//    @SerializedName("Pincode")
//    @Expose
//    private String Pincode;

    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Mobile_No")
    @Expose
    private String mobileNo;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Gender")
    @Expose
    private Integer gender;


    @SerializedName("Token")
    @Expose
    private String Token;


//    public String getPincode() {
//        return Pincode;
//    }
//
//    public void setPincode(String pincode) {
//        Pincode = pincode;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
