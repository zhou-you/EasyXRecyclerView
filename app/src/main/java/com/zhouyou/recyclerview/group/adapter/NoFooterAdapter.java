package com.zhouyou.recyclerview.group.adapter;

import android.content.Context;

import com.zhouyou.recyclerview.group.bean.GroupBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.ArrayList;

/**
 * 这是不带组尾的Adapter。
 * 只需要{@link GroupedRecyclerViewAdapter#hasFooter(int)}方法返回false就可以去掉组尾了。
 */
public class NoFooterAdapter extends GroupedListAdapter {

    public NoFooterAdapter(Context context, ArrayList<GroupBean> groups) {
        super(context, groups);
    }

    /**
     * 返回false表示没有组尾
     *
     */
    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    /**
     * 当hasFooter返回false时，这个方法不会被调用。
     *
     */
    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }


    /**
     * 当hasFooter返回false时，这个方法不会被调用。
     */
    @Override
    public void onBindFooterViewHolder(HelperRecyclerViewHolder holder, int groupPosition, GroupBean item) {
    }
}
