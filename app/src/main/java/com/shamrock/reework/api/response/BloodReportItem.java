package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

public class BloodReportItem
{
    @SerializedName("UploadDate")
    private String UploadDate;

    @SerializedName("ReportLink")
    private String ReportLink;

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String UploadDate) {
        this.UploadDate = UploadDate;
    }

    public String getReportLink() {
        return ReportLink;
    }

    public void setReportLink(String ReportLink) {
        this.ReportLink = ReportLink;
    }
}
