package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class CommunityMediaBean extends MBaseBean {
    private String type;
    @SerializedName("contents")
    private String contents;

    @Expose(serialize = false, deserialize = false)
    private String[] images;
    @Expose(serialize = false, deserialize = false)
    private List<ImageItem> imageItems;
    @Expose(serialize = false, deserialize = false)
    private CommunityMediaVideoContent mediaVideo;
    @Expose(serialize = false, deserialize = false)
    private CommunityMediaLinkContent mediaLink;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setContents() {
        if (type == null || contents == null) return;
        Gson gson = new Gson();
        switch (type) {
            case "video":
                setContents(getMediaVideo() == null ? "" : gson.toJson(getMediaVideo()));
                break;
            case "image":
                setContents(getImages() == null ? "" : gson.toJson(getImages()));
                break;
            case "url":
                setContents(getMediaLink() == null ? "" : gson.toJson(getMediaLink()));
                break;
        }
    }

    public void initContent() {
        if (type == null || contents == null) return;
        Gson gson = new Gson();
        switch (type) {
            case "video":
                setMediaVideo(gson.fromJson(contents, new TypeToken<CommunityMediaVideoContent>() {
                }.getType()));
                break;
            case "image":
                setImages(gson.fromJson(contents, new TypeToken<ArrayList<String>>() {
                }.getType()));
                break;
            case "url":
                setMediaLink(gson.fromJson(contents, new TypeToken<CommunityMediaLinkContent>() {
                }.getType()));
                break;
        }
    }

    public List<ImageItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(List<ImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public String[] getImages() {
        if (images == null || images.length == 0) return null;
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public CommunityMediaVideoContent getMediaVideo() {
        if (mediaVideo == null) return null;
        return mediaVideo;
    }

    public void setMediaVideo(CommunityMediaVideoContent mediaVideo) {
        this.mediaVideo = mediaVideo;
    }

    public CommunityMediaLinkContent getMediaLink() {
        if (mediaLink == null) return null;
        return mediaLink;
    }

    public void setMediaLink(CommunityMediaLinkContent mediaLink) {
        this.mediaLink = mediaLink;
    }
}
