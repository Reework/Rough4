package com.shamrock.reework.activity.PaymentModule.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckSumRequest {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("bankid")
    @Expose
    private String bankid;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("debitorcredit")
    @Expose
    private String debitorcredit;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("showMobile")
    @Expose
    private String showMobile;
    @SerializedName("txnType")
    @Expose
    private String txnType;
    @SerializedName("paymentOptionTypes")
    @Expose
    private String paymentOptionTypes;
    @SerializedName("zpPayOption")
    @Expose
    private String zpPayOption;
    @SerializedName("isAutoRedirect")
    @Expose
    private String isAutoRedirect;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDebitorcredit() {
        return debitorcredit;
    }

    public void setDebitorcredit(String debitorcredit) {
        this.debitorcredit = debitorcredit;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getShowMobile() {
        return showMobile;
    }

    public void setShowMobile(String showMobile) {
        this.showMobile = showMobile;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getPaymentOptionTypes() {
        return paymentOptionTypes;
    }

    public void setPaymentOptionTypes(String paymentOptionTypes) {
        this.paymentOptionTypes = paymentOptionTypes;
    }

    public String getZpPayOption() {
        return zpPayOption;
    }

    public void setZpPayOption(String zpPayOption) {
        this.zpPayOption = zpPayOption;
    }

    public String getIsAutoRedirect() {
        return isAutoRedirect;
    }

    public void setIsAutoRedirect(String isAutoRedirect) {
        this.isAutoRedirect = isAutoRedirect;
    }

}
