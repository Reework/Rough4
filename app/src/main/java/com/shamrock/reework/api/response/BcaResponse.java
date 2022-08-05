package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BcaResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<BcaReport> data = null;

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

    public ArrayList<BcaReport> getData() {
        return data;
    }

    public void setData(ArrayList<BcaReport> data)
    {
        this.data = data;
    }

    public class BcaReport
    {
        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Userid")
        @Expose
        private Integer userid;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Mobileno")
        @Expose
        private String mobileno;
        @SerializedName("BodyFat")
        @Expose
        private Integer bodyFat;
        @SerializedName("VisceralBodyFat")
        @Expose
        private Integer visceralBodyFat;
        @SerializedName("SkeletalMuscle")
        @Expose
        private Integer skeletalMuscle;
        @SerializedName("TotalBodyWater")
        @Expose
        private Integer totalBodyWater;
        @SerializedName("BMI")
        @Expose
        private Integer bMI;
        @SerializedName("BMR")
        @Expose
        private Integer bMR;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;
        @SerializedName("ModifiedBy")
        @Expose
        private Integer modifiedBy;
        @SerializedName("ModifiedDate")
        @Expose
        private String modifiedDate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public Integer getBodyFat() {
            return bodyFat;
        }

        public void setBodyFat(Integer bodyFat) {
            this.bodyFat = bodyFat;
        }

        public Integer getVisceralBodyFat() {
            return visceralBodyFat;
        }

        public void setVisceralBodyFat(Integer visceralBodyFat) {
            this.visceralBodyFat = visceralBodyFat;
        }

        public Integer getSkeletalMuscle() {
            return skeletalMuscle;
        }

        public void setSkeletalMuscle(Integer skeletalMuscle) {
            this.skeletalMuscle = skeletalMuscle;
        }

        public Integer getTotalBodyWater() {
            return totalBodyWater;
        }

        public void setTotalBodyWater(Integer totalBodyWater) {
            this.totalBodyWater = totalBodyWater;
        }

        public Integer getBMI() {
            return bMI;
        }

        public void setBMI(Integer bMI) {
            this.bMI = bMI;
        }

        public Integer getBMR() {
            return bMR;
        }

        public void setBMR(Integer bMR) {
            this.bMR = bMR;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Integer getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Integer modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }
    }
}
