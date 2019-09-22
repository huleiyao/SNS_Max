package com.bytegem.snsmax.main.app.bean.chat;

import java.util.List;

/**
 * 获取当前房间历史消息的实体
 */
public class ChatListResp {

    public List<ChatListDataResp> data;

    public static class ChatListDataResp{
        public int id;
        public int user_id;
        public ChatList.ChatListContentMessage contents;
        public String created_at;
    }

}
