package com.bytegem.snsmax.main.app.bean.usertype;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class UserTypeBean extends MBaseBean {

    private boolean has_bind;
    private String number;

    public boolean isHas_bind() {
        return has_bind;
    }

    public void setHas_bind(boolean has_bind) {
        this.has_bind = has_bind;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
