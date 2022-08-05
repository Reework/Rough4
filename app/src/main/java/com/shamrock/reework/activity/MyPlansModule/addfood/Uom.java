package com.shamrock.reework.activity.MyPlansModule.addfood;

import com.shamrock.reework.activity.todaysplan.model.Groups;

import java.util.ArrayList;

public class Uom {
    private ArrayList<Groups> Group;

    private String Value;

    private String Text;

    private String Selected;

    private String Disabled;

    public Uom(ArrayList<Groups> group, String value, String text, String selected, String disabled) {
        Group = group;
        Value = value;
        Text = text;
        Selected = selected;
        Disabled = disabled;
    }

    public ArrayList<Groups> getGroup() {
        return Group;
    }

    public void setGroup(ArrayList<Groups> group) {
        Group = group;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getSelected() {
        return Selected;
    }

    public void setSelected(String selected) {
        Selected = selected;
    }

    public String getDisabled() {
        return Disabled;
    }

    public void setDisabled(String disabled) {
        Disabled = disabled;
    }
}
