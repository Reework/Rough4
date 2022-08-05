package com.shamrock.reework.util;

public enum Gender {
    MALE("Male", 0),
    FEMALE("Female", 1);

    private String stringValue;
    private int intValue;

    Gender(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
