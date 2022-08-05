package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeightMonthsResponse
{
    /**
     * Code : 1
     * Message : all previous data for user
     * MonthData : [{"healthdate":"Oct-2018"},{"healthdate":"Oct-2018"}]
     * GraphData : [{"healthdate":"Oct-2018","Weight":66},{"healthdate":"Oct-2018","Weight":55},{"healthdate":"Oct-2018","Weight":55},{"healthdate":"Oct-2018","Weight":48},{"healthdate":"Oct-2018","Weight":48},{"healthdate":"Oct-2018","Weight":51},{"healthdate":"Oct-2018","Weight":51},{"healthdate":"Oct-2018","Weight":53}]
     * CurrentWeight : 53
     * IdealWeight : 44
     */

    @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    @SerializedName("CurrentWeight")
    private double CurrentWeight;
    @SerializedName("IdealWeight")
    private double IdealWeight;
    @SerializedName("MonthData")
    private List<MonthDataResponse> MonthData;
    @SerializedName("GraphData")
    private List<GraphResponse.GraphEntityData> GraphData;

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

    public double getCurrentWeight() {
        return CurrentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        CurrentWeight = currentWeight;
    }

    public double getIdealWeight() {
        return IdealWeight;
    }

    public void setIdealWeight(double idealWeight) {
        IdealWeight = idealWeight;
    }

    public List<MonthDataResponse> getMonthData() {
        return MonthData;
    }

    public void setMonthData(List<MonthDataResponse> MonthData) {
        this.MonthData = MonthData;
    }

    public List<GraphResponse.GraphEntityData> getGraphData() {
        return GraphData;
    }

    public void setGraphData(List<GraphResponse.GraphEntityData> GraphData) {
        this.GraphData = GraphData;
    }

    public static class MonthDataResponse
    {
        /**
         * healthdate : Oct-2018
         */

       /* @SerializedName("healthdate")
        private String healthdate;

        public String getHealthdate() {
            return healthdate;
        }

        public void setHealthdate(String healthdate) {
            this.healthdate = healthdate;
        }*/

        @SerializedName("healthdate")
        @Expose
        private String healthdate;
        @SerializedName("Weight")
        @Expose
        private Integer weight;

        public String getHealthdate() {
            return healthdate;
        }

        public void setHealthdate(String healthdate) {
            this.healthdate = healthdate;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

    }

//    public static class GraphDataResponse {
//        /**
//         * healthdate : Oct-2018
//         * Weight : 66
//         */
//
//        private String healthdate;
//        private int Weight;
//
//        public String getHealthdate() {
//            return healthdate;
//        }
//
//        public void setHealthdate(String healthdate) {
//            this.healthdate = healthdate;
//        }
//
//        public int getWeight() {
//            return Weight;
//        }
//
//        public void setWeight(int Weight) {
//            this.Weight = Weight;
//        }
//    }


//    /**
//     * Code : 1
//     * Message : Month Date list
//     * Months : ["Oct-2018","Oct-2018","Oct-2018","Oct-2018","Oct-2018","Oct-2018","Oct-2018"]
//     */
//
//    @SerializedName("Code")
//    private int Code;
//    @SerializedName("Message")
//    private String Message;
//    @SerializedName("CurrentWeight")
//    private String CurrentWeight;
//    @SerializedName("IdealWeight")
//    private String IdealWeight;
//    @SerializedName("MonthData")
//    private List<String> Months;
//    @SerializedName("GraphData")
//    private List<GraphDataItem> graphDataItems;
//
//    public int getCode() {
//        return Code;
//    }
//
//    public void setCode(int Code) {
//        this.Code = Code;
//    }
//
//    public String getMessage() {
//        return Message;
//    }
//
//    public void setMessage(String Message) {
//        this.Message = Message;
//    }
//
//    public List<String> getMonths() {
//        return Months;
//    }
//
//    public void setMonths(List<String> months) {
//        Months = months;
//    }
//
//    private class GraphDataItem {
//        @SerializedName("healthdate")
//        private String MonthName;
//        @SerializedName("Weight")
//        private String Weight;
//
//        public String getMonthName() {
//            return MonthName;
//        }
//
//        public void setMonthName(String monthName) {
//            MonthName = monthName;
//        }
//
//        public String getWeight() {
//            return Weight;
//        }
//
//        public void setWeight(String weight) {
//            Weight = weight;
//        }
//    }
}
