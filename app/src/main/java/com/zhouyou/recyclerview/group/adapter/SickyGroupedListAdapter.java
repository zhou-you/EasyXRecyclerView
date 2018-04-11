package com.zhouyou.recyclerview.group.adapter;

import android.content.Context;

import com.zhouyou.recyclerview.group.bean.GroupBean;
import com.zhouyou.recyclerviewdemo.R;

import java.util.List;

/**
 * <p>描述：（这里用一句话描述这个类的作用)</p>
 * 作者： zhouyou<br>
 * 日期： 2017/12/19 17:08 <br>
 * 版本： v1.0<br>
 */
public class SickyGroupedListAdapter extends GroupedListAdapter{
    public SickyGroupedListAdapter(Context context) {
        super(context);
    }

    public SickyGroupedListAdapter(Context context, List<GroupBean> list) {
        super(context, list);
    }

    @Override
    public int getHeaderLayout(int viewType) {//覆写 使用带有Sticky标签tag的布局
        return R.layout.adapter_header_sticky;
    }
}
