package com.example.taskapplication.ui.model;

public class GroupModel {
    String groupName;
    public boolean hasServers;

    public GroupModel(String groupName, boolean hasServers) {
        this.groupName = groupName;
        this.hasServers = hasServers;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isHasServers() {
        return hasServers;
    }

    public void setHasServers(boolean hasServers) {
        this.hasServers = hasServers;
    }
}
