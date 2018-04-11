package com.zhouyou.recyclerview.group.adapter;

import android.content.Context;

import com.zhouyou.recyclerview.group.bean.GroupBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.ArrayList;

/**
 * 这是不带组头的Adapter。
 *只需要{@link GroupedRecyclerViewAdapter#hasHeader(int)}方法返回false就可以去掉组尾了。
 */
public class NoHeaderAdapter extends GroupedListAdapter {

    public NoHeaderAdapter(Context context, ArrayList<GroupBean> groups) {
        super(context, groups);
    }

    /**
     * 返回false表示没有组头
     *
     */
    @Override
    public boolean hasHeader(int groupPosition) {
        return false;
    }

    /**
     * 当hasHeader返回false时，这个方法不会被调用。
     *
     */
    @Override
    public int getHeaderLayout(int viewType) {
        return 0;
    }

    /**
     * 当hasHeader返回false时，这个方法不会被调用。
     */
    @Override
    public void onBindHeaderViewHolder(HelperRecyclerViewHolder holder, int groupPosition, GroupBean item) {
    }
}
