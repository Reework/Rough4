package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDataResponse {

    /**
     * Code : 1
     * Message : login Successfully
     * Data : {"ImageUrl":"http://shamrockreework.dweb.in/Databank/Shamrock_Upload/Reework_Images/28/28.png","FirstName":"Pankaj","LastName":"Gadge","DOB":"19/11/1991","Gender":3,"Email":"pankaj.g@intelegain.com","Mobile_No":"8767888588","Address":"vashi","country_id":109,"State_id":1483,"city_id":0,"lang_code":"pj","Token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ijg3Njc4ODg1ODgiLCJuYmYiOjE1NDA4OTY4NTgsImV4cCI6MTU0MDk4MzI1OCwiaWF0IjoxNTQwODk2ODU4fQ.PtoCwt0dBZDr-LzsZ8rDi4YD6mgBgMjTzrvFZvbj5EM"}
     */

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
    private DataResponse Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataResponse getData() {
        return Data;
    }

    public void setData(DataResponse Data) {
        this.Data = Data;
    }

    public static class DataResponse
    {
        /**
         * ImageUrl : http://shamrockreework.dweb.in/Databank/Shamrock_Upload/Reework_Images/28/28.png
         * FirstName : Pankaj
         * LastName : Gadge
         * DOB : 19/11/1991
         * Gender : 3
         * Email : pankaj.g@intelegain.com
         * Mobile_No : 8767888588
         * Address : vashi
         * country_id : 109
         * State_id : 1483
         * city_id : 0
         * lang_code : pj
         * Token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ijg3Njc4ODg1ODgiLCJuYmYiOjE1NDA4OTY4NTgsImV4cCI6MTU0MDk4MzI1OCwiaWF0IjoxNTQwODk2ODU4fQ.PtoCwt0dBZDr-LzsZ8rDi4YD6mgBgMjTzrvFZvbj5EM
         */


        @SerializedName("BloodGroup")
        @Expose
        private String BloodGroup;
        @SerializedName("Age")
        @Expose
        private int age;
        @SerializedName("ImageUrl")
        @Expose
        private String imageUrl;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("Gender")
        @Expose
        private Integer gender;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("country_id")
        @Expose
        private Integer countryId;
        @SerializedName("State_id")
        @Expose
        private Integer stateId;
        @SerializedName("city_id")
        @Expose
        private Integer cityId;
        @SerializedName("lang_code")
        @Expose
        private String langCode;
        @SerializedName("Token")
        @Expose
        private String token;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        @SerializedName("state_name")
        @Expose
        private String stateName;
        @SerializedName("city_name")
        @Expose
        private String cityName;

        @SerializedName("Pincode")
        @Expose
        private String Pincode;

        @SerializedName("Discription")
        @Expose
        private String aboutme;


//        UniqueId

        @SerializedName("UniqueId")
        @Expose
        private String UniqueId;

        public String getUniqueId() {
            return UniqueId;
        }

        public String getBloodGroup() {
            return BloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            BloodGroup = bloodGroup;
        }

        public void setUniqueId(String uniqueId) {
            UniqueId = uniqueId;
        }

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
            this.Pincode = pincode;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

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

        public String getDOB() {
            return dOB;
        }

        public void setDOB(String dOB) {
            this.dOB = dOB;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getLangCode() {
            return langCode;
        }

        public void setLangCode(String langCode) {
            this.langCode = langCode;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }

}
