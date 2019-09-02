package com.bytegem.snsmax.main.app.bean.user;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.discusses.DiscussBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;

import java.util.List;

/**
 * 搜索接口返回的实体
 */
public class SearchDTO<T> extends MBaseBean {

    /**
     * 查询返回的数据
     */
    public List<T> data;

    /**
     * 每个用户的实体
     */
    public class SearchUserItem extends UserBean {

    }

    /**
     * 每个圈子的实体
     */
    public class SearchCircelItem extends MyCircleDTO.MyCircleDataItem {

    }

    /**
     * 每个讨论的实体
     */
    public class SearchDiscussesItem extends DiscussBean {

    }
}
