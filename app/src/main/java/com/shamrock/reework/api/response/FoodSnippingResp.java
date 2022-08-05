package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodSnippingResp {
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("UserID")
        @Expose
        private Integer userID;
        @SerializedName("FoodSnappingPath")
        @Expose
        private String foodSnappingPath;
        @SerializedName("UploadedOn")
        @Expose
        private String uploadedOn;

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

        public String getFoodSnappingPath() {
            return foodSnappingPath;
        }

        public void setFoodSnappingPath(String foodSnappingPath) {
            this.foodSnappingPath = foodSnappingPath;
        }

        public String getUploadedOn() {
            return uploadedOn;
        }

        public void setUploadedOn(String uploadedOn) {
            this.uploadedOn = uploadedOn;
        }

    }

}
