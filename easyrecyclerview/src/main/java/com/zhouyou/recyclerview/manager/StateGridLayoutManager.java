package com.zhouyou.recyclerview.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * <p>描述：自定义GridLayoutManager</p>
 * 主要用于，如果你是GridLayoutManager布局有3列，但是要显示空页面的时候需要1列，也就是整个一个地方都是空白<br/>
 * 作者： zhouyou<br>
 * 日期： 2018/1/20 11:22 <br>
 * 版本： v1.0<br>
 */
public class StateGridLayoutManager extends GridLayoutManager {
    private static final int DEFAULT_SPAN_SIZE = 1;
    private int mSpanCount;

    public StateGridLayoutManager(Context context, int spanCount) {
        super(context, DEFAULT_SPAN_SIZE);
        mSpanCount = spanCount;
    }

    public StateGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, DEFAULT_SPAN_SIZE, orientation, reverseLayout);
        mSpanCount = spanCount;
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        int span = getItemCount() == DEFAULT_SPAN_SIZE ? DEFAULT_SPAN_SIZE : mSpanCount;
        setSpanCount(span);
        super.onItemsChanged(recyclerView);
    }
}
