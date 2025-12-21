package com.example.taskapplication.ui.model;

public class ServersModel {
    String serverName;
    int OnlineUser;
    String serverConnection;
    int serverPort;
    String serverGroupName;

    public ServersModel(String serverName, int onlineUser, String serverConnection, int serverPort, String serverGroupName) {
        this.serverName = serverName;
        this.OnlineUser = onlineUser;
        this.serverConnection = serverConnection;
        this.serverPort = serverPort;
        this.serverGroupName = serverGroupName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getOnlineUser() {
        return OnlineUser;
    }

    public void setOnlineUser(int onlineUser) {
        OnlineUser = onlineUser;
    }

    public String getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(String serverConnection) {
        this.serverConnection = serverConnection;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerGroupName() {
        return serverGroupName;
    }

    public void setServerGroupName(String serverGroupName) {
        this.serverGroupName = serverGroupName;
    }
}
