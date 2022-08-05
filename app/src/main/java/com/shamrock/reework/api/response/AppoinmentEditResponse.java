package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppoinmentEditResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private AppoinmentEditResponseData data;

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

    public AppoinmentEditResponseData getData() {
        return data;
    }

    public void setData(AppoinmentEditResponseData data) {
        this.data = data;
    }

    public static class AppoinmentEditResponseData
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
        private Object description;
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
        @SerializedName("DeletedBy")
        @Expose
        private Object deletedBy;
        @SerializedName("StatusID")
        @Expose
        private Integer statusID;

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

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
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

        public Object getDeletedBy() {
            return deletedBy;
        }

        public void setDeletedBy(Object deletedBy) {
            this.deletedBy = deletedBy;
        }

        public Integer getStatusID() {
            return statusID;
        }

        public void setStatusID(Integer statusID) {
            this.statusID = statusID;
        }
    }

}
