package com.bytegem.snsmax.main.app.bean.messages;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.List;

public class ChatRoomResp extends MBaseBean {

    public ChatRoomData data;

    public static class ChatRoomData{
        public int id;
        public boolean is_group;
        public String created_at ;
        public String updated_at;
        public List<ChatRoomMembers> members;
    }

    public static class ChatRoomMembers{
        public int id;
        public String name;
        public String sex;
        public int exp;
        public int likes_count;
        public int followers_count;
        public int followings_count;
        public ChatRoomChatMembers chat_member;
    }

    public static class ChatRoomChatMembers {
        public int user_id;
        public int room_id;
        public String role;
        public String last_active_at;
        public String last_read_message_id;

    }
}
