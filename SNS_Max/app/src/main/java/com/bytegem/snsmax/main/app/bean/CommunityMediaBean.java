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

    private transient String[] images;
    private transient List<ImageItem> imageItems;
    private transient CommunityMediaVideoContent mediaVideo;
    private transient CommunityMediaLinkContent mediaLink;

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

    public MBaseBean getMedia() {
        if (type == null) return null;
        switch (type) {
            case "video":
                return new MediaVideo(type, getMediaVideo());
            case "image":
                return new MediaImage(type, getImages());
            case "url":
                return new MediaLink(type, getMediaLink());
        }
        return null;
    }

    class MediaImage extends MBaseBean {
        private String type;
        private String[] contents;

        public MediaImage(String type, String[] contents) {
            this.type = type;
            this.contents = contents;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String[] getContents() {
            return contents;
        }

        public void setContents(String[] contents) {
            this.contents = contents;
        }
    }

    class MediaVideo extends MBaseBean {
        private String type;
        private CommunityMediaVideoContent contents;

        public MediaVideo(String type, CommunityMediaVideoContent contents) {
            this.type = type;
            this.contents = contents;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public CommunityMediaVideoContent getContents() {
            return contents;
        }

        public void setContents(CommunityMediaVideoContent contents) {
            this.contents = contents;
        }
    }

    class MediaLink extends MBaseBean {
        private String type;
        private CommunityMediaLinkContent contents;

        public MediaLink(String type, CommunityMediaLinkContent contents) {
            this.type = type;
            this.contents = contents;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public CommunityMediaLinkContent getContents() {
            return contents;
        }

        public void setContents(CommunityMediaLinkContent contents) {
            this.contents = contents;
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

    public synchronized boolean checkImage() {
        for (String image : images)
            if (image == null || image.isEmpty())
                return false;
        return true;
    }

    public void setImages(int position, String image) {
        this.images[position] = image;
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
