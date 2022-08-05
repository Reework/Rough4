package com.shamrock.reework.reecoachmodule.activities.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClsChatHistoryData {



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

}
