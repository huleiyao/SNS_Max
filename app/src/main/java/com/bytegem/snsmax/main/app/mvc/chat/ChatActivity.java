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
package com.bytegem.snsmax.main.app.mvc.chat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.widget.ListView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.mvc.chat.bean.Message;
import com.bytegem.snsmax.main.app.mvc.chat.voice.manager.MediaManager;
import com.bytegem.snsmax.main.app.mvc.chat.widget.KJChatKeyboard;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.utils.FileUtils;

import java.io.File;


/**
 * 聊天主界面(v2版本)
 */
public class ChatActivity extends KJActivity {

    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0x1;

    private ChatActivityHelper helper;
    KJChatKeyboard box;
    ListView mRealListView;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_chat2);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        helper = new ChatActivityHelper(this);
        box = findViewById(R.id.chat_msg_input_board);
        mRealListView = findViewById(R.id.chat_listview);

        mRealListView.setSelector(android.R.color.transparent);
        helper.initMessageInputToolBox(box,mRealListView);
        helper.initListView();
    }

    public String getToUserName(){
        return "123456";
    }

    public String getToUserAvatar(){
        return "123";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && box.isShow()) {
            box.hideLayout();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.release(); //停止播放语音
    }

    @Override
    protected void onDestroy() {
        helper = null;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD) {
            Uri dataUri = data.getData();
            if (dataUri != null) {
                File file = FileUtils.uri2File(aty, dataUri);
                Message message = helper.createSendPhotoMessage(getToUserName(),getToUserAvatar(),file);
                helper.sendPhotoMessage(message);
            }
        }
    }
}
