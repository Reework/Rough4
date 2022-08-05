package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetAllAppointmentResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private ArrayList<AppointmentData> data = null;

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

    public ArrayList<AppointmentData> getData() {
        return data;
    }

    public void setData(ArrayList<AppointmentData> data) {
        this.data = data;
    }

    public static class AppointmentData implements Serializable
    {
        @SerializedName("ApptID")
        @Expose
        private Integer apptID;

        @SerializedName("Date")
        @Expose
        private String date;

        @SerializedName("Time")
        @Expose
        private String time;

        @SerializedName("Description")
        @Expose
        private String description;

        @SerializedName("IsCancelled")
        @Expose
        private Boolean isCancelled;

        @SerializedName("UserID")
        @Expose
        private Integer userID;

        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;

        @SerializedName("DeletedOn")
        @Expose
        private Object deletedOn;

        @SerializedName("Comment")
        @Expose
        private Object comment;

        @SerializedName("EndTime")
        @Expose
        private String endTime;

        @SerializedName("ReeCoachId")
        @Expose
        private Integer reeCoachId;



        @SerializedName("VenuType")
        @Expose
        private Integer VenuType;


        @SerializedName("Fees")
        @Expose
        private int Fees;


        @SerializedName("StatusID")
        @Expose
        private int StatusID;


        @SerializedName("Fullname")
        @Expose
        private String Fullname;


        public int getStatusID() {
            return StatusID;
        }

        public void setStatusID(int statusID) {
            StatusID = statusID;
        }

        public String getFullname() {
            return Fullname;
        }

        public void setFullname(String fullname) {
            Fullname = fullname;
        }

        public int getFees() {
            return Fees;
        }

        public void setFees(int fees) {
            Fees = fees;
        }

        public Integer getVenuType() {
            return VenuType;
        }

        public void setVenuType(Integer venuType) {
            VenuType = venuType;
        }

        public Integer getApptID() {
            return apptID;
        }

        public void setApptID(Integer apptID) {
            this.apptID = apptID;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getIsCancelled() {
            return isCancelled;
        }

        public void setIsCancelled(Boolean isCancelled) {
            this.isCancelled = isCancelled;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
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

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Integer getReeCoachId() {
            return reeCoachId;
        }

        public void setReeCoachId(Integer reeCoachId) {
            this.reeCoachId = reeCoachId;
        }

    }
}
