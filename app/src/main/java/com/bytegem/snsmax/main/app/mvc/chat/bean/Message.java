/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bytegem.snsmax.main.app.mvc.chat.bean;

import java.util.Date;

/**
 * 聊天消息javabean
 *
 * @author kymjs (http://www.kymjs.com/)
 */
public class Message {
    //消息类型分类
    public final static int MSG_TYPE_TEXT = 3;
    public final static int MSG_TYPE_PHOTO = 1;
    public final static int MSG_TYPE_FACE = 2;
    public final static int MSG_TYPE_AUDIO = 4; //语音

    //消息状态集合
    public final static int MSG_STATE_SENDING = 3;
    public final static int MSG_STATE_SUCCESS = 1;
    public final static int MSG_STATE_FAIL = 2;

    public Long id;
    public int type; // 0-文本 | 1-图片 | 2-face | 4-语音 ... 消息类型
    public int state; // 0-sending | 1-success | 2-fail 消息发送状态
    public String fromUserName;
    public String fromUserAvatar;
    public String toUserName;
    public String toUserAvatar;
    public String content; //文本消息是内容。其他消息是路径

    public Boolean isSend; //是否是我发送出去
    public Boolean sendSucces; //是否发送成功
    public Long time;

    //语音消息相关的属性
    public int second = 0; //语音消息的时长
    public boolean isPlayed = false;//是否已经播放过
    public boolean isPlaying = false;//是否正在播放

    public Message(int type, int state, String fromUserName,
                   String fromUserAvatar, String toUserName, String toUserAvatar,
                   String content, Boolean isSend, Boolean sendSucces, Date time) {
        super();
        this.type = type;
        this.state = state;
        this.fromUserName = fromUserName;
        this.fromUserAvatar = fromUserAvatar;
        this.toUserName = toUserName;
        this.toUserAvatar = toUserAvatar;
        this.content = content;
        this.isSend = isSend;
        this.sendSucces = sendSucces;
        this.time = time.getTime();
    }
}
