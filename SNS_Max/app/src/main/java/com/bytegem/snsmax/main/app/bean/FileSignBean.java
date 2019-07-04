package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.google.gson.annotations.SerializedName;

public class FileSignBean extends MBaseBean {
    private String path;
    private Header header;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public class Header {
        private String Authorization;
        private String Host;
        @SerializedName("Content-MD5")
        private String md5;
        @SerializedName("x-cos-acl")
        private String cos;

        public String getAuthorization() {
            return Authorization;
        }

        public void setAuthorization(String authorization) {
            Authorization = authorization;
        }

        public String getHost() {
            return Host;
        }

        public void setHost(String host) {
            Host = host;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getCos() {
            return cos;
        }

        public void setCos(String cos) {
            this.cos = cos;
        }
    }
}
