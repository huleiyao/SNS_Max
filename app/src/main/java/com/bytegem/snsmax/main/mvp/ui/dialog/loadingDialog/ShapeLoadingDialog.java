package com.bytegem.snsmax.main.mvp.ui.dialog.loadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.bytegem.snsmax.R;


public class ShapeLoadingDialog {

    private Context mContext;
    private Dialog mDialog;
    private LoadingView mLoadingView;
    private View mDialogContentView;

    public ShapeLoadingDialog(Context context) {
        this.mContext = context;
        init();
    }

    // Binary XML file line #35: Error inflating class
    // com.example.dialog_demo.ShapeLoadingView
    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog);
        mDialogContentView = LayoutInflater.from(mContext).inflate(
                R.layout.layout_dialog, null);

        mLoadingView =  mDialogContentView
                .findViewById(R.id.loadView);
        mDialog.setContentView(mDialogContentView);
        mDialog.setCancelable(false);
    }

    public void setBackground(int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) mDialogContentView
                .getBackground();
        gradientDrawable.setColor(color);
    }

    public void setLoadingText(CharSequence charSequence) {
        mLoadingView.setLoadingText(charSequence);
    }

    public void show() {
        mDialog.show();

    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
    }
}
