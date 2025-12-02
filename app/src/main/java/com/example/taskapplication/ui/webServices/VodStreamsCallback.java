package com.example.taskapplication.ui.webServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VodStreamsCallback {

    @SerializedName("num")
    @Expose
    private Integer num;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stream_type")
    @Expose
    private String streamType;
    @SerializedName("stream_id")
    @Expose
    private Integer streamId;
    @SerializedName("tmdb_id")
    @Expose
    private Integer tmdbId;
    @SerializedName("stream_icon")
    @Expose
    private String streamIcon;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("rating_5based")
    @Expose
    private Float rating5based;
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName("is_adult")
    @Expose
    private String isAdult;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("container_extension")
    @Expose
    private String containerExtension;
    @SerializedName("custom_sid")
    @Expose
    private String customSid;
    @SerializedName("direct_source")
    @Expose
    private String directSource;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    public Integer getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(Integer tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getStreamIcon() {
        return streamIcon;
    }

    public void setStreamIcon(String streamIcon) {
        this.streamIcon = streamIcon;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Float getRating5based() {
        return rating5based;
    }

    public void setRating5based(Float rating5based) {
        this.rating5based = rating5based;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(String isAdult) {
        this.isAdult = isAdult;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContainerExtension() {
        return containerExtension;
    }

    public void setContainerExtension(String containerExtension) {
        this.containerExtension = containerExtension;
    }

    public String getCustomSid() {
        return customSid;
    }

    public void setCustomSid(String customSid) {
        this.customSid = customSid;
    }

    public String getDirectSource() {
        return directSource;
    }

    public void setDirectSource(String directSource) {
        this.directSource = directSource;
    }
}

