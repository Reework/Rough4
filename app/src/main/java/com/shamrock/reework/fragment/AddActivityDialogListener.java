package com.shamrock.reework.fragment;

public interface AddActivityDialogListener {

    void onActivityAdd(String ActivityID, String SubActivtyID, String hours, String ID, String activityclocktime);

    void onActivityEdit(String ActivityID, String SubActivtyID, String hours, String ID, String activityclocktime);
}
