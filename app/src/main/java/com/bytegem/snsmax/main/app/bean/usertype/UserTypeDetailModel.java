package com.bytegem.snsmax.main.app.bean.usertype;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserTypeDetailModel extends MBaseBean {

    private String code;
    @SerializedName("new")
    private List<UserTypeDetailBean> userPhone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<UserTypeDetailBean> getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(List<UserTypeDetailBean> userPhone) {
        this.userPhone = userPhone;
    }
}
