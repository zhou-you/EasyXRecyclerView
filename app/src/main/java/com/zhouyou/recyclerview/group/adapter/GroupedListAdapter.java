package com.zhouyou.recyclerview.group.adapter;

import android.content.Context;

import com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter;
import com.zhouyou.recyclerview.group.bean.GroupBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerviewdemo.R;

import java.util.List;

/**
 * <p>描述：（这里用一句话描述这个类的作用)</p>
 * 作者： zhouyou<br>
 * 日期： 2017/12/19 17:08 <br>
 * 版本： v1.0<br>
 */
public class GroupedListAdapter extends GroupedRecyclerViewAdapter<GroupBean> {
    public GroupedListAdapter(Context context) {
        super(context);
    }

    public GroupedListAdapter(Context context, List<GroupBean> list) {
        super(context, list);
    }

    @Override
    public int getGroupCount() {
        return getGroups().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).getChildren().size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.adapter_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.adapter_footer;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.adapter_child;
    }

    @Override
    public void onBindHeaderViewHolder(HelperRecyclerViewHolder holder, int groupPosition, GroupBean item) {
        //也可以通过Group获取
        //GroupEntity entity =getGroup(groupPosition);
        holder.setText(R.id.tv_header, item.getHeader());
    }

    @Override
    public void onBindFooterViewHolder(HelperRecyclerViewHolder holder, int groupPosition, GroupBean item) {
        holder.setText(R.id.tv_footer, item.getFooter());
    }

    @Override
    public void onBindChildViewHolder(HelperRecyclerViewHolder holder, int groupPosition, int childPosition, GroupBean item) {
        holder.setText(R.id.tv_child, item.getChildren().get(childPosition).getChild());
    }
}
