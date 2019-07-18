package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.bean.location.Geo;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;
import com.bytegem.snsmax.main.app.bean.user.UserBean;

import org.greenrobot.greendao.annotation.Id;

public class FeedBean extends MBaseBean {
    @Id(autoincrement = false)
    private int id;
    private String contents;
    private String created_at;
    private Geo geo;
    private MediaBean media;

    private UserBean user;
    private GroupBean group;
    private TopicBean topic;

    private int likes_count;
    private int comments_count;
    private int share_count;
    private String share;
    private boolean has_liked;

    public FeedBean() {
//        setUserBean(new UserBean());
        setGroup(new GroupBean());
    }

    public boolean isHas_liked() {
        return has_liked;
    }

    public void setHas_liked(boolean has_liked) {
        this.has_liked = has_liked;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public TopicBean getTopic() {
        return topic;
    }

    public void setTopic(TopicBean topic) {
        this.topic = topic;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public MediaBean getMedia() {
        return media;
    }

    public void setMedia(MediaBean media) {
        this.media = media;
    }
}
