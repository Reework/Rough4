package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TempRespForBeforeAfter
{
    @SerializedName("Code")
    @Expose
    private Integer code;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private ArrayList<TempData> data = null;

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

    public ArrayList<TempData> getData() {
        return data;
    }

    public void setData(ArrayList<TempData> data) {
        this.data = data;
    }

    public static class TempData
    {
        @SerializedName("ID")
        @Expose
        private Integer iD;

        @SerializedName("UserID")
        @Expose
        private Integer userID;

        @SerializedName("BeforeFilePath")
        @Expose
        private String beforeFilePath;

        @SerializedName("AfterFilePath")
        @Expose
        private String afterFilePath;

        @SerializedName("UploadedOn")
        @Expose
        private Object uploadedOn;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getBeforeFilePath() {
            return beforeFilePath;
        }

        public void setBeforeFilePath(String beforeFilePath) {
            this.beforeFilePath = beforeFilePath;
        }

        public String getAfterFilePath() {
            return afterFilePath;
        }

        public void setAfterFilePath(String afterFilePath) {
            this.afterFilePath = afterFilePath;
        }

        public Object getUploadedOn() {
            return uploadedOn;
        }

        public void setUploadedOn(Object uploadedOn) {
            this.uploadedOn = uploadedOn;
        }
    }
}
