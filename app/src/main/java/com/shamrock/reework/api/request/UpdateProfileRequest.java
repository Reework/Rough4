package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest {

    /**
     * UserID : 7
     * country_id : 1
     * city_id  : 2
     * lang_code : hi
     * Address : vashi
     * ImageURL : ASADSDSDFSFF
     */

    @SerializedName("Age")
    private int age;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @SerializedName("Userid")
    private String UserID;

    @SerializedName("Country_id")
    private String country_id;

    @SerializedName("State_id")
    private String state_id;

    @SerializedName("City_id")
    private String city_id;

    @SerializedName("Lang_code")
    private String lang_code;

    @SerializedName("Address")
    private String Address;

    @SerializedName("DOB")
    private String dOB;

    @SerializedName("ImageUrl")
    private String ImageUrl;


    @SerializedName("Pincode")
    private String Pincode;

    @SerializedName("Discription")
    private String aboutme;

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
    //private String ImageURL;


    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLang_code() {
        return lang_code;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

}
