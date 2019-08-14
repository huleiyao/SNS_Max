package com.bytegem.snsmax.main.app.bean.group;

import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.user.UserBean;

import java.util.ArrayList;

public class LISTGroupFeeds {
    private ArrayList<FeedBean> feeds;
    private ArrayList<UserBean> users;

    public ArrayList<FeedBean> getFeeds() {
        return feeds;
    }

    public void setFeeds(ArrayList<FeedBean> feeds) {
        this.feeds = feeds;
    }

    public ArrayList<UserBean> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserBean> users) {
        this.users = users;
    }
}
