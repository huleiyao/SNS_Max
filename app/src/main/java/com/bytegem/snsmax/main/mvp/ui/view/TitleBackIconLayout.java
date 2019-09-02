package com.bytegem.snsmax.main.mvp.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TitleBackIconLayout extends RelativeLayout {
    public TitleBackIconLayout(Context context) {
        this(context,null);
    }

    public TitleBackIconLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBackIconLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public TitleBackIconLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr();
    }

    private void initAttr(){
        setOnClickListener(v->{
            if(getContext() instanceof Activity){
                ((Activity) getContext()).finish();
            }
        });
    }
}
