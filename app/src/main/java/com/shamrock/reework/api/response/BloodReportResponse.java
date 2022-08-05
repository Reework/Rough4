package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BloodReportResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<BloodReportData> data = null;

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

    public ArrayList<BloodReportData> getData() {
        return data;
    }

    public void setData(ArrayList<BloodReportData> data) {
        this.data = data;
    }

    public class BloodReportData
    {
        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Userid")
        @Expose
        private Integer userid;
        @SerializedName("MobileNo")
        @Expose
        private String mobileNo;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("FastingSugar")
        @Expose
        private String fastingSugar;
        @SerializedName("BP")
        @Expose
        private String bP;
        @SerializedName("PostPrandialSugar")
        @Expose
        private String postPrandialSugar;
        @SerializedName("HBA1c")
        @Expose
        private String hBA1c;
        @SerializedName("Creatinine")
        @Expose
        private String creatinine;
        @SerializedName("urea")
        @Expose
        private String urea;
        @SerializedName("T3")
        @Expose
        private String t3;
        @SerializedName("Haemoglobin")
        @Expose
        private String haemoglobin;
        @SerializedName("Potassium")
        @Expose
        private String potassium;
        @SerializedName("Sodium")
        @Expose
        private String sodium;
        @SerializedName("Calcium")
        @Expose
        private String calcium;
        @SerializedName("Magnesium")
        @Expose
        private String magnesium;
        @SerializedName("Phosphorous")
        @Expose
        private String phosphorous;
        @SerializedName("VitB12")
        @Expose
        private String vitB12;
        @SerializedName("FolicAcid")
        @Expose
        private String folicAcid;
        @SerializedName("Homocysteine")
        @Expose
        private String homocysteine;
        @SerializedName("VitD")
        @Expose
        private String vitD;
        @SerializedName("TotalCholesterol")
        @Expose
        private String totalCholesterol;
        @SerializedName("Triglycerides")
        @Expose
        private String triglycerides;
        @SerializedName("HDLCholesterol")
        @Expose
        private String hDLCholesterol;
        @SerializedName("LDLCholesterol")
        @Expose
        private String lDLCholesterol;
        @SerializedName("Uricacid")
        @Expose
        private String uricacid;
        @SerializedName("T4")
        @Expose
        private String t4;
        @SerializedName("TSH")
        @Expose
        private String tSH;
        @SerializedName("SHBG")
        @Expose
        private String sHBG;
        @SerializedName("ProteinTotal")
        @Expose
        private String proteinTotal;
        @SerializedName("Albumin")
        @Expose
        private String albumin;
        @SerializedName("BilirubinTotal")
        @Expose
        private String bilirubinTotal;
        @SerializedName("SGOT")
        @Expose
        private String sGOT;
        @SerializedName("SGPT")
        @Expose
        private String sGPT;
        @SerializedName("CreactiveProtein")
        @Expose
        private String creactiveProtein;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;
        @SerializedName("ModifiedBy")
        @Expose
        private Integer modifiedBy;
        @SerializedName("ModifiedDate")
        @Expose
        private String modifiedDate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFastingSugar() {
            return fastingSugar;
        }

        public void setFastingSugar(String fastingSugar) {
            this.fastingSugar = fastingSugar;
        }

        public String getBP() {
            return bP;
        }

        public void setBP(String bP) {
            this.bP = bP;
        }

        public String getPostPrandialSugar() {
            return postPrandialSugar;
        }

        public void setPostPrandialSugar(String postPrandialSugar) {
            this.postPrandialSugar = postPrandialSugar;
        }

        public String getHBA1c() {
            return hBA1c;
        }

        public void setHBA1c(String hBA1c) {
            this.hBA1c = hBA1c;
        }

        public String getCreatinine() {
            return creatinine;
        }

        public void setCreatinine(String creatinine) {
            this.creatinine = creatinine;
        }

        public String getUrea() {
            return urea;
        }

        public void setUrea(String urea) {
            this.urea = urea;
        }

        public String getT3() {
            return t3;
        }

        public void setT3(String t3) {
            this.t3 = t3;
        }

        public String getHaemoglobin() {
            return haemoglobin;
        }

        public void setHaemoglobin(String haemoglobin) {
            this.haemoglobin = haemoglobin;
        }

        public String getPotassium() {
            return potassium;
        }

        public void setPotassium(String potassium) {
            this.potassium = potassium;
        }

        public String getSodium() {
            return sodium;
        }

        public void setSodium(String sodium) {
            this.sodium = sodium;
        }

        public String getCalcium() {
            return calcium;
        }

        public void setCalcium(String calcium) {
            this.calcium = calcium;
        }

        public String getMagnesium() {
            return magnesium;
        }

        public void setMagnesium(String magnesium) {
            this.magnesium = magnesium;
        }

        public String getPhosphorous() {
            return phosphorous;
        }

        public void setPhosphorous(String phosphorous) {
            this.phosphorous = phosphorous;
        }

        public String getVitB12() {
            return vitB12;
        }

        public void setVitB12(String vitB12) {
            this.vitB12 = vitB12;
        }

        public String getFolicAcid() {
            return folicAcid;
        }

        public void setFolicAcid(String folicAcid) {
            this.folicAcid = folicAcid;
        }

        public String getHomocysteine() {
            return homocysteine;
        }

        public void setHomocysteine(String homocysteine) {
            this.homocysteine = homocysteine;
        }

        public String getVitD() {
            return vitD;
        }

        public void setVitD(String vitD) {
            this.vitD = vitD;
        }

        public String getTotalCholesterol() {
            return totalCholesterol;
        }

        public void setTotalCholesterol(String totalCholesterol) {
            this.totalCholesterol = totalCholesterol;
        }

        public String getTriglycerides() {
            return triglycerides;
        }

        public void setTriglycerides(String triglycerides) {
            this.triglycerides = triglycerides;
        }

        public String getHDLCholesterol() {
            return hDLCholesterol;
        }

        public void setHDLCholesterol(String hDLCholesterol) {
            this.hDLCholesterol = hDLCholesterol;
        }

        public String getLDLCholesterol() {
            return lDLCholesterol;
        }

        public void setLDLCholesterol(String lDLCholesterol) {
            this.lDLCholesterol = lDLCholesterol;
        }

        public String getUricacid() {
            return uricacid;
        }

        public void setUricacid(String uricacid) {
            this.uricacid = uricacid;
        }

        public String getT4() {
            return t4;
        }

        public void setT4(String t4) {
            this.t4 = t4;
        }

        public String getTSH() {
            return tSH;
        }

        public void setTSH(String tSH) {
            this.tSH = tSH;
        }

        public String getSHBG() {
            return sHBG;
        }

        public void setSHBG(String sHBG) {
            this.sHBG = sHBG;
        }

        public String getProteinTotal() {
            return proteinTotal;
        }

        public void setProteinTotal(String proteinTotal) {
            this.proteinTotal = proteinTotal;
        }

        public String getAlbumin() {
            return albumin;
        }

        public void setAlbumin(String albumin) {
            this.albumin = albumin;
        }

        public String getBilirubinTotal() {
            return bilirubinTotal;
        }

        public void setBilirubinTotal(String bilirubinTotal) {
            this.bilirubinTotal = bilirubinTotal;
        }

        public String getSGOT() {
            return sGOT;
        }

        public void setSGOT(String sGOT) {
            this.sGOT = sGOT;
        }

        public String getSGPT() {
            return sGPT;
        }

        public void setSGPT(String sGPT) {
            this.sGPT = sGPT;
        }

        public String getCreactiveProtein() {
            return creactiveProtein;
        }

        public void setCreactiveProtein(String creactiveProtein) {
            this.creactiveProtein = creactiveProtein;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Integer getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Integer modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }
    }
}
