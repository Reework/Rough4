package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveSingleChatRequest
{
    @SerializedName("FromUserID")
    @Expose
    private Integer fromUserID;
    @SerializedName("ToUserID")
    @Expose
    private Integer toUserID;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(Integer fromUserID) {
        this.fromUserID = fromUserID;
    }

    public Integer getToUserID() {
        return toUserID;
    }

    public void setToUserID(Integer toUserID) {
        this.toUserID = toUserID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
