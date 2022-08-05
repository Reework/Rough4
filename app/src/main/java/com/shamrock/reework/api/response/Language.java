package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Language {
    /**
     * lang_code : hi
     * lang_name : Hindi
     */

    @SerializedName("lang_id")
    @Expose
    private Integer langId;

    @SerializedName("lang_code")
    @Expose
    private String langCode;

    @SerializedName("lang_name")
    @Expose
    private String langName;

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

   /* @SerializedName("lang_code")
    private String lang_code;
    @SerializedName("lang_name")
    private String lang_name;

    public String getLang_code() {
        return lang_code;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    public String getLang_name() {
        return lang_name;
    }

    public void setLang_name(String lang_name) {
        this.lang_name = lang_name;
    }*/
}

