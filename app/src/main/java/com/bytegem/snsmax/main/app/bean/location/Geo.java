package com.bytegem.snsmax.main.app.bean.location;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class Geo extends MBaseBean {
    private String latitude;//纬度
    private String longitude;//经度
    private String address;//地址信息

    public Geo() {
        setLatitude("0");
        setLongitude("0");
        setAddress("");
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
