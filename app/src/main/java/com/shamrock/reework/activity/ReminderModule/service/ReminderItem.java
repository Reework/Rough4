package com.shamrock.reework.activity.ReminderModule.service;

import java.io.Serializable;

public class ReminderItem implements Serializable{

    int reminderIconPosition;
    String reminderName, reminderClock;
    int times;
    boolean checked;

    public ReminderItem(int reminderIconPosition, String reminderName, String reminderClock, int times, boolean checked) {
        this.reminderIconPosition = reminderIconPosition;
        this.reminderName = reminderName;
        this.reminderClock = reminderClock;
        this.times = times;
        this.checked = checked;
    }

    public int getReminderIcon() {
        return reminderIconPosition;
    }

    public void setReminderIcon(int reminderIcon) {
        this.reminderIconPosition = reminderIcon;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getReminderClock() {
        return reminderClock;
    }

    public void setReminderClock(String reminderClock) {
        this.reminderClock = reminderClock;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
