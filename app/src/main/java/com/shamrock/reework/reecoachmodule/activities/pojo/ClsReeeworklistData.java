package com.shamrock.reework.reecoachmodule.activities.pojo;

public class ClsReeeworklistData {
//    "ProfileImg": "http://localhost:62096/databank/shamrock_upload/Reework_Images/7225/7225.png",
//            "Userid": 7225,
//            "FullName": "Sandip S",
//            "Email": "aksingh2810@gmail.com",
//            "MobileNo": "8369526343",
//            "Age": 0,
//            "Address": "Dadar",
//            "Pincode": "400610",
//            "City": "Thane",
//            "State": "Maharashtra",
//            "Country": "India",
//            "UnreadMessageCount": 0

    private String ProfileImg;
    private int Userid;
    private String FullName;
    private String Email;
    private String MobileNo;
    private String Address;
    private int Age;
    private int UnreadMessageCount;

    public String getAddress() {
        return Address;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getProfileImg() {
        return ProfileImg;
    }

    public void setProfileImg(String profileImg) {
        ProfileImg = profileImg;
    }

    public int getUserid() {
        return Userid;
    }

    public void setUserid(int userid) {
        Userid = userid;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public int getUnreadMessageCount() {
        return UnreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        UnreadMessageCount = unreadMessageCount;
    }
}
