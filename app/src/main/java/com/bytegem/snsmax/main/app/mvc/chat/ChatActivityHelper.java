package com.bytegem.snsmax.main.app.mvc.chat;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;

import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.mvc.chat.adapter.ChatAdapter;
import com.bytegem.snsmax.main.app.mvc.chat.bean.Emojicon;
import com.bytegem.snsmax.main.app.mvc.chat.bean.Faceicon;
import com.bytegem.snsmax.main.app.mvc.chat.bean.Message;
import com.bytegem.snsmax.main.app.mvc.chat.emoji.DisplayRules;
import com.bytegem.snsmax.main.app.mvc.chat.utils.OnOperationListener;
import com.bytegem.snsmax.main.app.mvc.chat.voice.manager.AudioRecordButton;
import com.bytegem.snsmax.main.app.mvc.chat.widget.KJChatKeyboard;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;

import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 界面帮助类。帮助处理界面以及业务等
 */
public class ChatActivityHelper {
    ChatActivity act;
    List<Message> messageDatas = new ArrayList();

    private ChatAdapter adapter;

    ChatActivityHelper(ChatActivity act) {
        this.act = act;
    }

    /**
     * 发送文本消息
     * @param message
     */
    void sendStringMessage(Message message){
        addMessageToList(message);
    }

    /**
     * 发送图片消息
     * @param message
     */
    void sendPhotoMessage(Message message){
        addMessageToList(message);
    }

    /**
     * 发送语音消息
     * @param message
     */
    void sendVoiceMessage(Message message){
        addMessageToList(message);
    }

    /**
     * 添加消息到列表
     * @param message
     */
    public void addMessageToList(List<Message> message){
        messageDatas.addAll(message);
        notifyDataSetChanged();
    }

    /**
     * 添加消息到列表
     * @param message
     */
    public void addMessageToList(Message message){
        messageDatas.add(message);
        notifyDataSetChanged();
    }

    /**
     * 刷新消息
     */
    public void notifyDataSetChanged(){
        adapter.refresh(messageDatas);
    }

    /**
     * 创建发送纯文本消息
     *
     * @param toUserName   接收的用户名称
     * @param toUserAvatar 接收的用户头像地址
     * @param content      消息的内容
     * @return 消息体
     */
    public Message createSendTextMessage(String toUserName, String toUserAvatar, String content) {
        DATAUser userinfo = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson());
        if(userinfo == null || userinfo.getData()== null){
            return null;
        }
        return new Message(
                Message.MSG_TYPE_TEXT, Message.MSG_STATE_SENDING, userinfo.getData().getName(), userinfo.getData().getAvatar(), toUserName,
                toUserAvatar, content, true, false, new Date(System.currentTimeMillis())
        );
    }

    /**
     * 创建发送图片类型消息
     *
     * @param toUserName   接收的用户名称
     * @param toUserAvatar 接收的用户头像地址
     * @param file         图片文件
     * @return 消息体
     */
    public Message createSendPhotoMessage(String toUserName, String toUserAvatar, File file) {
        DATAUser userinfo = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson());
        if(userinfo == null || userinfo.getData()== null){
            return null;
        }
        return new Message(
                Message.MSG_TYPE_PHOTO, Message.MSG_STATE_SENDING, userinfo.getData().getName(), userinfo.getData().getAvatar(), toUserName,
                toUserAvatar, file.getAbsolutePath(), true, false, new Date(System.currentTimeMillis())
        );
    }

    /**
     * 创建发送图片类型消息
     *
     * @param toUserName   接收的用户名称
     * @param toUserAvatar 接收的用户头像地址
     * @param content      内容
     * @return 消息体
     */
    public Message createSendFaceMessage(String toUserName, String toUserAvatar, Faceicon content) {
        DATAUser userinfo = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson());
        if(userinfo == null || userinfo.getData()== null){
            return null;
        }
        return new Message(
                Message.MSG_TYPE_FACE, Message.MSG_STATE_SENDING, userinfo.getData().getName(), userinfo.getData().getAvatar(), toUserName,
                toUserAvatar, content.getPath(), true, false, new Date(System.currentTimeMillis())
        );
    }

    /**
     * 创建发送语音类型消息
     *
     * @param toUserName   接收的用户名称
     * @param toUserAvatar 接收的用户头像地址
     * @param voice      语音消息的位置路径
     * @return 消息体
     */
    public Message createSendVoiceMessage(String toUserName, String toUserAvatar, File voice) {
        DATAUser userinfo = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson());
        if(userinfo == null || userinfo.getData()== null){
            return null;
        }
        return new Message(
                Message.MSG_TYPE_VIOCE, Message.MSG_STATE_SENDING, userinfo.getData().getName(), userinfo.getData().getAvatar(), toUserName,
                toUserAvatar, voice.getAbsolutePath(), true, false, new Date(System.currentTimeMillis())
        );
    }

    /**
     * 初始化键盘输入相关操作
     */
    void initMessageInputToolBox(KJChatKeyboard box, ListView mRealListView){
        //语音发送的消息
        box.setAudioFinishRecorderListener((seconds, filePath) -> {
            Message message = createSendVoiceMessage(act.getToUserName(),act.getToUserAvatar(),new File(filePath));
            message.second = (int) seconds;
            sendVoiceMessage(message);
        });
        box.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                Message message = createSendTextMessage(act.getToUserName(),act.getToUserAvatar(),content);
                sendStringMessage(message);
            }

            @Override
            public void selectedFace(Faceicon content) {
                Message message = createSendFaceMessage(act.getToUserName(),act.getToUserAvatar(),content);
                if(message != null) {
                    addMessageToList(message);
                }
            }

            @Override
            public void selectedEmoji(Emojicon emoji) {
                box.getEditTextBox().append(emoji.getValue());
            }

            @Override
            public void selectedBackSpace(Emojicon back) {
                DisplayRules.backspace(box.getEditTextBox());
            }

            @Override
            public void selectedFunction(int index) {
                switch (index) {
                    case 0:
                        goToAlbum();
                        break;
                    case 1:
                        ViewInject.toast("跳转相机");
                        break;
                }
            }
        });

        List<String> faceCagegory = new ArrayList<>();
