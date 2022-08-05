package com.shamrock.reework.activity.AppoinmentModule.pojo;

import java.util.ArrayList;

public class GroupData {
    private String GroupName;

    private ArrayList<User> Users;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public ArrayList<User> getUsers() {
        return Users;
    }

    public void setUsers(ArrayList<User> users) {
        Users = users;
    }
}
