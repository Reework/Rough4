package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAllNotificationResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<Notifications> data = null;

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

    public ArrayList<Notifications> getData() {
        return data;
    }

    public void setData(ArrayList<Notifications> data) {
        this.data = data;
    }

    public class Notifications
    {
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("NotificationMessage")
        @Expose
        private String notificationMessage;
        @SerializedName("NotificationDate")
        @Expose
        private String notificationDate;
        @SerializedName("NotificationSubject")
        @Expose
        private Object notificationSubject;
        @SerializedName("UserID")
        @Expose
        private Integer userID;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("IsMailSent")
        @Expose
        private Boolean isMailSent;
        @SerializedName("IsRead")
        @Expose
        private Boolean isRead;
        @SerializedName("IsMail")
        @Expose
        private Boolean isMail;
        @SerializedName("IsNotificationSent")
        @Expose
        private Boolean isNotificationSent;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getNotificationMessage() {
            return notificationMessage;
        }

        public void setNotificationMessage(String notificationMessage) {
            this.notificationMessage = notificationMessage;
        }

        public String getNotificationDate() {
            return notificationDate;
        }

        public void setNotificationDate(String notificationDate) {
            this.notificationDate = notificationDate;
        }

        public Object getNotificationSubject() {
            return notificationSubject;
        }

        public void setNotificationSubject(Object notificationSubject) {
            this.notificationSubject = notificationSubject;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
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

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Boolean getIsMailSent() {
            return isMailSent;
        }

        public void setIsMailSent(Boolean isMailSent) {
            this.isMailSent = isMailSent;
        }

        public Boolean getIsRead() {
            return isRead;
        }

        public void setIsRead(Boolean isRead) {
            this.isRead = isRead;
        }

        public Boolean getIsMail() {
            return isMail;
        }

        public void setIsMail(Boolean isMail) {
            this.isMail = isMail;
        }

        public Boolean getIsNotificationSent() {
            return isNotificationSent;
        }

        public void setIsNotificationSent(Boolean isNotificationSent) {
            this.isNotificationSent = isNotificationSent;
        }
    }
}
