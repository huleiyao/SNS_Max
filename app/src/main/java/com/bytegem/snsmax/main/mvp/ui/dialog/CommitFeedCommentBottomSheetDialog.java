package com.bytegem.snsmax.main.mvp.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bytegem.snsmax.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.Unbinder;

public class CommitFeedCommentBottomSheetDialog extends BottomSheetDialog {
    private Unbinder mUnbinder;
    View.OnClickListener onClickListener;
    TextView.OnEditorActionListener onEditorActionListener;

    @OnClick(R.id.dialog_send_comment)
    void onClick(View view) {
        onClickListener.onClick(view);
    }

    @OnEditorAction(R.id.commit_content)
    boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return onEditorActionListener.onEditorAction(textView, i, keyEvent);
    }

    public CommitFeedCommentBottomSheetDialog(@NonNull Context context, View.OnClickListener onClickListener, TextView.OnEditorActionListener onEditorActionListener) {
        super(context);
        setContentView(R.layout.view_commit);
        getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(context.getResources().getColor(R.color.albumTransparent));
        mUnbinder = ButterKnife.bind(this);
        this.onClickListener = onClickListener;
        this.onEditorActionListener = onEditorActionListener;
    }

    public CommitFeedCommentBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected CommitFeedCommentBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void unBind() {
        mUnbinder.unbind();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unBind();
    }
}
