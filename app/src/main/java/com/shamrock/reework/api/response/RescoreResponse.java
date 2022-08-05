package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RescoreResponse {
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<RescoreData> data = null;

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

    public ArrayList<RescoreData> getData() {
        return data;
    }

    public void setData(ArrayList<RescoreData> data) {
        this.data = data;
    }

    public class RescoreData implements Serializable {
        @SerializedName("lstMessage")
        @Expose
        private ArrayList<String> lstMessage = null;
        @SerializedName("IdealBMI")
        @Expose
        private String idealBMI;
        @SerializedName("ActualBMI")
        @Expose
        private String actualBMI;
        @SerializedName("ActualBodyWeight")
        @Expose
        private String actualBodyWeight;
        @SerializedName("IdealBodyWeight")
        @Expose
        private String idealBodyWeight;
        @SerializedName("ActualCalorieIntake")
        @Expose
        private String actuialCalorieIntake;
        @SerializedName("IdealCalorieIntake")
        @Expose
        private String idealCalorieIntake;
        @SerializedName("ActualSleep")
        @Expose
        private String actualSleep;
        @SerializedName("IdealSleep")
        @Expose
        private String idealSleep;
        @SerializedName("ActivityLevel")
        @Expose
        private Integer activityLevel;
        @SerializedName("isSmoke")
        @Expose
        private Boolean isSmoke;
        @SerializedName("MedicalState")
        @Expose
        private String medicalState;
        @SerializedName("reScore")
        @Expose
        private float reScore;


        @SerializedName("ReescoreInPercent")
        @Expose
        private int ReescoreInPercent;


        @SerializedName("FirstREEscoreInPercent")//FirstREEscore
        @Expose
        private float FirstREEscoreInPercent;


        @SerializedName("TodaysREEscoreInPercent") //TodaysREEscore
        @Expose
        private float TodaysREEscoreInPercent;

        @SerializedName("FutureREEscoreInPercent")//FutureREEscore
        @Expose
        private float FutureREEscoreInPercent;

        public float getFirstREEscoreInPercent() {
            return FirstREEscoreInPercent;
        }

        public void setFirstREEscoreInPercent(float firstREEscoreInPercent) {
            FirstREEscoreInPercent = firstREEscoreInPercent;
        }

        public float getTodaysREEscoreInPercent() {
            return TodaysREEscoreInPercent;
        }

        public void setTodaysREEscoreInPercent(float todaysREEscoreInPercent) {
            TodaysREEscoreInPercent = todaysREEscoreInPercent;
        }

        public float getFutureREEscoreInPercent() {
            return FutureREEscoreInPercent;
        }

        public void setFutureREEscoreInPercent(float futureREEscoreInPercent) {
            FutureREEscoreInPercent = futureREEscoreInPercent;
        }

      /*  @SerializedName("FirstREEscore")
        @Expose
        private float FirstREEscore;


        @SerializedName("TodaysREEscore")
        @Expose
        private float TodaysREEscore;

        @SerializedName("FutureREEscore")
        @Expose
        private float FutureREEscore;

        public float getFirstREEscore() {
            return FirstREEscore;
        }

        public float getFutureREEscore() {
            return FutureREEscore;
        }

        public void setFutureREEscore(float futureREEscore) {
            FutureREEscore = futureREEscore;
        }

        public void setFirstREEscore(float firstREEscore) {
            FirstREEscore = firstREEscore;
        }

        public float getTodaysREEscore() {
            return TodaysREEscore;
        }

        public void setTodaysREEscore(float todaysREEscore) {
            TodaysREEscore = todaysREEscore;
        }

        public void setTodaysREEscore(int todaysREEscore) {
            TodaysREEscore = todaysREEscore;
        }*/

        public int getReescoreInPercent() {
            return ReescoreInPercent;
        }

        public void setReescoreInPercent(int reescoreInPercent) {
            ReescoreInPercent = reescoreInPercent;
        }

        public ArrayList<String> getLstMessage() {
            return lstMessage;
        }

        public void setLstMessage(ArrayList<String> lstMessage) {
            this.lstMessage = lstMessage;
        }

        public String getIdealBMI() {
            return idealBMI;
        }

        public void setIdealBMI(String idealBMI) {
            this.idealBMI = idealBMI;
        }

        public String getActualBMI() {
            return actualBMI;
        }

        public void setActualBMI(String actualBMI) {
            this.actualBMI = actualBMI;
        }

        public String getActualBodyWeight() {
            return actualBodyWeight;
        }

        public void setActualBodyWeight(String actualBodyWeight) {
            this.actualBodyWeight = actualBodyWeight;
        }

        public String getIdealBodyWeight() {
            return idealBodyWeight;
        }

        public void setIdealBodyWeight(String idealBodyWeight) {
            this.idealBodyWeight = idealBodyWeight;
        }

        public String getActuialCalorieIntake() {
            return actuialCalorieIntake;
        }

        public void setActuialCalorieIntake(String actuialCalorieIntake) {
            this.actuialCalorieIntake = actuialCalorieIntake;
        }

        public String getIdealCalorieIntake() {
            return idealCalorieIntake;
        }

        public void setIdealCalorieIntake(String idealCalorieIntake) {
            this.idealCalorieIntake = idealCalorieIntake;
        }

        public String getActualSleep() {
            return actualSleep;
        }

        public void setActualSleep(String actualSleep) {
            this.actualSleep = actualSleep;
        }

        public String getIdealSleep() {
            return idealSleep;
        }

        public void setIdealSleep(String idealSleep) {
            this.idealSleep = idealSleep;
        }

        public Integer getActivityLevel() {
            return activityLevel;
        }

        public void setActivityLevel(Integer activityLevel) {
            this.activityLevel = activityLevel;
        }

        public Boolean getIsSmoke() {
            return isSmoke;
        }

        public void setIsSmoke(Boolean isSmoke) {
            this.isSmoke = isSmoke;
        }

        public String getMedicalState() {
            return medicalState;
        }

        public void setMedicalState(String medicalState) {
            this.medicalState = medicalState;
        }


        public Boolean getSmoke() {
            return isSmoke;
        }

        public void setSmoke(Boolean smoke) {
            isSmoke = smoke;
        }

        public float getReScore() {
            return reScore;
        }

        public void setReScore(float reScore) {
            this.reScore = reScore;
        }
    }
}
