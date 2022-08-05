package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InterpretationResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<Data> data = null;

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

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data
    {
        @SerializedName("BodyWeight_CurrentState")
        @Expose
        private Integer bodyWeightCurrentState;
        @SerializedName("BodyWeight_IdealState")
        @Expose
        private Integer bodyWeightIdealState;
        @SerializedName("BMI_CurrentState")
        @Expose
        private Double bMICurrentState;
        @SerializedName("BMI_IdealState")
        @Expose
        private Double bMIIdealState;
        @SerializedName("ActivityLevel_CurrentState")
        @Expose
        private String activityLevelCurrentState;
        @SerializedName("ActivityLevel_IdealState")
        @Expose
        private String activityLevelIdealState;

        public Integer getBodyWeightCurrentState() {
            return bodyWeightCurrentState;
        }

        public void setBodyWeightCurrentState(Integer bodyWeightCurrentState) {
            this.bodyWeightCurrentState = bodyWeightCurrentState;
        }

        public Integer getBodyWeightIdealState() {
            return bodyWeightIdealState;
        }

        public void setBodyWeightIdealState(Integer bodyWeightIdealState) {
            this.bodyWeightIdealState = bodyWeightIdealState;
        }

        public Double getBMICurrentState() {
            return bMICurrentState;
        }

        public void setBMICurrentState(Double bMICurrentState) {
            this.bMICurrentState = bMICurrentState;
        }

        public Double getBMIIdealState() {
            return bMIIdealState;
        }

        public void setBMIIdealState(Double bMIIdealState) {
            this.bMIIdealState = bMIIdealState;
        }

        public String getActivityLevelCurrentState() {
            return activityLevelCurrentState;
        }

        public void setActivityLevelCurrentState(String activityLevelCurrentState) {
            this.activityLevelCurrentState = activityLevelCurrentState;
        }

        public String getActivityLevelIdealState() {
            return activityLevelIdealState;
        }

        public void setActivityLevelIdealState(String activityLevelIdealState) {
            this.activityLevelIdealState = activityLevelIdealState;
        }
    }
}
