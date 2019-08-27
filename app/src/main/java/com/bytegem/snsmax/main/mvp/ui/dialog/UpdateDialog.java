package com.bytegem.snsmax.main.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bytegem.snsmax.R;

public class UpdateDialog extends Dialog {

    //定义回调事件，用于dialog的点击事件
    public interface OnUpdateDialogListener {
        void back(String name);
    }

    private String name;
    private static OnUpdateDialogListener updateDialogListener;
    EditText editText;
    TextView textView;

    public UpdateDialog(Context context, String name, OnUpdateDialogListener updateDialogListener) {
        super(context);
        this.name = name;
        this.updateDialogListener = updateDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        textView = findViewById(R.id.dialog_edit_title);
        textView.setText(name);
        editText = findViewById(R.id.dialog_edit_txt);
        Button btnUpdate = findViewById(R.id.dialog_update);
        Button btnCancel = findViewById(R.id.dialog_cancel);
        btnUpdate.setOnClickListener(updateClickListener);
        btnCancel.setOnClickListener(cancelClickListener);
    }

    private View.OnClickListener cancelClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            UpdateDialog.this.dismiss();
        }
    };

    private View.OnClickListener updateClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            updateDialogListener.back(String.valueOf(editText.getText()));
            UpdateDialog.this.dismiss();
        }
    };
}
