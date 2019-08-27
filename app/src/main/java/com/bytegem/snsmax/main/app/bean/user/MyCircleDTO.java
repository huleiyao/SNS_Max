package com.bytegem.snsmax.main.app.bean.user;

import android.widget.EditText;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.List;

/**
 * 我的圈子数据传输对象
 */
public class MyCircleDTO extends MBaseBean {
    public List<MyCircleDataItem> data;

    /**
     * 数据传输实体中的每项item数据，每个圈子的数据
     */
    public static class MyCircleDataItem{
        public int id;
        public String name;
        public String desc;
        public String avatar;
        public boolean is_private;
        public int popularity;
        public int feeds_count;
        public int members_count;
        public String speak;
        public String created_at;
        public String updated_at;
        public MyCircleDataCreatorItem creator;
    }

    /**
     * 圈子每个实体中的Creator字段数据。主要作用于判定是否为自己创建的
     */
    public static class MyCircleDataCreatorItem{
        public int id;
        public String name;
        public String desc;
        public String avatar;
        public String sex;
        public int exp;
        public int created_at;
        public int likes_count;
        public int followers_count;
        public int followings_count;
    }


}
