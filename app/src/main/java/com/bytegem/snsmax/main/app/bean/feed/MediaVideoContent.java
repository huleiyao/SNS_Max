package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.utils.Utils;

public class MediaVideoContent extends MBaseBean {
    //video
    private String cover;
    private String video;

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
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
