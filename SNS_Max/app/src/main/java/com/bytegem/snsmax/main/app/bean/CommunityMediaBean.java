package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommunityMediaBean extends MBaseBean {
    private String type;
    @SerializedName("contents")
    private CommunityMediaContent contents;
//    @SerializedName("contents")
//    private ArrayList<String> images;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CommunityMediaContent getContents() {
        return contents;
    }

    public void setContents(CommunityMediaContent contents) {
        this.contents = contents;
    }
}
