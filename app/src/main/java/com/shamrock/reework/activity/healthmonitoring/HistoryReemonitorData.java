package com.shamrock.reework.activity.healthmonitoring;

import java.util.ArrayList;

public class HistoryReemonitorData {
    private ArrayList<TestList> TestList;

    private String Key;

    public ArrayList<TestList> getTestList() {
        return TestList;
    }

    public void setTestList(ArrayList<TestList> testList) {
        TestList = testList;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
