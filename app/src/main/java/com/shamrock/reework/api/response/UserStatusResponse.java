package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserStatusResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private UserData data;

//    @SerializedName("isEmailVerified")
//    @Expose
//    private boolean isEmailVerified;
//
//    public boolean isEmailVerified() {
//        return isEmailVerified;
//    }
//
//    public void setEmailVerified(boolean emailVerified) {
//        isEmailVerified = emailVerified;
//    }

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

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public static class UserData
    {

//        "IsPathoRequire": true,
//                "IsReecoachRequire": true,
//                "PathologistId": 3,
//                "ReecoachId": 3037,

        @SerializedName("IsPathoRequire")
        @Expose
        private boolean IsPathoRequire;


        @SerializedName("IsReecoachRequire")
        @Expose
        private boolean IsReecoachRequire;


        @SerializedName("ReecoachId")
        @Expose
        private int ReecoachId;

        @SerializedName("PathologistId")
        @Expose
        private int PathologistId;





        @SerializedName("IsAppliedBloodTest")
        @Expose
        private String IsAppliedBloodTest;



        @SerializedName("StatusId")
        @Expose
        private Integer statusId;

        public String getIsAppliedBloodTest() {
            return IsAppliedBloodTest;
        }

        public void setIsAppliedBloodTest(String isAppliedBloodTest) {
            IsAppliedBloodTest = isAppliedBloodTest;
        }

        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("PlanID")
        @Expose
        private Integer planID;
        @SerializedName("IsTrial")
        private String IsTrail;


        public boolean isPathoRequire() {
            return IsPathoRequire;
        }

        public void setPathoRequire(boolean pathoRequire) {
            IsPathoRequire = pathoRequire;
        }

        public boolean isReecoachRequire() {
            return IsReecoachRequire;
        }

        public void setReecoachRequire(boolean reecoachRequire) {
            IsReecoachRequire = reecoachRequire;
        }

        public int getReecoachId() {
            return ReecoachId;
        }

        public void setReecoachId(int reecoachId) {
            ReecoachId = reecoachId;
        }

        public int getPathologistId() {
            return PathologistId;
        }

        public void setPathologistId(int pathologistId) {
            PathologistId = pathologistId;
        }

        public String getIsTrail() {
            return IsTrail;
        }

        public void setIsTrail(String isTrail) {
            IsTrail = isTrail;
        }

        public Integer getStatusId() {
            return statusId;
        }

        public void setStatusId(Integer statusId) {
            this.statusId = statusId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getPlanID() {
            return planID;
        }

        public void setPlanID(Integer planID) {
            this.planID = planID;
        }
    }
}
