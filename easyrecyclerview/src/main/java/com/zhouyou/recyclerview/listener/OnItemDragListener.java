package com.zhouyou.recyclerview.listener;

import android.support.v7.widget.RecyclerView;

/**
 * <p>描述:设置拖拽监听<br/></p>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/11/1 14:29<br>
 * 版本： v2.0<br>
 */
public interface OnItemDragListener {
    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos);

    void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to);

    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos);
}
