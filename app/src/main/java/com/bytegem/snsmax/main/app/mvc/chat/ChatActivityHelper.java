package com.bytegem.snsmax.main.app.mvc.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.chat.ChatList;
import com.bytegem.snsmax.main.app.bean.chat.ChatMessageSendResp;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.bytegem.snsmax.main.app.config.UpdataImageService;
import com.bytegem.snsmax.main.app.mvc.chat.adapter.ChatAdapter;
import com.bytegem.snsmax.main.app.mvc.chat.bean.Emojicon;
import com.bytegem.snsmax.main.app.mvc.chat.bean.Faceicon;
import com.bytegem.snsmax.main.app.mvc.chat.bean.Message;
import com.bytegem.snsmax.main.app.mvc.chat.emoji.DisplayRules;
import com.bytegem.snsmax.main.app.mvc.chat.utils.OnOperationListener;
import com.bytegem.snsmax.main.app.mvc.chat.widget.KJChatKeyboard;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;

import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 界面帮助类。帮助处理界面以及业务等
 */
@SuppressLint("CheckResult")
public class ChatActivityHelper {
    ChatActivity act;
    List<Message> messageDatas = new ArrayList();
    public MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    String roomId; //当前房间的房间id
    List<ChatList.ChatListItemUserInfo> userInfos = new ArrayList(); //当前房间的用户集合

    private ChatAdapter adapter;

    ChatActivityHelper(ChatActivity act) {
        this.act = act;
    }

    /**
     * 发送文本消息
     *
     * @param message
     */
    void sendStringMessage(Message message) {
        addMessageToList(message);
        //开始发送信息
        sendRemoteTextMessage(message);
    }

    /**
     * 发送图片消息
     *
     * @param message
     */
    void sendPhotoMessage(Message message) {
        sendRemoteMediaMessage(message);
    }

    /**
     * 发送语音消息
     *
     * @param message
     */
    void sendAudioMessage(Message message) {
        sendRemoteMediaMessage(message);
    }

    /**
     * 添加消息到列表
     *
     * @param message
     */
    public void addMessageToList(List<Message> message) {
        messageDatas.addAll(message);
        notifyDataSetChanged();
    }

    /**
     * 添加消息到列表
     *
     * @param message
     */
    public void addMessageToList(Message message) {
        messageDatas.add(message);
        notifyDataSetChanged();
    }

