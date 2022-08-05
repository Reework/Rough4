package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MedicineItem implements Serializable {
    @SerializedName("Mymedid")
    int id;
    @SerializedName("MedicineName")
    String name;

    @SerializedName("ImgName")
    String ImgName;



    @SerializedName("ImgPath")
    String ImgPath;

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
