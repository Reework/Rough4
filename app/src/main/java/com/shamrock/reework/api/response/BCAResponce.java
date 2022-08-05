package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BCAResponce {

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

        private String FilePath;
        @SerializedName("UserID")
        @Expose
        private Integer userID;
        @SerializedName("Reportdate")
        @Expose
        private String createdOn;

        private String CollectedDate;

//        @SerializedName("TestDetails")
//        @Expose
//        private List<TestDetails> TestDetails = null;
        @SerializedName("ReportType")
        @Expose
        private String reportType;

        @SerializedName("TestList")
        @Expose
        private List<TestList> TestList;

        public List<com.shamrock.reework.api.response.TestList> getTestList() {
            return TestList;
        }

        public void setTestList(List<com.shamrock.reework.api.response.TestList> testList) {
            TestList = testList;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getCollectedDate() {
            return CollectedDate;
        }

        public void setCollectedDate(String collectedDate) {
            CollectedDate = collectedDate;
        }

        //        public List<LabReportDetail> getLabReportDetails() {
//            return labReportDetails;
//        }
//
//        public void setLabReportDetails(List<LabReportDetail> labReportDetails) {
//            this.labReportDetails = labReportDetails;
//        }


//        public List<TestDetails> getTestDetails() {
//            return TestDetails;
//        }
//
//        public void setTestDetails(List<TestDetails> testDetails) {
//            TestDetails = testDetails;
//        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

//        public String getPDFPath() {
//            return PDFPath;
//        }
//
//        public void setPDFPath(String PDFPath) {
//            this.PDFPath = PDFPath;
//        }


        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String filePath) {
            FilePath = filePath;
        }
    }

    public class TestDetails {

        @SerializedName("ReportType")
        @Expose
        private String reportType;
        @SerializedName("TestId")
        @Expose
        private Integer testId;
        @SerializedName("TestName")
        @Expose
        private String testName;
        @SerializedName("LabTestValue")
        @Expose
        private String testValue;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("NormalRange")
        @Expose
        private String normalRange;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("FilePath")
        @Expose
        private String filePath;

//        @SerializedName("Score")
//        @Expose
//        private String score;

        @SerializedName("Remark")
        @Expose
        private String Remark;

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

//        public String getScore() {
//            return score;
//        }
//
//        public void setScore(String score) {
//            this.score = score;
//        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public Integer getTestId() {
            return testId;
        }

        public void setTestId(Integer testId) {
            this.testId = testId;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getTestValue() {
            return testValue;
        }

        public void setTestValue(String testValue) {
            this.testValue = testValue;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getNormalRange() {
            return normalRange;
        }

        public void setNormalRange(String normalRange) {
            this.normalRange = normalRange;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

    }
}
