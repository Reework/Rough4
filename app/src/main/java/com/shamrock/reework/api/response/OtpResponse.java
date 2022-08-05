package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private DataResponse data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }

    public static class DataResponse
    {

//        @SerializedName("role_ID")
//        @Expose
//        private int role_ID;
        @SerializedName("Userid")
        @Expose
        private Integer userid;
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
        @SerializedName("role_ID")
        @Expose
        private Integer roleID;
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
        @SerializedName("Plan_ID")
        @Expose
        private Integer planID;
        @SerializedName("IsVerified")
        @Expose
        private Boolean isVerified;
        @SerializedName("IsFreezed")
        @Expose
        private Boolean isFreezed;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("otp_status")
        @Expose
        private Object otpStatus;
        @SerializedName("Token")
        @Expose
        private String token;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("Gender")
        @Expose
        private Integer gender;
        @SerializedName("Reecoach_id")
        @Expose
        private Integer reecoachId;
        @SerializedName("HealthParam")
        @Expose
        private Boolean healthParam;
        @SerializedName("ImageUrl")
        @Expose
        private String imageUrl;
        @SerializedName("BloodTestReport")
        @Expose
        private Boolean bloodTestReport;
        @SerializedName("IsBloodTestSchedule")
        @Expose
        private Boolean isBloodTestSchedule;
        @SerializedName("country_name")
        @Expose
        private String countryName;
        @SerializedName("state_name")
        @Expose
        private String stateName;
        @SerializedName("city_name")
        @Expose
        private String cityName;


        @SerializedName("CreatedBy")
        @Expose
        private int CreatedBy;

        public int getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(int createdBy) {
            CreatedBy = createdBy;
        }

//        @SerializedName("SubscribedFeatureList")
//        @Expose
//        private List<SubscribedFeatureList> subscribedFeatureList = null;


        public Integer getRoleID() {
            return roleID;
        }

        public void setRoleID(Integer roleID) {
            this.roleID = roleID;
        }

        //        public int getRole_ID() {
//            return role_ID;
//        }
//
//        public void setRole_ID(int role_ID) {
//            this.role_ID = role_ID;
//        }
        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
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

        public Integer getPlanID() {
            return planID;
        }

        public void setPlanID(Integer planID) {
            this.planID = planID;
        }

        public Boolean getIsVerified() {
            return isVerified;
        }

        public void setIsVerified(Boolean isVerified) {
            this.isVerified = isVerified;
        }

        public Boolean getIsFreezed() {
            return isFreezed;
        }

        public void setIsFreezed(Boolean isFreezed) {
            this.isFreezed = isFreezed;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Object getOtpStatus() {
            return otpStatus;
        }

        public void setOtpStatus(Object otpStatus) {
            this.otpStatus = otpStatus;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public Integer getReecoachId() {
            return reecoachId;
        }

        public void setReecoachId(Integer reecoachId) {
            this.reecoachId = reecoachId;
        }

        public Boolean getHealthParam() {
            return healthParam;
        }

        public void setHealthParam(Boolean healthParam) {
            this.healthParam = healthParam;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Boolean getBloodTestReport() {
            return bloodTestReport;
        }

        public void setBloodTestReport(Boolean bloodTestReport) {
            this.bloodTestReport = bloodTestReport;
        }

        public Boolean getIsBloodTestSchedule() {
            return isBloodTestSchedule;
        }

        public void setIsBloodTestSchedule(Boolean isBloodTestSchedule) {
            this.isBloodTestSchedule = isBloodTestSchedule;
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

//        public List<SubscribedFeatureList> getSubscribedFeatureList() {
//            return subscribedFeatureList;
//        }
//
//        public void setSubscribedFeatureList(List<SubscribedFeatureList> subscribedFeatureList) {
//            this.subscribedFeatureList = subscribedFeatureList;
//        }
    }
    public class SubscribedFeatureList {

        @SerializedName("FeatureID")
        @Expose
        private Integer featureID;
        @SerializedName("FeatureName")
        @Expose
        private String featureName;
        @SerializedName("PlanIDs")
        @Expose
        private String planIDs;

        public Integer getFeatureID() {
            return featureID;
        }

        public void setFeatureID(Integer featureID) {
            this.featureID = featureID;
        }

        public String getFeatureName() {
            return featureName;
        }

        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }

        public String getPlanIDs() {
            return planIDs;
        }

        public void setPlanIDs(String planIDs) {
            this.planIDs = planIDs;
        }

    }

}
