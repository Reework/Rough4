package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TipsResponce {
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

        @SerializedName("TipsId")
        @Expose
        private Integer tipsId;
        @SerializedName("SleepTips")
        @Expose
        private String sleepTips;
        @SerializedName("MindTips")
        @Expose
        private String mindTips;


        public Datum(Integer tipsId, String sleepTips, String mindTips) {
            this.tipsId = tipsId;
            this.sleepTips = sleepTips;
            this.mindTips = mindTips;
        }

        public Integer getTipsId() {
            return tipsId;
        }

        public void setTipsId(Integer tipsId) {
            this.tipsId = tipsId;
        }

        public String getSleepTips() {
            return sleepTips;
        }

        public void setSleepTips(String sleepTips) {
            this.sleepTips = sleepTips;
        }

        public String getMindTips() {
            return mindTips;
        }

        public void setMindTips(String mindTips) {
            this.mindTips = mindTips;
        }

    }


}
