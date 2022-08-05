package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodAnalysisResponce {
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("Protien")
        @Expose
        private Integer protien;
        @SerializedName("Fats")
        @Expose
        private Integer fats;
        @SerializedName("Carbohydrates")
        @Expose
        private Integer carbohydrates;
        @SerializedName("Fibre")
        @Expose
        private Integer fibre;

        public Integer getProtien() {
            return protien;
        }

        public void setProtien(Integer protien) {
            this.protien = protien;
        }

        public Integer getFats() {
            return fats;
        }

        public void setFats(Integer fats) {
            this.fats = fats;
        }

        public Integer getCarbohydrates() {
            return carbohydrates;
        }

        public void setCarbohydrates(Integer carbohydrates) {
            this.carbohydrates = carbohydrates;
        }

        public Integer getFibre() {
            return fibre;
        }

        public void setFibre(Integer fibre) {
            this.fibre = fibre;
        }

    }
}
