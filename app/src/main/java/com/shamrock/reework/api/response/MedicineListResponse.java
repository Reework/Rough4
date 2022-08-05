package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.MedicineModule.service.FrequencyList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MedicineListResponse implements Serializable {

    /**
     * Code : 1
     * Message : Medicine list
     * Data : [{"Mymedid":8,"UserID":102,"MedicineId":6,"Frequency":"13","Dosage":"3,4,6"},{"Mymedid":9,"UserID":102,"MedicineId":5,"Frequency":"2","Dosage":"2,3"}]
     */

    @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    private List<DataResponse> Data;

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

    public List<DataResponse> getData() {
        return Data;
    }

    public void setData(List<DataResponse> Data) {
        this.Data = Data;
    }

    public static class DataResponse implements Serializable{
        /**
         * Mymedid : 8
         * UserID : 102
         * MedicineId : 6
         * Frequency : 13
         * Dosage : 3,4,6
         */

        @SerializedName("ImgPath")
        @Expose
        private String ImgPath;


        @SerializedName("ImgName")
        @Expose
        private String ImgName;

        @SerializedName("Mymedid")
        @Expose
        private Integer mymedid;

//        @SerializedName("MedicineId")
//        @Expose
//        private Integer medicineId;

        @SerializedName("MedicineName")
        @Expose
        private String medicineName;

        @SerializedName("Frequency")
        @Expose
        private String frequency;

        @SerializedName("Dosage")
        @Expose
        private String dosage;

        @SerializedName("IsNotification")
        @Expose
        private boolean IsNotification;

        public boolean IsNotification() {
            return IsNotification;
        }

        public void setChekdedNoti(boolean chekdedNoti) {
            IsNotification = chekdedNoti;
        }

        @SerializedName("FrequencyList")
        private ArrayList<FrequencyList>FrequencyList;

        public ArrayList<com.shamrock.reework.activity.MedicineModule.service.FrequencyList> getFrequencyList() {
            return FrequencyList;
        }

        public void setFrequencyList(ArrayList<com.shamrock.reework.activity.MedicineModule.service.FrequencyList> frequencyList) {
            FrequencyList = frequencyList;
        }

        public String getImgPath() {
            return ImgPath;
        }

        public void setImgPath(String imgPath) {
            ImgPath = imgPath;
        }

        public String getImgName() {
            return ImgName;
        }

        public void setImgName(String imgName) {
            ImgName = imgName;
        }

        public Integer getMymedid() {
            return mymedid;
        }

        public void setMymedid(Integer mymedid) {
            this.mymedid = mymedid;
        }

//        public Integer getMedicineId() {
//            return medicineId;
//        }

//        public void setMedicineId(Integer medicineId) {
//            this.medicineId = medicineId;
//        }

        public String getMedicineName() {
            return medicineName;
        }

        public void setMedicineName(String medicineName) {
            this.medicineName = medicineName;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getDosage() {
            return dosage;
        }

        public void setDosage(String dosage) {
            this.dosage = dosage;
        }

       /* @SerializedName("Mymedid")
        private int myMedid;
        @SerializedName("UserID")
        private int UserID;
        @SerializedName("MedicineId")
        private int MedicineId;
        @SerializedName("MedicineName")
        private String Name;
        @SerializedName("Frequency")
        private String Frequency;
        @SerializedName("Dosage")
        private String Days;

        public int getMyMedid() {
            return myMedid;
        }

        public void setMyMedid(int myMedid) {
            this.myMedid = myMedid;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int userID) {
            UserID = userID;
        }

        public int getMedicineId() {
            return MedicineId;
        }

        public void setMedicineId(int medicineId) {
            MedicineId = medicineId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getFrequency() {
            return Frequency;
        }

        public void setFrequency(String frequency) {
            Frequency = frequency;
        }

        public String getDays() {
            return Days;
        }

        public void setDays(String days) {
            Days = days;
        }*/
    }
}
