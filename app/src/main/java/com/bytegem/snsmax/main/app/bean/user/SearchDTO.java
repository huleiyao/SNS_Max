package com.bytegem.snsmax.main.app.bean.user;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.List;

/**
 * 搜索接口返回的实体
 */
public class SearchDTO<T> extends MBaseBean {

    /** 查询返回的数据 */
    public List<T> data;

    /**
     * 每个用户的实体
     */
    public class SearchUserItem{

    }
}
