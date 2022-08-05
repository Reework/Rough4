package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

public class HealthParameterResponse
{


    /**
     * Code : 1
     * Message : Your Ideal Body Weight (IBW) calculated
     * Data : {"Weight":82,"Ideal_Weight":75,"Healthdate":"19/10/2018"}
     */

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    private DataResponse Data;

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

    public DataResponse getData() {
        return Data;
    }

    public void setData(DataResponse Data) {
        this.Data = Data;
    }

    public static class DataResponse
    {
        /**
         * Weight : 82
         * Ideal_Weight : 75
         * Healthdate : 19/10/2018
         */

        @SerializedName("Weight")
        private double Weight;

        @SerializedName("Ideal_Weight")
        private double Ideal_Weight;

        @SerializedName("Healthdate")
        private String Healthdate;

        public double getWeight() {
            return Weight;
        }

        public void setWeight(double weight) {
            Weight = weight;
        }

        public double getIdeal_Weight() {
            return Ideal_Weight;
        }

        public void setIdeal_Weight(double ideal_Weight) {
            Ideal_Weight = ideal_Weight;
        }

        public String getHealthdate() {
            return Healthdate;
        }

        public void setHealthdate(String Healthdate) {
            this.Healthdate = Healthdate;
        }
    }
}
