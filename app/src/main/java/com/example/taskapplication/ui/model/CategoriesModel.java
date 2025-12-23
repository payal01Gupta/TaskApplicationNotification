package com.example.taskapplication.ui.model;

public class CategoriesModel {

    int channelId;
    String channelName;
    Boolean isHD;

    public CategoriesModel(int channelId, String channelName, Boolean isHD) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.isHD = isHD;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Boolean getHD() {
        return isHD;
    }

    public void setHD(Boolean HD) {
        isHD = HD;
    }
}