    /**
     * 刷新消息
     */
    public void notifyDataSetChanged() {
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
        if (userinfo == null || userinfo.getData() == null) {
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
        if (userinfo == null || userinfo.getData() == null) {
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
        if (userinfo == null || userinfo.getData() == null) {
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
     * @param voice        语音消息的位置路径
     * @return 消息体
     */
    public Message createSendVoiceMessage(String toUserName, String toUserAvatar, File voice) {
        DATAUser userinfo = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson());
        if (userinfo == null || userinfo.getData() == null) {
            return null;
        }
        return new Message(
                Message.MSG_TYPE_AUDIO, Message.MSG_STATE_SENDING, userinfo.getData().getName(), userinfo.getData().getAvatar(), toUserName,
                toUserAvatar, voice.getAbsolutePath(), true, false, new Date(System.currentTimeMillis())
        );
    }

    /**
     * 初始化键盘输入相关操作
     */
    void initMessageInputToolBox(KJChatKeyboard box, ListView mRealListView) {
        //语音发送的消息
        box.setAudioFinishRecorderListener((seconds, filePath) -> {
            Message message = createSendVoiceMessage(act.getToUserName(), act.getToUserAvatar(), new File(filePath));
            message.second = (int) seconds;
            sendAudioMessage(message);
        });
        box.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                Message message = createSendTextMessage(act.getToUserName(), act.getToUserAvatar(), content);
                sendStringMessage(message);
            }

            @Override
            public void selectedFace(Faceicon content) {
                Message message = createSendFaceMessage(act.getToUserName(), act.getToUserAvatar(), content);
                if (message != null) {
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
     * 初始化相关的Intent
     */
    void initIntent(Intent it) {
        roomId = String.valueOf(it.getIntExtra(act.ROOM_ID, 0));
        String userInfoJson = it.getStringExtra(act.USREINFO_KEY);
        ChatList.ChatListItemUserInfo[] userin = HttpMvcHelper.getGson().fromJson(userInfoJson, ChatList.ChatListItemUserInfo[].class);
        if (userin != null) {
            for (int i = 0; i < userin.length; i++) {
                userInfos.add(userin[i]);
            }
        }
    }

    /**
     * 初始化列表
     */
    void initListView() {
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
    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener getOnTouchListener(KJChatKeyboard box) {
        return (v, event) -> {
            box.hideLayout();
            box.hideKeyboard(act);
            return false;
        };
    }

    /*
     * 发送文本信息
     * @param message
     */
    private void sendRemoteTextMessage(Message message) {
        ChatList.ChatListContentMessage messageContent = new ChatList.ChatListContentMessage();
        messageContent.type = ChatList.TYPE_TEXT;
        messageContent.text = message.content;
        HttpMvcHelper.obtainRetrofitService(CommunityService.class)
                .sendMessage(MApplication.getTokenOrType(), roomId, HttpMvcHelper.getGson().toJson(messageContent))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(succ -> {
                    message.toUserAvatar = userAvatar(succ);
                    //发送成功。将消息状态更改为已发送
                    updateSendStatus(message, Message.MSG_STATE_SUCCESS);
                }, error -> {
                    updateSendStatus(message, Message.MSG_STATE_FAIL);
                });
    }

    /*
     * 发送媒体消息，也就是除了文本消息之外的消息
     * @param message
     */
    private void sendRemoteMediaMessage(Message message) {
        //第一步。上传临时文件到服务器获取地址
        String[] imgExt = {"jpg", "jpeg", "png", "webp", "gif"};
        String[] voiceExt = {"mpeg", "mp4"};
        String messExt = FileUtils.getFileExtension(message.content);
        String tempType = "";
        //检查是否为图片类型
        for (String s : imgExt) {
            if (s.equals(messExt)) {
                tempType = "image";
                break;
            }
        }
        if ("mpeg".equals(messExt)) {
            tempType = "audio";
        }
        if ("".equals(tempType)) {
            for (String s : voiceExt) {
                if (s.equals(messExt)) {
                    tempType = "video";
                    break;
                }
            }
        }
        String minType = getMediaMessageMimeType(message);
        if ("".equals(minType)) {
            ToastUtils.showShort("不支持的文件格式:" + messExt);
            return;
        }
        File tempCover = M.getTempFile(MApplication.getInstance(), minType);
        if(tempCover == null){
            ToastUtils.showShort("文件处理失败");
            return;
        }
        addMessageToList(message);
        uploadMediaTempFile(
                tempType,
                tempCover,
                new File(message.content).length(),
                M.getFileMD5(new File(message.content))
        )
                .flatMap(fileSignBean -> updataFile(fileSignBean, message))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(succ -> {
                    message.toUserAvatar = userAvatar(succ);
                    //发送成功。将消息状态更改为已发送
                    updateSendStatus(message, Message.MSG_STATE_SUCCESS);
                }, error -> {
                    updateSendStatus(message, Message.MSG_STATE_FAIL);
                });
    }

    /*
     * 检查消息所对应的的类型的类型
     * @param message
     * @return 在发送的消息体中的类型:text、image、audio
     */
    private String getCheckMessageType(Message message) {
        if (message.type == Message.MSG_TYPE_PHOTO) {
            return ChatList.TYPE_IMAGE;
        }
        if (message.type == Message.MSG_TYPE_AUDIO) {
            return ChatList.TYPE_AUDIO;
        }
        return ChatList.TYPE_TEXT;
    }

    /*
     * 上传临时文件到服务器。并且获得路径
     *
     * @param type 类型:仅支持 image - 图片和 video - 视频， audio - 音频。
     * @param file 必须，上传一个 10KB 以内的任意尺寸同需要上传文件同 MIME 文件，支持的文件有 jpg,jpeg,png,webp,gif,mp4
     * @param length 必须，需要上传的图片真实文件尺寸，单位 Byte。
     *               如果 type=video 则设置上限为 10485760 Byte（10MiB）如果 type=image 则设置上限为 3145728 byte（3MiB）
     * @param md5 必须，需要上传的图片真实的 32 位 MD5 摘要。
     * @return
     */
    private Observable<FileSignBean> uploadMediaTempFile(String type, File file, long length, String md5) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("factor", file.getName(), requestBody);
        return HttpMvcHelper
                .obtainRetrofitService(UpdataImageService.class)
                .getImageSign(
                        MApplication.getTokenOrType()
                        , MultipartBody.Part.createFormData("type", type)
                        , part
                        , MultipartBody.Part.createFormData("length", length + "")
                        , MultipartBody.Part.createFormData("md5", md5)
                );
    }

    /*
     * 上传文件到服务器并且发送该消息，实际上融合了上传文件和发送消息在一起
     *
     * @param fileSignBean 上传临时文件返回的对象
     * @param message      消息对象
     * @return
     */
    private Observable<ChatMessageSendResp> updataFile(FileSignBean fileSignBean, Message message) {
        String path = fileSignBean.getPath();
        if (path.indexOf("/") == 0)
            path = path.substring(1);
        final String remoPath = path;
        return HttpMvcHelper
                .obtainRetrofitService(CommunityService.class)
                .updataImage(
                        fileSignBean.getHeaders().getAuthorization()
                        , fileSignBean.getHeaders().getHost()
                        , fileSignBean.getHeaders().getMd5()
                        , fileSignBean.getHeaders().getCos()
                        , getMediaMessageMimeType(message)
                        , RequestBody.create(MediaType.parse("application/otcet-stream"), new File(message.content))
                        , path
                )
                .flatMap(mBaseBean -> {
                    ChatList.ChatListContentMessage messageContent = new ChatList.ChatListContentMessage();
                    messageContent.type = getCheckMessageType(message);
                    messageContent.text = remoPath;
                    //更新图片为网络图片
//                    message.content = remoPath;
                    return HttpMvcHelper
                            .obtainRetrofitService(CommunityService.class)
                            .sendMessage(
                                    HttpMvcHelper.getTokenOrType(),
                                    roomId,
                                    HttpMvcHelper.getGson().toJson(messageContent)
                            );
                });

    }

    /*
     * 获取媒体消息的文件类型
     * @return
     */
    private String getMediaMessageMimeType(Message message) {
        String type = "";
        String ext = FileUtils.getFileExtension(message.content);
        switch (ext) {
            case "png":
                type = "image/png";
                break;
            case "jpeg":
                type = "image/jpeg";
                break;
            case "jpg":
                type = "image/jpg";
                break;
            case "gif":
                type = "image/gif";
                break;
            case "mpeg":
                type = "audio/mpeg";
                break;
            case "mp4":
                type = "video/mp4";
                break;
        }
        return type;
    }

    //根据返回类型在本地查找用户头像
    private String userAvatar(ChatMessageSendResp resp) {
        if (resp == null || resp.data == null) {
            return "";
        }
        for (ChatList.ChatListItemUserInfo userInfo : userInfos) {
            if (userInfo.equals(resp.data.user_id)) {
                return userInfo.avatar;
            }
        }
        return "";
    }

    /*
     * 更新指定消息的状态
     * @param message
     * @param newStatus
     */
    private void updateSendStatus(Message message, int newStatus) {
        message.state = newStatus;
        adapter.notifyDataSetChanged();
    }
}
