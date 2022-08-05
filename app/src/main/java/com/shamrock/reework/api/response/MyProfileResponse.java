package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

public class MyProfileResponse {

    /**
     * Code : 1
     * Message : Video uploaded sucessfully.
     * Data : D:/sharock_upload/Reework_Images/24.png
     */

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
