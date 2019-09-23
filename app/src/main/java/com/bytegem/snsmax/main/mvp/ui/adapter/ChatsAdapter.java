package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.view.View;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.chat.ChatList;
import com.bytegem.snsmax.main.app.utils.DateFormConvert;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.kymjs.kjframe.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatsAdapter extends BaseQuickAdapter<ChatList.ChatListItem, BaseViewHolder> {

    private int userId = 0;

    public ChatsAdapter() {
        super(R.layout.item_chat_list);
    }

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void convert(BaseViewHolder viewHolder, ChatList.ChatListItem bean) {
        try {
            if(userId == 0) {
                userId = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson()).getData().getId();
            }
        }catch (Exception e){
            userId = -1;
        }
//        viewHolder.setText(R.id.area_name, bean);
        GlideLoaderUtil.LoadCircleImage(mContext,
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560157380928&di=a5fcba2094b5d96612a2a77b4873115e&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F9b671d17b52639d35e7c76c23f79fbabebe769d43140-xjB5Tw_fw658"
                , viewHolder.getView(R.id.chat_item_user_cover)
        );
        String msg = "";
        String count = "0";
        try{
            ChatList.ChatListContentMessage item = ChatList.getLastMessage(bean.last_message);
            switch (item.type){
                case ChatList.TYPE_IMAGE:{
                    msg = "[图片]";
                    break;
                }
                case ChatList.TYPE_TEXT:{
                    msg = item.text;
                    break;
                }
                case ChatList.TYPE_AUDIO:{
                    msg = "[语音]";
                    break;
                }
                default:{
                    msg = "[未知]";
                    break;
                }
            }
        }catch (Exception e){}
        String name = "";
        if(!bean.is_group){
            if(userId > 0) {
                for (ChatList.ChatListItemUserInfo member : bean.members) {
                    if (!(member.id == userId)) {
                        name = member.name;
                    }
                }
                if ("".equals(name)) {
                    name = "-";
                }
            }else{
                name = "-";
            }
        }else{
            name ="--";
        }
        Date date = DateFormConvert.utc2LocalData(bean.last_message.created_at);
        viewHolder.setText(R.id.chat_item_time, date == null ? "--" : StringUtils.friendlyTime(df.format(date))) //时间
                .setText(R.id.chat_item_user_name, name) //名称
                .setText(R.id.chat_item_user_tag, msg) //最近的消息
                .setText(R.id.chat_item_message_count, count); //
        if("0".equals(count)){
            viewHolder.getView(R.id.chat_item_message_count).setVisibility(View.INVISIBLE);
        }else{
            viewHolder.getView(R.id.chat_item_message_count).setVisibility(View.VISIBLE);
        }

    }

}
