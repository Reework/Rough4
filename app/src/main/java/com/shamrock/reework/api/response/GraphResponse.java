package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphResponse {
    /**
     * Code : 1
     * Message : Ok
     * Data : [{"weight":95,"month":"Oct-2018"},{"weight":92,"month":"Nov-2018"},{"weight":86,"month":"Dec-2018"},{"weight":84,"month":"Jan-2019"},{"weight":79,"month":"Feb-2019"}]
     */

    @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Data")
    private List<GraphEntityData> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<GraphEntityData> getData() {
        return Data;
    }

    public void setData(List<GraphEntityData> Data) {
        this.Data = Data;
    }

    public static class GraphEntityData
    {
        /**
         * weight : 95
         * month : Oct-2018
         */

        @SerializedName("Weight")
        private double weight;
        @SerializedName("healthdate")
        private String month;

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
