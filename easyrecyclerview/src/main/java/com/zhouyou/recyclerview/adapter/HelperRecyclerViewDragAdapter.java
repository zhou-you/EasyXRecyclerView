package com.zhouyou.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import com.zhouyou.recyclerview.listener.OnItemDragListener;
import com.zhouyou.recyclerviewsdk.R;


import java.util.Collections;
import java.util.List;

/**
 * <p>描述:继承于HelperRecyclerViewAdapter提供拖拽便捷操作的baseDragAdapter<br/></p>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/11/1 10:29<br>
 * 版本： v2.0<br>
 */
@SuppressWarnings(value={"unchecked", "deprecation"})
public abstract class HelperRecyclerViewDragAdapter<T> extends HelperRecyclerViewAdapter<T> {

    private static final int NO_TOGGLE_VIEW = 0;
    private int mToggleViewId = NO_TOGGLE_VIEW;
    private ItemTouchHelper mItemTouchHelper;
    private boolean itemDragEnabled = false;
    private boolean itemSwipeEnabled = false;
    private OnItemDragListener mOnItemDragListener;
    private boolean mDragOnLongPress = true;

    private View.OnTouchListener mOnToggleViewTouchListener;
    private View.OnLongClickListener mOnToggleViewLongClickListener;

    public HelperRecyclerViewDragAdapter(List<T> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public HelperRecyclerViewDragAdapter(Context context, int... layoutIds) {
        super(context, layoutIds);
    }

    public HelperRecyclerViewDragAdapter(List<T> mList, Context context) {
        super(mList, context);
    }

    @Override
    public void onBindViewHolder(BH holder, int position) {
        super.onBindViewHolder(holder, position);
        //int viewType = holder.getItemViewType();
        if (mItemTouchHelper != null && itemDragEnabled) {
            if (mToggleViewId != NO_TOGGLE_VIEW) {
                View toggleView = ((BaseRecyclerViewHolder) holder).getView(mToggleViewId);
                if (toggleView != null) {
                    toggleView.setTag(R.id.BaseQuickAdapter_viewholder_support, holder);
                    if (mDragOnLongPress) {
                        toggleView.setOnLongClickListener(mOnToggleViewLongClickListener);
                    } else {
                        toggleView.setOnTouchListener(mOnToggleViewTouchListener);
                    }
                }
            } else {
                holder.itemView.setTag(R.id.BaseQuickAdapter_viewholder_support, holder);
                holder.itemView.setOnLongClickListener(mOnToggleViewLongClickListener);
            }
        }
    }

    public void setToggleViewId(int toggleViewId) {
        mToggleViewId = toggleViewId;
    }

    /**
     * Set the drag event should be trigger on long press.
     * Work when the toggleViewId has been set.
     *
     * @param longPress by default is true.
     */
    public void setToggleDragOnLongPress(boolean longPress) {
        mDragOnLongPress = longPress;
        if (mDragOnLongPress) {
            mOnToggleViewTouchListener = null;
            mOnToggleViewLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemTouchHelper != null && itemDragEnabled) {
                        mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag(R.id.BaseQuickAdapter_viewholder_support));
                    }
                    return true;
                }
            };
        } else {
            mOnToggleViewTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN
                            && !mDragOnLongPress) {
                        if (mItemTouchHelper != null && itemDragEnabled) {
                            mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag(R.id.BaseQuickAdapter_viewholder_support));
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            mOnToggleViewLongClickListener = null;
        }
    }

    /**
     * Enable drag items.
     * Use itemView as the toggleView when long pressed.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper) {
        enableDragItem(itemTouchHelper, NO_TOGGLE_VIEW, true);
    }

    /**
     * Enable drag items. Use the specified view as toggle.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     * @param toggleViewId    The toggle view's id.
     * @param dragOnLongPress If true the drag event will be trigger on long press, otherwise on touch down.
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int toggleViewId, boolean dragOnLongPress) {
        itemDragEnabled = true;
        mItemTouchHelper = itemTouchHelper;
        setToggleViewId(toggleViewId);
        setToggleDragOnLongPress(dragOnLongPress);
    }

    /**
     * Disable drag items.
     */
    public void disableDragItem() {
        itemDragEnabled = false;
        mItemTouchHelper = null;
    }

    public boolean isItemDraggable() {
        return itemDragEnabled;
    }

    /**
     * <p>Enable swipe items.</p>
     */
    public void enableSwipeItem() {
        itemSwipeEnabled = true;
    }

    public void disableSwipeItem() {
        itemSwipeEnabled = false;
    }

    public boolean isItemSwipeEnable() {
        return itemSwipeEnabled;
    }

    /**
     * @param onItemDragListener Register a callback to be invoked when drag event happen.
     */
    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        mOnItemDragListener = onItemDragListener;
    }

    public int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
        //return viewHolder.getAdapterPosition() - getHeaderViewCount() -1;
        return viewHolder.getAdapterPosition();
    }

    public void onItemDragStart(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    public void onItemDragMoving(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        int from = getViewHolderPosition(source);
        int to = getViewHolderPosition(target);

        if (from < to) {
            for (int i = from; i < to; i++) {
                Collections.swap(getList(), i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                Collections.swap(getList(), i, i - 1);
            }
        }
        notifyItemMoved(source.getAdapterPosition(), target.getAdapterPosition());

        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragMoving(source, from, target, to);
        }
    }

    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragEnd(viewHolder, getViewHolderPosition(viewHolder));
        }
    }
}
