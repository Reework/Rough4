package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.MyRecoachModule.activity.AdditionalDetails;

import java.util.ArrayList;

public class ReecoachDetailsResponse {
    /**
     * Code : 1
     * Message : I am your Reecoach
     * Data : {"FirstName":"sachin","LastName":"Modak","country_id":1,"city_id":2,"lang_code":"mr","Email":"sachin.m@intelegain.com","Mobile_No":"9769068028","Gender":1,"Address":"thane,maharashtra","Image_URL":null}
     */

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
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

    public static class DataResponse {
        public int getUnreadMessageCount() {
            return UnreadMessageCount;
        }

        public void setUnreadMessageCount(int unreadMessageCount) {
            UnreadMessageCount = unreadMessageCount;
        }

        /**
         * FirstName : sachin
         * LastName : Modak
         * country_idcountry_id : 1
         * city_id : 2
         * lang_code : mr
         * Email : sachin.m@intelegain.com
         * Mobile_No : 9769068028
         * Gender : 1
         * Address : thane,maharashtra
         * Image_URL : null
         */

//
        @SerializedName("UnreadMessageCount")
        @Expose
        private int UnreadMessageCount;
        @SerializedName("IdaNumber")
        @Expose
        private String IdaNumber;
        @SerializedName("ReecoachType")
        @Expose
        private String ReecoachType;

        public String getIdaNumber() {
            return IdaNumber;
        }

        public void setIdaNumber(String idaNumber) {
            IdaNumber = idaNumber;
        }

        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Mobile_No")
        @Expose
        private String mobileNo;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("ImageUrl")
        @Expose
        private String imageUrl;
        @SerializedName("Discription")
        @Expose
        private String Discription;


        //        allowChange
        @SerializedName("allowChange")
        @Expose
        private boolean allowChange;

        public boolean isAllowChange() {
            return allowChange;
        }

        public void setAllowChange(boolean allowChange) {
            this.allowChange = allowChange;
        }

        public String getDiscription() {
            return Discription;
        }

        public void setDiscription(String discription) {
            Discription = discription;
        }

        @SerializedName("AdditionalDetails")
        ArrayList<AdditionalDetails> AdditionalDetails;


        public ArrayList<AdditionalDetails> getAdditionalDetails() {
            return AdditionalDetails;
        }

        public void setAdditionalDetails(ArrayList<AdditionalDetails> additionalDetails) {
            AdditionalDetails = additionalDetails;
        }

        public String getReecoachType() {
            return ReecoachType;
        }

        public void setReecoachType(String reecoachType) {
            ReecoachType = reecoachType;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
