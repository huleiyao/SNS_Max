package com.bytegem.snsmax.main.app.bean.usertype;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class UserTypeDetailBean extends MBaseBean {

    private String phone_number;
    private String code;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
