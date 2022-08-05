package com.shamrock.reework.util;

public class DaysUtils {

    public static String fromInteger(int x) {
        switch (x) {
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tue";
            case 4:
                return "Wed";
            case 5:
                return "Thu";
            case 6:
                return "Fri";
            case 7:
                return "Sat";
        }
        return null;
    }

    public static int[] getIntArray(String aa, String seperator) {
        // if aa = "2,33,4,8,6,45,784,21,102"; && separator == ','
        // then intArray = [2,33,4,8,6,45,784,21,102];
        String[] daysList = aa.split(seperator);
        int[] intArray = new int[daysList.length];
        for (int i = 0; i < daysList.length; i++) {
//            intArray[i] = Integer.parseInt(daysList[i]);
            intArray[i] = Integer.parseInt(daysList[i]) -1 ;    // -1 use for days stability
        }
        return intArray;
    }
}
