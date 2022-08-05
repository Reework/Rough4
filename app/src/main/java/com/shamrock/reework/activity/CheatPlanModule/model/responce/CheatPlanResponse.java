package com.shamrock.reework.activity.CheatPlanModule.model.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheatPlanResponse {

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

        @SerializedName("CheatId")
        @Expose
        private Integer cheatId;
        @SerializedName("CheatPlan")
        @Expose
        private String cheatPlan;
        @SerializedName("WishToEatId")
        @Expose
        private Integer wishToEatId;

        public Integer getCheatId() {
            return cheatId;
        }

        public void setCheatId(Integer cheatId) {
            this.cheatId = cheatId;
        }

        public String getCheatPlan() {
            return cheatPlan;
        }

        public void setCheatPlan(String cheatPlan) {
            this.cheatPlan = cheatPlan;
        }

        public Integer getWishToEatId() {
            return wishToEatId;
        }

        public void setWishToEatId(Integer wishToEatId) {
            this.wishToEatId = wishToEatId;
        }
    }
}
