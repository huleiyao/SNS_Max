package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class MediaBean extends MBaseBean {
    private String type;
    @SerializedName("contents")
    private Object contents;

    private transient String[] images;//上传时才用
    private transient ArrayList<String> imageList;
    private transient ArrayList<ImageItem> imageItems;
    private transient MediaVideoContent mediaVideo;
    private transient MediaLinkContent mediaLink;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContents() {
        return contents;
    }

    public void setContents(Object contents) {
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
        private MediaVideoContent contents;

        public MediaVideo(String type, MediaVideoContent contents) {
            this.type = type;
            this.contents = contents;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public MediaVideoContent getContents() {
            return contents;
        }

        public void setContents(MediaVideoContent contents) {
            this.contents = contents;
        }
    }

    class MediaLink extends MBaseBean {
        private String type;
        private MediaLinkContent contents;

        public MediaLink(String type, MediaLinkContent contents) {
            this.type = type;
            this.contents = contents;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public MediaLinkContent getContents() {
            return contents;
        }

        public void setContents(MediaLinkContent contents) {
            this.contents = contents;
        }
    }

    public void initContent() {
        if (type == null || contents == null) return;
        Gson gson = new Gson();
        switch (type) {
            case "video":
                setMediaVideo(gson.fromJson(gson.toJson(contents), new TypeToken<MediaVideoContent>() {
                }.getType()));
                break;
            case "image":
                setImageList(gson.fromJson(gson.toJson(contents), new TypeToken<ArrayList<String>>() {
                }.getType()));
                complementUrlFromImage();
                break;
            case "url":
                setMediaLink(gson.fromJson(gson.toJson(contents), new TypeToken<MediaLinkContent>() {
                }.getType()));
                break;
        }
    }

    public void complementUrlFromImage() {
        if (imageList != null) {
            ArrayList<String> list = new ArrayList<>();
            for (String path : imageList)
                if (path == null) ;
                else if (path.isEmpty()) ;
                else if (path.contains("http"))
                    list.add(path);
                else
                    list.add(Utils.checkUrl(path));
            setImageList(list);
        }
    }

    public ArrayList<ImageItem> getImageItems() {
        if (imageItems == null) initContent();
        if (imageItems == null) imageItems = new ArrayList<>();
        return imageItems;
    }

    public void setImageItems(ArrayList<ImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public String[] getImages() {
        if (mediaVideo == null) initContent();
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

    public MediaVideoContent getMediaVideo() {
        if (mediaVideo == null) initContent();
        if (mediaVideo == null) mediaVideo = new MediaVideoContent("", "");
        return mediaVideo;
    }

    public MediaVideoContent getCreateMediaVideo() {
        return mediaVideo;
    }

    public void setMediaVideo(MediaVideoContent mediaVideo) {
        this.mediaVideo = mediaVideo;
    }

    public MediaLinkContent getMediaLink() {
        if (mediaLink == null) initContent();
        if (mediaLink == null) mediaLink = new MediaLinkContent();
        return mediaLink;
    }

    public void setMediaLink(MediaLinkContent mediaLink) {
        this.mediaLink = mediaLink;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }
}