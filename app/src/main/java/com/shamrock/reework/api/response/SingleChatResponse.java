package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SingleChatResponse
{

    @SerializedName("SenderProfileImg")
    @Expose
    private String SenderProfileImg;
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<ChatList> data = null;

    public String getSenderProfileImg() {
        return SenderProfileImg;
    }

    public void setSenderProfileImg(String senderProfileImg) {
        SenderProfileImg = senderProfileImg;
    }

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

    public ArrayList<ChatList> getData() {
        return data;
    }

    public void setData(ArrayList<ChatList> data) {
        this.data = data;
    }

    public class ChatList
    {
        @SerializedName("ChatID")
        @Expose
        private Integer chatID;
        @SerializedName("FromUserID")
        @Expose
        private Integer fromUserID;
        @SerializedName("ToUserID")
        @Expose
        private Integer toUserID;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("DateTime")
        @Expose
        private String dateTime;
        @SerializedName("IsRead")
        @Expose
        private Boolean isRead;
        @SerializedName("IsReecoach")
        @Expose
        private Boolean isReecoach;

        public Integer getChatID() {
            return chatID;
        }

        public void setChatID(Integer chatID) {
            this.chatID = chatID;
        }

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

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public Boolean getIsRead() {
            return isRead;
        }

        public void setIsRead(Boolean isRead) {
            this.isRead = isRead;
        }

        public Boolean getIsReecoach() {
            return isReecoach;
        }

        public void setIsReecoach(Boolean isReecoach) {
            this.isReecoach = isReecoach;
        }
    }
}
