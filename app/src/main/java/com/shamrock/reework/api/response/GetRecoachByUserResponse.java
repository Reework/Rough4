package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRecoachByUserResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data
    {
        @SerializedName("Userid")
        @Expose
        private Integer userid;
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
        @SerializedName("city_id")
        @Expose
        private Integer cityId;
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
        @SerializedName("DeletedOn")
        @Expose
        private Object deletedOn;
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
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("lang_code")
        @Expose
        private String langCode;
        @SerializedName("Reecoach_id")
        @Expose
        private Integer reecoachId;
        @SerializedName("HealthParam")
        @Expose
        private Boolean healthParam;
        @SerializedName("Password")
        @Expose
        private Object password;
        @SerializedName("Image_URL")
        @Expose
        private Object imageURL;
        @SerializedName("BloodTestReport")
        @Expose
        private Boolean bloodTestReport;
        @SerializedName("State_id")
        @Expose
        private Integer stateId;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("ModifiedBy")
        @Expose
        private Integer modifiedBy;
        @SerializedName("ModifiedOn")
        @Expose
        private String modifiedOn;
        @SerializedName("DeletedBY")
        @Expose
        private Object deletedBY;

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
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

        public Integer getRoleID() {
            return roleID;
        }

        public void setRoleID(Integer roleID) {
            this.roleID = roleID;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
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

        public Object getDeletedOn() {
            return deletedOn;
        }

        public void setDeletedOn(Object deletedOn) {
            this.deletedOn = deletedOn;
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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLangCode() {
            return langCode;
        }

        public void setLangCode(String langCode) {
            this.langCode = langCode;
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

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getImageURL() {
            return imageURL;
        }

        public void setImageURL(Object imageURL) {
            this.imageURL = imageURL;
        }

        public Boolean getBloodTestReport() {
            return bloodTestReport;
        }

        public void setBloodTestReport(Boolean bloodTestReport) {
            this.bloodTestReport = bloodTestReport;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Integer getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Integer modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public Object getDeletedBY() {
            return deletedBY;
        }

        public void setDeletedBY(Object deletedBY) {
            this.deletedBY = deletedBY;
        }
    }
}
