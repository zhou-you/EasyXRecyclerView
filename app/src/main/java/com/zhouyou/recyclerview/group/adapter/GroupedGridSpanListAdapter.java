package com.zhouyou.recyclerview.group.adapter;

import android.content.Context;

import com.zhouyou.recyclerview.group.bean.GroupBean;

import java.util.List;

/**
 * <p>描述：子项为Grid布局的分组列表。给RecyclerView的LayoutManager</p>
 * 设置各子项不同的SPAN
 * 作者： zhouyou<br>
 * 日期： 2017/12/19 17:08 <br>
 * 版本： v1.0<br>
 */
public class GroupedGridSpanListAdapter extends GroupedListAdapter {
    public GroupedGridSpanListAdapter(Context context) {
        super(context);
    }

    public GroupedGridSpanListAdapter(Context context, List<GroupBean> list) {
        super(context, list);
    }

    /**
     * 如果不需要设置不同的Grid  SPAN，可以不用覆写此方法
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public int getChildSpanSize(int groupPosition, int childPosition) {
        //定义子项为不同的item
        if (groupPosition % 2 == 1) {//例如分组对2求余是1的用2
            return 2;
        } else if (groupPosition % 2 == 2) {
            return 3;
        }
        return super.getChildSpanSize(groupPosition, childPosition);
    }
}
