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
package com.bytegem.snsmax.main.app.mvc.chat.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.mvc.chat.bean.Message;
import com.bytegem.snsmax.main.app.mvc.chat.utils.UrlUtils;
import com.bytegem.snsmax.main.app.mvc.chat.voice.manager.MediaManager;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kymjs (http://www.kymjs.com/) on 6/8/15.
 */
public class ChatAdapter extends BaseAdapter {

    /**
     * 聊天列表中对内容的点击事件监听
     */
    public interface OnChatItemClickListener {
        void onPhotoClick(int position);

        void onTextClick(int position);

        void onFaceClick(int position);

        void onVoiceClick(int position);
    }

    private final Context cxt;
    private List<Message> datas = null;
    private KJBitmap kjb;
    private OnChatItemClickListener listener;

    List<AnimationDrawable> mAnimationDrawables = new ArrayList<>();
    int pos = -1;//标记当前录音索引，默认没有播放任何一个
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ChatAdapter(Context cxt, List<Message> datas, OnChatItemClickListener listener) {
        this.cxt = cxt;
        if (datas == null) {
            datas = new ArrayList<Message>(0);
        }
        this.datas = datas;
        kjb = new KJBitmap();
        this.listener = listener;
    }

    public void refresh(List<Message> datas) {
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).isSend ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        final ViewHolder holder;
        final Message data = datas.get(position);
        if (v == null) {
            holder = new ViewHolder();
            if (data.isSend) {
                v = View.inflate(cxt, R.layout.chat_item_list_right, null);
            } else {
                v = View.inflate(cxt, R.layout.chat_item_list_left, null);
            }
            holder.layout_content = v.findViewById(R.id.chat_item_layout_content);
            holder.img_avatar = v.findViewById(R.id.chat_item_avatar);
            holder.img_chatimage = v.findViewById(R.id.chat_item_content_image);
            holder.img_sendfail = v.findViewById(R.id.chat_item_fail);
            holder.progress = v.findViewById(R.id.chat_item_progress);
            holder.tv_chatcontent = v.findViewById(R.id.chat_item_content_text);
            holder.rl_chatvoice = v.findViewById(R.id.rl_chatvoice);
            holder.iea_ivred = v.findViewById(R.id.iea_iv_red);
            holder.iea_llsinger = v.findViewById(R.id.iea_ll_singer);
            holder.iea_tvvoicetime1 = v.findViewById(R.id.iea_tv_voicetime1);
            holder.tv_date = v.findViewById(R.id.chat_item_date);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (datas.size() > 1 && position > 1 &&
                datas.get(position).time - datas.get(position - 1).time > 1000 * 60 * 5) {
            //如果上一条记录时间大于5分钟才会显示时间
            holder.tv_date.setText(StringUtils.friendlyTime(df.format(new Date(data.time))));
            holder.tv_date.setVisibility(View.VISIBLE);
        } else {
            holder.tv_date.setVisibility(View.GONE);
        }

        //如果是文本类型，则隐藏图片，如果是图片则隐藏文本
        if (data.type == Message.MSG_TYPE_TEXT) { //文本消息
            holder.img_chatimage.setVisibility(View.GONE);
            holder.rl_chatvoice.setVisibility(View.GONE);
            holder.tv_chatcontent.setVisibility(View.VISIBLE);
            if (data.content.contains("href")) {
                holder.tv_chatcontent = UrlUtils.handleHtmlText(holder.tv_chatcontent, data
                        .content);
            } else {
                holder.tv_chatcontent = UrlUtils.handleText(holder.tv_chatcontent, data
                        .content);
            }
        } else if (data.type == Message.MSG_TYPE_VIOCE) { //语音消息
            holder.img_chatimage.setVisibility(View.GONE);
            holder.tv_chatcontent.setVisibility(View.GONE);
            holder.rl_chatvoice.setVisibility(View.VISIBLE);
            //设置显示时长
            holder.iea_tvvoicetime1.setText(data.second <= 0 ? 1 + "''" : data.second + "''");
            if (!data.isPlayed) {
                holder.iea_ivred.setVisibility(View.VISIBLE);
            } else {
                holder.iea_ivred.setVisibility(View.GONE);
            }
            if (listener != null) {
                holder.rl_chatvoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onVoiceClick(position);
                        final AnimationDrawable animationDrawable = (AnimationDrawable) holder.iea_llsinger.getBackground();
                        //重置动画
                        resetAnim(animationDrawable);
                        animationDrawable.start();

                        //处理点击正在播放的语音时，可以停止；再次点击时重新播放。
                        if (pos == position) {
                            if (data.isPlaying) {
                                animationDrawable.stop();
                                animationDrawable.selectDrawable(0);//reset
                                stopPlay(data);
                                return;
                            } else {
                                data.isPlaying = true;
                            }
                        } else {
                            if (pos != -1 && datas.get(pos).isPlaying) {
                                animationDrawable.stop();
                                animationDrawable.selectDrawable(0);//reset
                                stopPlay(datas.get(pos));
                            }
                        }

                        //记录当前位置正在播放。
                        pos = position;
                        startPlay(data,animationDrawable);
                    }
                });
            }
        } else {
            holder.tv_chatcontent.setVisibility(View.GONE);
            holder.rl_chatvoice.setVisibility(View.GONE);
            holder.img_chatimage.setVisibility(View.VISIBLE);

            //如果内存缓存中有要显示的图片，且要显示的图片不是holder复用的图片，则什么也不做，否则显示一张加载中的图片
            if (kjb.getMemoryCache(data.content) != null && data.content != null &&
                    data.content.equals(holder.img_chatimage.getTag())) {
            } else {
                holder.img_chatimage.setImageResource(R.drawable.default_head);
            }
            kjb.display(holder.img_chatimage, data.content, 300, 300);
        }

        //如果是表情或图片，则不显示气泡，如果是图片则显示气泡
        if (data.type != Message.MSG_TYPE_TEXT) {
            holder.layout_content.setBackgroundResource(android.R.color.transparent);
        } else {
            if (data.isSend) {
                holder.layout_content.setBackgroundResource(R.drawable.chat_to_bg_selector);
            } else {
                holder.layout_content.setBackgroundResource(R.drawable.chat_from_bg_selector);
            }
        }

        //显示头像
        if (data.isSend) {
            kjb.display(holder.img_avatar, data.fromUserAvatar);
        } else {
            kjb.display(holder.img_avatar, data.toUserAvatar);
        }

        if (listener != null) {
            holder.tv_chatcontent.setOnClickListener(v1 -> listener.onTextClick(position));
            holder.img_chatimage.setOnClickListener(v12 -> {
                switch (data.type) {
                    case Message.MSG_TYPE_PHOTO: //图片点击
                        listener.onPhotoClick(position);
                        break;
                    case Message.MSG_TYPE_FACE: //不知道
                        listener.onFaceClick(position);
                        break;
                    case Message.MSG_TYPE_VIOCE: //语音
                        listener.onFaceClick(position);
                        break;
                }
            });
        }

        //消息发送的状态
        switch (data.type) {
            case Message.MSG_STATE_FAIL:
                holder.progress.setVisibility(View.GONE);
                holder.img_sendfail.setVisibility(View.VISIBLE);
                break;
            case Message.MSG_STATE_SUCCESS:
                holder.progress.setVisibility(View.GONE);
                holder.img_sendfail.setVisibility(View.GONE);
                break;
            case Message.MSG_STATE_SENDING:
                holder.progress.setVisibility(View.VISIBLE);
                holder.img_sendfail.setVisibility(View.GONE);
                break;
        }
        return v;
    }

    //停止播放
    private void stopPlay(Message data) {
        data.isPlaying = false;
        MediaManager.release();
    }

    //开始播放
    private void startPlay(Message data,AnimationDrawable animationDrawable) {
        data.isPlaying = true;
        if(!data.isPlayed) {
            data.isPlayed = true;
        }
        //播放前重置。
        MediaManager.release();
        //开始实质播放
        MediaManager.playSound(
                data.content,
                mediaPlayer -> {
                    data.isPlaying = false;
                    resetAnim(animationDrawable);
                    MediaManager.release();
                    //播放完毕，当前播放索引置为-1。
                    pos = -1;
                }
        );
    }

    //重置动画
    private void resetAnim(AnimationDrawable animationDrawable) {
        if (!mAnimationDrawables.contains(animationDrawable)) {
            mAnimationDrawables.add(animationDrawable);
        }
        for (AnimationDrawable ad : mAnimationDrawables) {
            ad.selectDrawable(0);
            ad.stop();
        }
    }

    static class ViewHolder {
        TextView tv_date;
        ImageView img_avatar;
        TextView tv_chatcontent;
        RelativeLayout rl_chatvoice;
        ImageView img_chatimage;
        ImageView img_sendfail;
        ProgressBar progress;
        RelativeLayout layout_content;

        //语音相关的空间
        ImageView iea_ivred; //语音消息未读的小红点
        LinearLayout iea_llsinger; //语音消息播放时候的动画
        TextView iea_tvvoicetime1; //时长
    }
}
