package com.bytegem.snsmax.main.app.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;

import java.util.List;

/**
 * Author: Ly
 * Data：2018/5/30-19:08
 * Description:
 */
public class TagTextView extends android.support.v7.widget.AppCompatTextView {
    public interface TopicListener {
        void topicListener(TopicBean topicBean);
    }

    private StringBuffer content_buffer;
    private TextView tv_tag;
    private View view;//标签布局的最外层布局
    private Context mContext;
    private TopicListener listener;
    private TopicBean mTopicBean;
//必须重写所有的构造器，否则可能会出现无法inflate布局的错误！

    public TagTextView(Context context) {
        super(context);
        mContext = context;
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setListener(TopicListener listener) {
        this.listener = listener;
    }

    public TopicBean getmTopicBean() {
        return mTopicBean;
    }

    public void setContentAndTag(String content, TopicBean topicBean) {
        mTopicBean = topicBean;
        if (topicBean == null) {
            setText(content);
            return;
        }
        String tag = topicBean.getName();
        tag = "#" + tag + "# ";
        content_buffer = new StringBuffer();
        content_buffer.append(tag);
        content_buffer.append(content);
        SpannableString spannableString = new SpannableString(content_buffer);
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag, null);//R.layout.tag是每个标签的布局
        tv_tag = view.findViewById(R.id.tv_tag);
        tv_tag.setText(tag);
        tv_tag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) listener.topicListener(topicBean);
            }
        });
        Drawable d = new BitmapDrawable(convertViewToBitmap(view));
        d.setBounds(0, 0, tv_tag.getWidth(), tv_tag.getHeight());//缺少这句的话，不会报错，但是图片不回显示
//            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);//图片将对齐底部边线
        spannableString.setSpan(new MyIm(d), 0, tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannableString);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setContentAndTags(String content, List<String> tags) {
        content_buffer = new StringBuffer();
        for (String item : tags) {//将每个tag的内容添加到content后边，之后将用drawable替代这些tag所占的位置
            content_buffer.append(item);
        }
        content_buffer.append(content);
        SpannableString spannableString = new SpannableString(content_buffer);
        for (int i = 0; i < tags.size(); i++) {
            String item = tags.get(i);
            View view = LayoutInflater.from(mContext).inflate(R.layout.tag, null);//R.layout.tag是每个标签的布局
            tv_tag = view.findViewById(R.id.tv_tag);
            tv_tag.setText("#" + item + "# ");
            Bitmap bitmap = convertViewToBitmap(view);
            Drawable d = new BitmapDrawable(bitmap);
            d.setBounds(0, 0, tv_tag.getWidth(), tv_tag.getHeight());//缺少这句的话，不会报错，但是图片不回显示
            MyIm span = new MyIm(d);//图片将对齐底部边线
//            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);//图片将对齐底部边线
            int startIndex;
            int endIndex;
            startIndex = getLastLength(tags, i);
            endIndex = startIndex + item.length();
            spannableString.setSpan(span, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannableString);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    private static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    private int getLastLength(List<String> list, int maxLength) {
        int length = 0;
        for (int i = 0; i < maxLength; i++) {
            length += list.get(i).length();
        }
        return length;
    }

    public class MyIm extends ImageSpan {
        public MyIm(Context arg0, int arg1) {
            super(arg0, arg1);
        }

        public MyIm(Drawable arg0) {
            super(arg0);
        }

        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fm) {
            Drawable d = getDrawable();
            Rect rect = d.getBounds();
            if (fm != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;

                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 4;

                fm.ascent = -bottom;
                fm.top = -bottom;
                fm.bottom = top;
                fm.descent = top;
            }
            return rect.right;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end,
                         float x, int top, int y, int bottom, Paint paint) {
            Drawable b = getDrawable();
            canvas.save();
            int transY = 0;
            transY = ((bottom - top) - b.getBounds().bottom) / 2 + top;
            canvas.translate(x, transY);
            b.draw(canvas);
            canvas.restore();
        }
    }
}