package com.bytegem.snsmax.main.mvp.model;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.usertype.UserTypeBean;

public class UserBindModel extends MBaseBean {

    private UserTypeBean phone;
    private UserTypeBean wx;

    public UserTypeBean getWx() {
        return wx;
    }

    public void setWx(UserTypeBean wx) {
        this.wx = wx;
    }

    public UserTypeBean getPhone() {
        return phone;
    }

    public void setPhone(UserTypeBean phone) {
        this.phone = phone;
    }
}
