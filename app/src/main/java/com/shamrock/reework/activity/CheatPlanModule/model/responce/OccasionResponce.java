package com.shamrock.reework.activity.CheatPlanModule.model.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OccasionResponce {
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

        @SerializedName("OccasionId")
        @Expose
        private Integer occasionId;
        @SerializedName("Occasion")
        @Expose
        private String occasion;

        public Integer getOccasionId() {
            return occasionId;
        }

        public void setOccasionId(Integer occasionId) {
            this.occasionId = occasionId;
        }

        public String getOccasion() {
            return occasion;
        }

        public void setOccasion(String occasion) {
            this.occasion = occasion;
        }

    }
}
