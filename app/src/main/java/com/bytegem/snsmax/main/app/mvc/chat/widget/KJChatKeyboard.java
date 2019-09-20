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
package com.bytegem.snsmax.main.app.mvc.chat.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.mvc.chat.adapter.FaceCategroyAdapter;
import com.bytegem.snsmax.main.app.mvc.chat.utils.OnOperationListener;
import com.bytegem.snsmax.main.app.mvc.chat.utils.SoftKeyboardStateHelper;
import com.bytegem.snsmax.main.app.mvc.chat.voice.manager.AudioRecordButton;

import java.util.List;

/**
 * 控件主界面
 *
 * @author kymjs (http://www.kymjs.com/)
 */
public class KJChatKeyboard extends RelativeLayout implements
        SoftKeyboardStateHelper.SoftKeyboardStateListener {

    public interface OnToolBoxListener {
        void onShowFace();
    }

    public static final int LAYOUT_TYPE_HIDE = 0;
    public static final int LAYOUT_TYPE_FACE = 1; //表情符号
    public static final int LAYOUT_TYPE_MORE = 2; //点击更多
    public static final int LAYOUT_TYPE_KEYBORAD_VOICE = 3; //语音/键盘切换

    /**
     * 当前页面的配置信息保存位置
     */
    public static final String CONFIG_PATH_KEY = "chat_keyboard";
    /**
     * 输入类型保存的key
     */
    public static final String CONFIG_INPUT_TYPE = "inpty_type";

    /**
     * 最上层输入框
     */
    private EditText mEtMsg;
    private AudioRecordButton mVoiceMsg;
    private CheckBox mKeybordVoice; //键盘和语音切换的按钮
    private CheckBox mBtnFace;
    private CheckBox mBtnMore;
    private Button mBtnSend;

    /**
     * 表情
     */
    private ViewPager mPagerFaceCagetory;
    private RelativeLayout mRlFace;
    private PagerSlidingTabStrip mFaceTabs;

    private int layoutType = LAYOUT_TYPE_HIDE;
    private FaceCategroyAdapter adapter;  //点击表情按钮时的适配器

    private List<String> mFaceData;

    private Context context;
    private OnOperationListener listener;
    private OnToolBoxListener mFaceListener;
    private SoftKeyboardStateHelper mKeyboardHelper;
    private int inputType = 0;//输入模式
    AudioRecordButton.AudioFinishRecorderListener mFinishRecoredListener;


    public KJChatKeyboard(Context context) {
        super(context);
        init(context);
    }

    public KJChatKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KJChatKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View root = View.inflate(context, R.layout.chat_tool_box, null);
        this.addView(root);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
        this.initWidget();
        applyConfig();
    }

    /**
     * 设置输入模式：0-文本模式，1:语音模式
     *
     * @param inputType
     */
    public void setInputType(int inputType) {
        this.inputType = inputType;
        try {
            hideLayout();
            if (this.inputType == 0) { //文本
                mVoiceMsg.setVisibility(GONE);
                mEtMsg.setVisibility(VISIBLE);
                mKeybordVoice.setChecked(true);
                changeLayout(LAYOUT_TYPE_HIDE);
                showKeyboard(getContext());
            } else if (this.inputType == 1) { //语音
                mVoiceMsg.setVisibility(VISIBLE);
                mEtMsg.setVisibility(GONE);
                mKeybordVoice.setChecked(false);
                changeLayout(LAYOUT_TYPE_KEYBORAD_VOICE);
                hideKeyboard(getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置录制完成的监听
     * @param listener
     */
    public void setAudioFinishRecorderListener(AudioRecordButton.AudioFinishRecorderListener listener) {
        mFinishRecoredListener = listener;
    }

    private void initData() {
        mKeyboardHelper = new SoftKeyboardStateHelper(((Activity) getContext())
                .getWindow().getDecorView());
        mKeyboardHelper.addSoftKeyboardStateListener(this);
    }

    private void initWidget() {
        mEtMsg = findViewById(R.id.toolbox_et_message);
        mVoiceMsg = findViewById(R.id.toolbox_et_voice);
        mKeybordVoice = findViewById(R.id.toolbox_btn_keybord_voice);
        mBtnSend = findViewById(R.id.toolbox_btn_send);
        mBtnFace = findViewById(R.id.toolbox_btn_face);
        mBtnMore = findViewById(R.id.toolbox_btn_more);
        mRlFace = findViewById(R.id.toolbox_layout_face);
        mPagerFaceCagetory = findViewById(R.id.toolbox_pagers_face);
        mFaceTabs = findViewById(R.id.toolbox_tabs);
        adapter = new FaceCategroyAdapter(((FragmentActivity) getContext())
                .getSupportFragmentManager(), LAYOUT_TYPE_FACE);
        mBtnSend.setOnClickListener(v -> {
            if (listener != null) {
                String content = mEtMsg.getText().toString();
                listener.send(content);
                mEtMsg.setText(null);
            }
        });
        // 点击表情按钮
        mBtnFace.setOnClickListener(getFunctionBtnListener(LAYOUT_TYPE_FACE));
        // 点击表情按钮旁边的加号
        mBtnMore.setOnClickListener(getFunctionBtnListener(LAYOUT_TYPE_MORE));
        //点击语音/键盘切换按钮
        mKeybordVoice.setOnClickListener(getFunctionBtnListener(LAYOUT_TYPE_KEYBORAD_VOICE));
        // 点击消息输入框
        mEtMsg.setOnClickListener(v -> hideLayout());
        mEtMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkSendBtu();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mVoiceMsg.setHasRecordPromission(true);
        mVoiceMsg.setLongClickRecording(false);
        mVoiceMsg.setAudioFinishRecorderListener((seconds, filePath) -> {
            //录制完成的操作
            if(mFinishRecoredListener != null){
                mFinishRecoredListener.onFinished(seconds,filePath);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInputType(inputType); //设置模式。默认为文本模式
    }

    @Override
    protected void onDetachedFromWindow() {
        //切换模式
        context.getSharedPreferences(CONFIG_PATH_KEY, Context.MODE_PRIVATE)
                .edit()
                .putInt(CONFIG_INPUT_TYPE, inputType)
                .apply();
        super.onDetachedFromWindow();
    }

    /*************************
     * 内部方法 start
     ************************/

    //应用配置
    private void applyConfig() {
        inputType = context.getSharedPreferences(CONFIG_PATH_KEY, Context.MODE_PRIVATE).getInt(CONFIG_INPUT_TYPE, 0);
    }

    //检查发送按钮的隐藏和显示
    private void checkSendBtu() {
        if (mEtMsg.getText().toString().isEmpty()) {
            mBtnMore.setVisibility(View.VISIBLE);
            mBtnSend.setVisibility(View.GONE);
        } else {
            mBtnMore.setVisibility(View.GONE);
            mBtnSend.setVisibility(View.VISIBLE);
        }
    }

    private OnClickListener getFunctionBtnListener(final int which) {
        return v -> {
            if (LAYOUT_TYPE_KEYBORAD_VOICE == which) {
                //切换模式
                if (inputType == 0) {
                    inputType = 1;
                } else {
                    inputType = 0;
                }
                setInputType(inputType);
            } else if (isShow() && which == layoutType) {
                hideLayout();
                showKeyboard(context);
            } else {
                changeLayout(which);
                showLayout();
                mBtnFace.setChecked(layoutType == LAYOUT_TYPE_FACE);
                mBtnMore.setChecked(layoutType == LAYOUT_TYPE_MORE);
            }
        };
    }

    private void changeLayout(int mode) {
        adapter = new FaceCategroyAdapter(((FragmentActivity) getContext())
                .getSupportFragmentManager(), mode);
        adapter.setOnOperationListener(listener);
        layoutType = mode;
        setFaceData(mFaceData);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        hideLayout();
    }

    @Override
    public void onSoftKeyboardClosed() {
    }

    /***************************** 内部方法 end ******************************/

    /**************************
     * 可选调用的方法 start
     **************************/

    public void setFaceData(List<String> faceData) {
        mFaceData = faceData;
        adapter.refresh(faceData);
        mPagerFaceCagetory.setAdapter(adapter);
        mFaceTabs.setViewPager(mPagerFaceCagetory);
        if (layoutType == LAYOUT_TYPE_MORE || layoutType == LAYOUT_TYPE_KEYBORAD_VOICE) {
            mFaceTabs.setVisibility(GONE);
        } else {
            //加1是表示第一个分类为默认的emoji表情分类，这个分类是固定不可更改的
            if (faceData.size() + 1 < 2) {
                mFaceTabs.setVisibility(GONE);
            } else {
                mFaceTabs.setVisibility(VISIBLE);
            }
        }
    }

    public EditText getEditTextBox() {
        return mEtMsg;
    }

    public void showLayout() {
        hideKeyboard(this.context);
        // 延迟一会，让键盘先隐藏再显示表情键盘，否则会有一瞬间表情键盘和软键盘同时显示
        postDelayed(() -> {
            mRlFace.setVisibility(View.VISIBLE);
            if (mFaceListener != null) {
                mFaceListener.onShowFace();
            }
        }, 50);
    }


    public boolean isShow() {
        return mRlFace.getVisibility() == VISIBLE;
    }

    public void hideLayout() {
        mRlFace.setVisibility(View.GONE);
        if (mBtnFace.isChecked()) {
            mBtnFace.setChecked(false);
        }
        if (mBtnMore.isChecked()) {
            mBtnMore.setChecked(false);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(mEtMsg.getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     */
    public static void showKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(activity.getCurrentFocus()
                    .getWindowToken(), 0);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public OnOperationListener getOnOperationListener() {
        return listener;
    }

    public void setOnOperationListener(OnOperationListener onOperationListener) {
        this.listener = onOperationListener;
        adapter.setOnOperationListener(onOperationListener);
    }

    public void setOnToolBoxListener(OnToolBoxListener mFaceListener) {
        this.mFaceListener = mFaceListener;
    }

    /*********************** 可选调用的方法 end ******************************/
}