//        File faceList = FileUtils.getSaveFolder("chat");
        File faceList = new File("");
        if (faceList.isDirectory()) {
            File[] faceFolderArray = faceList.listFiles();
            for (File folder : faceFolderArray) {
                if (!folder.isHidden()) {
                    faceCagegory.add(folder.getAbsolutePath());
                }
            }
        }

        box.setFaceData(faceCagegory);
        mRealListView.setOnTouchListener(getOnTouchListener(box));
    }

    /**
     * 初始化列表
     */
    void initListView(){
//        byte[] emoji = new byte[]{
//                (byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81
//        };
//        Message message = new Message(
//                Message.MSG_TYPE_TEXT,
//                Message.MSG_STATE_SUCCESS, "\ue415", "avatar", "Jerry", "avatar",
//                new String(emoji), false, true, new Date(System.currentTimeMillis()
//                - (1000 * 60 * 60 * 24) * 8)
//        );
//        Message message1 = new Message(
//                Message.MSG_TYPE_TEXT,
//                Message.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar",
//                "以后的版本支持链接高亮喔:http://www.kymjs.com支持http、https、svn、ftp开头的链接",
//                true, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 8)
//        );
//        Message message2 = new Message(
//                Message.MSG_TYPE_PHOTO,
//                Message.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar",
//                "http://static.oschina.net/uploads/space/2015/0611/103706_rpPc_1157342.png",
//                false, true, new Date(
//                System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 7)
//        );
//        Message message6 = new Message(
//                Message.MSG_TYPE_TEXT,
//                Message.MSG_STATE_FAIL, "Tom", "avatar", "Jerry", "avatar",
//                "test send fail", true, false, new Date(
//                System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 6)
//        );
//        Message message7 = new Message(
//                Message.MSG_TYPE_TEXT,
//                Message.MSG_STATE_SENDING, "Tom", "avatar", "Jerry", "avatar",
//                "<a href=\"http://kymjs.com\">自定义链接</a>也是支持的", true, true, new Date(System.currentTimeMillis()
//                - (1000 * 60 * 60 * 24) * 6)
//        );
//
//        messageDatas.add(message);
//        messageDatas.add(message1);
//        messageDatas.add(message2);
//        messageDatas.add(message6);
//        messageDatas.add(message7);

        adapter = new ChatAdapter(act, messageDatas, getOnChatItemClickListener());
        act.mRealListView.setAdapter(adapter);
    }

    /**
     * @return 聊天列表内存点击事件监听器
     */
    ChatAdapter.OnChatItemClickListener getOnChatItemClickListener() {
        return new ChatAdapter.OnChatItemClickListener() {
            @Override
            public void onPhotoClick(int position) {
                KJLoger.debug(messageDatas.get(position).content + "点击图片的");
                ViewInject.toast(act, messageDatas.get(position).content + "点击图片的");
            }

            @Override
            public void onTextClick(int position) {
            }

            @Override
            public void onFaceClick(int position) {
            }

            @Override
            public void onVoiceClick(int position) {
                notifyDataSetChanged();
                //还需要数据更新
            }
        };
    }

    /**
     * 跳转到选择相册界面
     */
    private void goToAlbum() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            act.startActivityForResult(
                    Intent.createChooser(intent, "选择图片"),
                    ChatActivity.REQUEST_CODE_GETIMAGE_BYSDCARD
            );
        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            intent.setType("image/*");
            act.startActivityForResult(
                    Intent.createChooser(intent, "选择图片"),
                    ChatActivity.REQUEST_CODE_GETIMAGE_BYSDCARD
            );
        }

    }

    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener(KJChatKeyboard box) {
        return (v, event) -> {
            box.hideLayout();
            box.hideKeyboard(act);
            return false;
        };
    }
}
