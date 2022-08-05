package com.shamrock.reework.activity.BloodTestModule.activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBloodTestReportRes {
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<DataList> data = null;

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

    public List<DataList> getData() {
        return data;
    }

    public void setData(List<DataList> data) {
        this.data = data;
    }



    public class DataList {
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("Scheduletime")
        @Expose
        private String scheduletime;
        @SerializedName("Scheduledate")
        @Expose
        private String scheduledate;
        @SerializedName("Scheduled_By_UserID")
        @Expose
        private Integer scheduledByUserID;
        @SerializedName("ScheduleID")
        @Expose
        private Integer scheduleID;
        @SerializedName("Select_Address")
        @Expose
        private Integer selectAddress;
        @SerializedName("HasReportGenerated")
        @Expose
        private Boolean hasReportGenerated;
        @SerializedName("CanEdit")
        @Expose
        private Boolean canEdit;
        @SerializedName("New_Address")
        @Expose
        private String newAddress;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getScheduletime() {
            return scheduletime;
        }

        public void setScheduletime(String scheduletime) {
            this.scheduletime = scheduletime;
        }

        public String getScheduledate() {
            return scheduledate;
        }

        public void setScheduledate(String scheduledate) {
            this.scheduledate = scheduledate;
        }

        public Integer getScheduledByUserID() {
            return scheduledByUserID;
        }

        public void setScheduledByUserID(Integer scheduledByUserID) {
            this.scheduledByUserID = scheduledByUserID;
        }

        public Integer getScheduleID() {
            return scheduleID;
        }

        public void setScheduleID(Integer scheduleID) {
            this.scheduleID = scheduleID;
        }

        public Integer getSelectAddress() {
            return selectAddress;
        }

        public void setSelectAddress(Integer selectAddress) {
            this.selectAddress = selectAddress;
        }

        public Boolean getHasReportGenerated() {
            return hasReportGenerated;
        }

        public void setHasReportGenerated(Boolean hasReportGenerated) {
            this.hasReportGenerated = hasReportGenerated;
        }

        public Boolean getCanEdit() {
            return canEdit;
        }

        public void setCanEdit(Boolean canEdit) {
            this.canEdit = canEdit;
        }

        public String getNewAddress() {
            return newAddress;
        }

        public void setNewAddress(String newAddress) {
            this.newAddress = newAddress;
        }
    }

}
