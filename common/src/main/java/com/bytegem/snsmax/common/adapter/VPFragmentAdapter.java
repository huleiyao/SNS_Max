package com.bytegem.snsmax.common.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.bytegem.snsmax.common.bean.FragmentBean;

import java.util.ArrayList;


/**
 * Created by addis on 2017/5/19.
 */

public class VPFragmentAdapter extends FragmentStatePagerAdapter {

    /**
     * 用于版本控制，修改一次，请把这个数值+1，并在此备注修改人，修改时间等信息
     * 更改内容： 第一次创建
     * updata name : addis
     * updata time : 2017/5/19
     */
    private static final long serialVersionUID = 2L;
    private static final String TAG = "VPFragmentAdapter";

    private ArrayList<FragmentBean> list;

    public VPFragmentAdapter(FragmentManager fm, ArrayList<FragmentBean> list) {
        super(fm);
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return ((FragmentBean) list.get(position)).getFragment();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((FragmentBean) list.get(position)).getTitle();
    }
}
