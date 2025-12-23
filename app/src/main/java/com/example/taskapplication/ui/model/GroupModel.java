package com.example.taskapplication.ui.model;

public class GroupModel {

    private String groupname;
    private boolean isGroup;
    private ServersModel server;

    public GroupModel(String groupname) {
        this.groupname = groupname;
        this.isGroup = true;
    }

    public GroupModel(ServersModel server) {
        this.server = server;
      //  this.groupname = server.getServerName();
        this.isGroup = false;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public String getGroupname() {
        return groupname;
    }

    public ServersModel getServer() {
        return server;
    }
 /*   String groupName;
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
    }*/
}
