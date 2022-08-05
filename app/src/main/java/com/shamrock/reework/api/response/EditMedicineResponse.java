package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditMedicineResponse
{
    /**
     * Code : 1
     * Message : Medicine update successfully
     * Data : [{"Mymedid":13,"UserID":102,"MedicineId":2,"Frequency":"2","Dosage":"2,3","IsDeleted":true,"DeletedOn":null}]
     */

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
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

    public static class DataResponse
    {
        /**
         * Mymedid : 13
         * UserID : 102
         * MedicineId : 2
         * Frequency : 2
         * Dosage : 2,3
         * IsDeleted : true
         * DeletedOn : null
         */

        @SerializedName("Mymedid")
        private int Mymedid;

        @SerializedName("UserID")
        private int UserID;

        @SerializedName("MedicineId")
        private int MedicineId;

        @SerializedName("Frequency")
        private String Frequency;

        @SerializedName("Dosage")
        private String Dosage;

        @SerializedName("IsDeleted")
        private boolean IsDeleted;

        @SerializedName("DeletedOn")
        private Object DeletedOn;

        public int getMymedid() {
            return Mymedid;
        }

        public void setMymedid(int Mymedid) {
            this.Mymedid = Mymedid;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getMedicineId() {
            return MedicineId;
        }

        public void setMedicineId(int MedicineId) {
            this.MedicineId = MedicineId;
        }

        public String getFrequency() {
            return Frequency;
        }

        public void setFrequency(String Frequency) {
            this.Frequency = Frequency;
        }

        public String getDosage() {
            return Dosage;
        }

        public void setDosage(String Dosage) {
            this.Dosage = Dosage;
        }

        public boolean isIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(boolean IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        public Object getDeletedOn() {
            return DeletedOn;
        }

        public void setDeletedOn(Object DeletedOn) {
            this.DeletedOn = DeletedOn;
        }
    }
//    {"dosage":"1","frequency":"3","medicineId":2,"myMedicineID":13,"userID":102}
}
