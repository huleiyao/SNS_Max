package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FileBean extends MBaseBean {
    @Id
    private String url;//网络地址
    private String path;//本地地址
    private String fileMimeType;

    @Generated(hash = 2061782861)
    public FileBean(String url, String path, String fileMimeType) {
        this.url = url;
        this.path = path;
        this.fileMimeType = fileMimeType;
    }

    @Generated(hash = 1910776192)
    public FileBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileMimeType() {
        return fileMimeType;
    }

    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }
}
