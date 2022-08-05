package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TestList {
    private String GroupName;

    @SerializedName("TestDetails")
    @Expose
    private List<BCAResponce.TestDetails> TestDetails = null;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public List<BCAResponce.TestDetails> getTestDetails() {
        return TestDetails;
    }

    public void setTestDetails(List<BCAResponce.TestDetails> testDetails) {
        TestDetails = testDetails;
    }
}
