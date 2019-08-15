package com.bytegem.snsmax.main.app.bean.location;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.location.Geo;

public class LocationBean extends MBaseBean {
    private String city;
    private String addressTitle;
    private String addressLocation;
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

    public String getAddress() {
        return geo.getAddress();
    }

    public void setAddress(String address) {
        geo.setAddress(address);
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }
}
