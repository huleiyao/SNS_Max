package com.bytegem.snsmax.main.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;

import java.util.ArrayList;
import java.util.List;

public class TagEditTextView extends AppCompatEditText {
    String content = "";
    private int preTextLength = 0;
    // 默认,话题文本高亮颜色
    private static final int FOREGROUND_COLOR = Color.parseColor("#2CB098");
    // 默认,话题背景高亮颜色
    private static final int BACKGROUND_COLOR = Color.parseColor("#00000000");
    private TextWatcher textWatcher = new TextWatcher() {
        int selectionStart = 0;
        int selectionEnd = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (topicBean != null) {
                if (s.toString().contains(String.format("#%s#", topicBean.getName()))) {
                    setObject(topicBean, true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            Editable editable = getText();
            int length = s.toString().length();

            //删除
            if (length < preTextLength) {
//                 = getSelectionEnd();
//                int selectionEnd = getSelectionEnd();

                /*
                 * 如果光标起始和结束不在同一位置,删除文本
                 */
                if (selectionStart != selectionEnd) {
                    // 查询文本是否属于话题对象,若是移除列表数据
//                    String tagetText = getText().toString().substring(selectionStart, selectionEnd);
                    if (topicBean != null)
                        if (getText().toString().indexOf(topicBean.getName()) == -1) {
                            selectionStart = 0;
                            selectionEnd = 0;
                            topicBean = null;
                        }
                    return;
                }
                int lastPos = 0;
                if (topicBean != null) {
                    String objectText = topicBean.getName();
                    lastPos = getText().toString().indexOf(objectText, lastPos);
                    int start = getSelectionStart();
                    if (lastPos != -1) {
                        if (start != 0 && start >= lastPos && start <= (lastPos + objectText.length())) {
                            // 选中话题
                            setSelection(lastPos, lastPos + objectText.length());
                            selectionStart = lastPos;
                            selectionEnd = lastPos + objectText.length();
                            // 设置背景色
                            editable.setSpan(new BackgroundColorSpan(mBackgroundColor), lastPos, lastPos + objectText.length(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            return;
                        }
                        lastPos += objectText.length();
                    }
                }
            }
            preTextLength = length;
            refreshEditTextUI(editable.toString());
        }
    };

    public void setContent() {
        String txt = getText().toString();
        if (topicBean == null)
            this.content = txt;
        else this.content = txt.replaceFirst(topicBean.getName(), "");
    }

    // 话题文本高亮颜色
    private int mForegroundColor = FOREGROUND_COLOR;

    // 话题背景高亮颜色
    private int mBackgroundColor = BACKGROUND_COLOR;

    private TopicBean topicBean;

    public TagEditTextView(Context context) {
        super(context);
        init();
    }

    public TagEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TagEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 监听光标的位置,若光标处于话题内容中间则移动光标到话题结束位置
     */
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (topicBean == null) {
            return;
        }
        int startPosition = 0;
        int endPosition = 0;
        String objectText = "";
        objectText = topicBean.getName();
        int length = getText().toString().length();
        while (true) {
            // 获取话题文本开始下标
            startPosition = getText().toString().indexOf(objectText, startPosition);
            endPosition = startPosition + objectText.length();
            if (startPosition == -1) {
                break;
            }
            // 若光标处于话题内容中间则移动光标到话题结束位置
            if (selStart > startPosition && selStart <= endPosition) {
                if ((endPosition + 1) > length) {
                    setSelection(endPosition);
                } else {
                    setSelection(endPosition + 1);
                }
                break;
            }
            startPosition = endPosition;
        }
    }

    public void setTopicBean(TopicBean tag) {
        this.topicBean = tag;
        refreshEditTextUI(getText().toString());
    }

    private void init() {
        this.addTextChangedListener(textWatcher);
    }


    private void refreshEditTextUI(String content) {
        /*
         * 内容变化时操作:
         * 1.查找匹配所有话题内容
         * 2.设置话题内容特殊颜色
         */
        if (topicBean == null)
            return;
        /*
         * 重新设置span
         */
        Editable editable = getText();
        int textLength = editable.length();

        int findPosition = 0;
        // 文本
        String objectText = topicBean.getName();
        while (findPosition <= length()) {
            // 获取文本开始下标
            findPosition = content.indexOf(objectText, findPosition);
            if (findPosition != -1) {
                // 设置话题内容前景色高亮
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(mForegroundColor);
                editable.setSpan(colorSpan, findPosition, findPosition + objectText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                findPosition += objectText.length();
            } else {
                break;
            }
        }
    }


    /**
     * 插入/设置话题
     *
     * @param object 话题对象
     */
    public void setObject(TopicBean object, boolean isAuto) {
        if (isAuto) {
            removeTextChangedListener(textWatcher);
        }
        String objectRule = "#";

        if (object == null) {
            return;
        }
        if (object.getName().startsWith("#") && object.getName().endsWith("#")) {
            objectRule = "";
        }
        String objectText = object.getName();
        if (TextUtils.isEmpty(objectText)) {
            return;
        }

        // 拼接字符# %s #,并保存
        objectText = objectRule + objectText + objectRule;
        object.setName(objectText.trim());

        /*
         * 1.添加话题内容到数据集合
         */
        topicBean = object;

        /*
         * 2.将话题内容添加到EditText中展示
         */
        // 光标位置
        int selectionStart = 0;
//        int selectionStart = getSelectionStart();
        // 原先内容
        Editable editable = getText();

        if (selectionStart >= 0) {
            // 在光标位置插入内容  话题后面插入空格,至关重要
            editable.insert(selectionStart, objectText + " ");
//            editable.insert(getSelectionStart(), " ");

            setSelection(getSelectionStart());// 移动光标到添加的内容后面
        }
        if (isAuto) {
            init();
        }
    }

    public String getContent() {
        setContent();
        return content;
    }

    /**
     * 获取object列表数据
     */
    public TopicBean getTopicBean() {
        // 由于保存时候文本内容添加了匹配字符#,此处去除,还原数据
        if (topicBean == null) return null;
        String objectText = topicBean.getName();
        String objectRule = "#";
        topicBean.setName(objectText.replace(objectRule, ""));// 将匹配规则字符替换
        return topicBean;
    }
}