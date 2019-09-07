package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.help.HelperBean;

import java.util.List;

public class HelperAdapter extends BaseAdapter {

    List<HelperBean> list;
    Context context;

    public HelperAdapter(List<HelperBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_proposal,null);
        }
        TextView tv = convertView.findViewById(R.id.txt_proposal);
        tv.setText(list.get(position).getTitle());
        return convertView;
    }
}
