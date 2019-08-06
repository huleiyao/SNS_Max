package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.utils.Utils;

public class MediaVideoContent extends MBaseBean {
    //video
    private String cover;
    private String video;
    private boolean coverChange = false;
    private boolean videoChange = false;

    public MediaVideoContent(String video, String cover) {
        this.cover = cover;
        this.video = video;
    }

    public void complementUrl() {
        if (cover != null && !cover.isEmpty() && !cover.contains("http"))
            setCover(Utils.checkUrl(cover));
        if (video != null && !video.isEmpty() && !video.contains("http"))
            setCover(Utils.checkUrl(video));
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
        coverChange = true;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
        videoChange = true;
    }

    public boolean isCoverChange() {
        return coverChange;
    }

    public boolean isVideoChange() {
        return videoChange;
    }
}
