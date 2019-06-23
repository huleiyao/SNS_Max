package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class LocationBean extends MBaseBean {
    private String city;
    private Geo geo;

    public LocationBean() {
        setGeo(new Geo());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitude() {
        return geo.getLongitude();
    }

    public void setLongitude(double longitude) {
        geo.setLongitude(longitude + "");
    }

    public String getLatitude() {
        return geo.getLatitude();
    }

    public void setLatitude(double latitude) {
        geo.setLatitude(latitude + "");
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
