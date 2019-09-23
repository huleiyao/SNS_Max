package com.bytegem.snsmax.main.app.bean.chat;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;

import java.util.List;

/**
 * 聊天列表
 */
public class ChatList extends MBaseBean {
    /**
     * 文本类型
     */
    public static final String TYPE_TEXT = "text";
    /**
     * 图片类型
     */
    public static final String TYPE_IMAGE = "image";
    /**
     * 语音类型
     */
    public static final String TYPE_AUDIO = "audio";

    /**
     * 获取最新的一条信息
     * @param fjMessage 当前房间最新的一条记录实体
     * @return
     */
    public static ChatListContentMessage getLastMessage(ChatListFJMessage fjMessage){
        if(fjMessage == null || fjMessage.contents == null){
            return new ChatListContentMessage();
        }
        try {
            return HttpMvcHelper.getGson().fromJson(fjMessage.contents,ChatListContentMessage.class);
        }catch (Exception e){
            return new ChatListContentMessage();
        }
    }

    public List<ChatListItem> data;
    public ChatListLinks links;
    public ChatListMate meta;

    /**
     * 聊天记录的列表每项实体
     */
    public static class ChatListItem {
        /**
         * 房间id
         */
        public int id;
        /**
         * 保留字段
         */
        public boolean is_group;
        /**
         * 开始聊天时间
         */
        public String created_at;
        /**
         * 结束聊天时间
         */
        public String updated_at;
        /**
         * 成员信息
         */
        public List<ChatListItemUserInfo> members;
        /**
         * 当前房间最新一条消息
         */
        public ChatListFJMessage last_message;
    }

    /**
     * 聊天信息中的成员信息
     */
    public static class ChatListItemUserInfo {
        /**
         * 用户id
         */
        public int id;
        /**
         * 电话号码
         */
        public String phone_number;
        /**
         * 名称
         */
        public String name;
        /**
         * 头像
         */
        public String avatar;
        /**
         * 性别
         */
        public String sex;
        /**
         * 猜测可能是经验值
         */
        public int exp;
        /**
         * 创建时间
         */
        public String created_at;
        /**
         *
         */
        public int likes_count;
        /**
         *
         */
        public int followers_count;
        /**
         *
         */
        public int followings_count;
        /**
         * 房间属性
         */
        public ChatListFJAttr chat_member;
    }

    /**
     * 房间属性
     */
    public static class ChatListFJAttr {
        /**
         * 用户id
         */
        public String user_id;
        /**
         * 房间id
         */
        public String room_id;
        /**
         * 角色
         */
        public String role;
        /**
         * 互动成员最后一次活动时间
         */
        public String last_active_at;
        /**
         * 成员最后阅读的消息列表ID，可以和房间信息的 `last_message.id` 如果两个只不想等，
         * 则代表当前房间有消息未读。注意，这个信息仅用于用户ID与当前登录 ID 相同时操作！
         */
        public int last_read_message_id;
    }

    /**
     * 当前房间最新的一条消息
     */
    public static class ChatListFJMessage {
        /**
         * 消息id
         */
        public int id;
        /**
         * 用户id
         */
        public int user_id;
        /**
         * 创建时间
         */
        public String created_at;
        /**
         * 消息内容
         */
        public String contents;
    }

    /**
     * 当前房间最新的一条消息
     */
    public static class ChatListContentMessage {
        /**
         * 消息内容
         */
        public String text = "";
        /**
         * 消息类型
         */
        public String type = ChatList.TYPE_TEXT;
    }

    /**
     * 当前房间最新的一条消息
     */
    public static class ChatListLinks {
        /**
         *
         */
        public String first;
        /**
         *
         */
        public String last;
        /**
         *
         */
        public String prev;
        /**
         *
         */
        public String next;
    }

    /**
     * 当前房间最新的一条消息
     */
    public static class ChatListMate {
        /**
         *
         */
        public int current_page;
        /**
         *
         */
        public int from;
        /**
         *
         */
        public String path;
        /**
         *
         */
        public int per_page;
        /**
         *
         */
        public int to;
        /**
         *
         */
        public int total;
    }

    /**
     * 发送消息的时候的结构体
     */
    public static class SendMessageBean{
        public ChatListContentMessage contents;

        public SendMessageBean(ChatListContentMessage contents){
            this.contents = contents;
        }
    }
}
