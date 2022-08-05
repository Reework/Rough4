package com.shamrock.reework.activity.newregistrationmodule.newregistration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul on 06,June,2020
 */
public class RegistrationRespo {

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
    public class Data {

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
        private Object token;
        @SerializedName("Address")
        @Expose
        private Object address;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("Gender")
        @Expose
        private Integer gender;
        @SerializedName("lang_code")
        @Expose
        private Object langCode;
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
        @SerializedName("DeviceTypeID")
        @Expose
        private Object deviceTypeID;
        @SerializedName("IsBloodTestSchedule")
        @Expose
        private Boolean isBloodTestSchedule;
        @SerializedName("FCM_DeviceToken")
        @Expose
        private Object fCMDeviceToken;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("ModifiedBy")
        @Expose
        private Object modifiedBy;
        @SerializedName("ModifiedOn")
        @Expose
        private Object modifiedOn;
        @SerializedName("DeletedBY")
        @Expose
        private Object deletedBY;
        @SerializedName("Pincode")
        @Expose
        private Object pincode;
        @SerializedName("Appointments")
        @Expose
        private List<Object> appointments = null;
        @SerializedName("Appointments1")
        @Expose
        private List<Object> appointments1 = null;
        @SerializedName("LabTestReports")
        @Expose
        private List<Object> labTestReports = null;
        @SerializedName("LifestylePlans")
        @Expose
        private List<Object> lifestylePlans = null;
        @SerializedName("Reminders")
        @Expose
        private List<Object> reminders = null;
        @SerializedName("ScheduledStatus")
        @Expose
        private List<Object> scheduledStatus = null;
        @SerializedName("t_UsersLabTest")
        @Expose
        private List<Object> tUsersLabTest = null;
        @SerializedName("t_UsersRescore")
        @Expose
        private List<Object> tUsersRescore = null;
        @SerializedName("TQ_ReeworkerAns")
        @Expose
        private List<Object> tQReeworkerAns = null;
        @SerializedName("UserActivities")
        @Expose
        private List<Object> userActivities = null;
        @SerializedName("UserTestSchedules")
        @Expose
        private List<Object> userTestSchedules = null;
        @SerializedName("UserSubscriptions")
        @Expose
        private List<Object> userSubscriptions = null;
        @SerializedName("UserTestSchedules1")
        @Expose
        private List<Object> userTestSchedules1 = null;

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

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
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

        public Object getLangCode() {
            return langCode;
        }

        public void setLangCode(Object langCode) {
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

        public Object getDeviceTypeID() {
            return deviceTypeID;
        }

        public void setDeviceTypeID(Object deviceTypeID) {
            this.deviceTypeID = deviceTypeID;
        }

        public Boolean getIsBloodTestSchedule() {
            return isBloodTestSchedule;
        }

        public void setIsBloodTestSchedule(Boolean isBloodTestSchedule) {
            this.isBloodTestSchedule = isBloodTestSchedule;
        }

        public Object getFCMDeviceToken() {
            return fCMDeviceToken;
        }

        public void setFCMDeviceToken(Object fCMDeviceToken) {
            this.fCMDeviceToken = fCMDeviceToken;
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

        public Object getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Object modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public Object getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(Object modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public Object getDeletedBY() {
            return deletedBY;
        }

        public void setDeletedBY(Object deletedBY) {
            this.deletedBY = deletedBY;
        }

        public Object getPincode() {
            return pincode;
        }

        public void setPincode(Object pincode) {
            this.pincode = pincode;
        }

        public List<Object> getAppointments() {
            return appointments;
        }

        public void setAppointments(List<Object> appointments) {
            this.appointments = appointments;
        }

        public List<Object> getAppointments1() {
            return appointments1;
        }

        public void setAppointments1(List<Object> appointments1) {
            this.appointments1 = appointments1;
        }

        public List<Object> getLabTestReports() {
            return labTestReports;
        }

        public void setLabTestReports(List<Object> labTestReports) {
            this.labTestReports = labTestReports;
        }

        public List<Object> getLifestylePlans() {
            return lifestylePlans;
        }

        public void setLifestylePlans(List<Object> lifestylePlans) {
            this.lifestylePlans = lifestylePlans;
        }

        public List<Object> getReminders() {
            return reminders;
        }

        public void setReminders(List<Object> reminders) {
            this.reminders = reminders;
        }

        public List<Object> getScheduledStatus() {
            return scheduledStatus;
        }

        public void setScheduledStatus(List<Object> scheduledStatus) {
            this.scheduledStatus = scheduledStatus;
        }

        public List<Object> getTUsersLabTest() {
            return tUsersLabTest;
        }

        public void setTUsersLabTest(List<Object> tUsersLabTest) {
            this.tUsersLabTest = tUsersLabTest;
        }

        public List<Object> getTUsersRescore() {
            return tUsersRescore;
        }

        public void setTUsersRescore(List<Object> tUsersRescore) {
            this.tUsersRescore = tUsersRescore;
        }

        public List<Object> getTQReeworkerAns() {
            return tQReeworkerAns;
        }

        public void setTQReeworkerAns(List<Object> tQReeworkerAns) {
            this.tQReeworkerAns = tQReeworkerAns;
        }

        public List<Object> getUserActivities() {
            return userActivities;
        }

        public void setUserActivities(List<Object> userActivities) {
            this.userActivities = userActivities;
        }

        public List<Object> getUserTestSchedules() {
            return userTestSchedules;
        }

        public void setUserTestSchedules(List<Object> userTestSchedules) {
            this.userTestSchedules = userTestSchedules;
        }

        public List<Object> getUserSubscriptions() {
            return userSubscriptions;
        }

        public void setUserSubscriptions(List<Object> userSubscriptions) {
            this.userSubscriptions = userSubscriptions;
        }

        public List<Object> getUserTestSchedules1() {
            return userTestSchedules1;
        }

        public void setUserTestSchedules1(List<Object> userTestSchedules1) {
            this.userTestSchedules1 = userTestSchedules1;
        }

    }
}
