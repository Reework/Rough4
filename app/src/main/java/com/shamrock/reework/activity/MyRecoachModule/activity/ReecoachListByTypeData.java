package com.shamrock.reework.activity.MyRecoachModule.activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReecoachListByTypeData {

    private String IdaNumber;

    private String ReecoachType;

    private String Email;

    private String Address;

    private String FirstName;

    private String Userid;

    private String Mobile_No;

    private String LastName;

    private String Id;

    private String Pincode;
    private String ImageUrl;
    private float Rating;
    private String Discription;

    public String getIdaNumber() {
        return IdaNumber;
    }

    public void setIdaNumber(String idaNumber) {
        IdaNumber = idaNumber;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getReecoachType() {
        return ReecoachType;
    }

    public void setReecoachType(String reecoachType) {
        ReecoachType = reecoachType;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }
}
