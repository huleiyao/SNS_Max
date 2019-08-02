package com.bytegem.snsmax.main.app.bean.login;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class LoginData extends MBaseBean {
    private String access_token;// 用户授权 Token
    private String token_type;// 授权认证类型
    private int expires_in;// 过期时间，单位「秒」

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
