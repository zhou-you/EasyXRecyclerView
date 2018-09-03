package com.zhouyou.recyclerview.group;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.ViewGroup;

import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * <p>描述：提供分组便捷操作,可设置状态的错误页面、空页面、加载中页面、内容页面自由切换</p>
 * 作者： zhouyou<br>
 * 日期： 2018/1/26 17:45 <br>
 * 版本： v1.0<br>
 */
@SuppressWarnings("unchecked")
public abstract class GroupedStateRecyclerViewAdapter<T>
        extends GroupedRecyclerViewAdapter<T> {

    @IntDef({STATE_NORMAL, STATE_LOADING, STATE_EMPTY, STATE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static final int STATE_NORMAL = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_ERROR = 3;

    public static final int TYPE_LOADING = 1000;
    public static final int TYPE_EMPTY = 1001;
    public static final int TYPE_ERROR = 1002;

    @State
    protected int state = STATE_NORMAL;

    public GroupedStateRecyclerViewAdapter(Context context, List list) {
        super(context, list);
    }

    public GroupedStateRecyclerViewAdapter(Context context) {
        super(context);
    }

    public void setState(@State int state) {
        this.state = state;
        if (xRecyclerView != null) {
            switch (state) {
                case STATE_LOADING:
                case STATE_EMPTY:
                case STATE_ERROR:
                    xRecyclerView.setEnabledScroll(false);
                    break;
                case STATE_NORMAL://恢复之前的状态
                    xRecyclerView.setEnabledScroll(true);
                    break;
            }
        }
        notifyDataSetChanged();
    }

    public int getState() {
        return state;
    }

    @Override
    public int getItemCount() {
        switch (state) {
            case STATE_LOADING:
            case STATE_EMPTY:
            case STATE_ERROR:
                return 1;
        }
        return super.getItemCount();
    }


    @Override
    public int getItemViewType(int position) {
        switch (state) {
            case STATE_LOADING:
                return TYPE_LOADING;
            case STATE_EMPTY:
                return TYPE_EMPTY;
            case STATE_ERROR:
                return TYPE_ERROR;
        }
        return itemViewType(position);
    }

    protected int itemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public HelperRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOADING:
                return new HelperRecyclerViewHolder(getLoadingView(parent),0);
            case TYPE_EMPTY:
                return new HelperRecyclerViewHolder(getEmptyView(parent),0);
            case TYPE_ERROR:
                return new HelperRecyclerViewHolder(getErrorView(parent),0);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(HelperRecyclerViewHolder viewHolder, int position) {
        switch (state) {
            case STATE_LOADING:
                onBindLoadingViewHolder(viewHolder);
                break;
            case STATE_EMPTY:
                onBindEmptyViewHolder(viewHolder);
                break;
            case STATE_ERROR:
                onBindErrorViewHolder(viewHolder);
                break;
            default:
                viewHolder(viewHolder, position);
                break;
        }
    }

    public void viewHolder(HelperRecyclerViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
    }

    public void onBindErrorViewHolder(HelperRecyclerViewHolder holder) {
    }

    public void onBindEmptyViewHolder(HelperRecyclerViewHolder holder) {
    }

    public void onBindLoadingViewHolder(HelperRecyclerViewHolder holder) {
    }

    public abstract View getEmptyView(ViewGroup parent);

    public abstract View getErrorView(ViewGroup parent);

    public abstract View getLoadingView(ViewGroup parent);

    private void invalidateState() {
        if (super.getItemCount() > 0) {
            setState(STATE_NORMAL);
        } else {
            setState(STATE_EMPTY);
        }
    }

    private int count() {
        return countGroupRangeItem(0, mStructures.size());
    }


    @Override
    public boolean setGroups(List<T> datas) {
        boolean result = super.setGroups(datas);
        invalidateState();
        return result;
    }

    @Override
    public void removeAll() {
        super.removeAll();
        invalidateState();
    }

    @Override
    public void removeGroup(int groupPosition) {
        super.removeGroup(groupPosition);
        invalidateState();
    }

    @Override
    public void removeRangeGroup(int groupPosition, int count) {
        super.removeRangeGroup(groupPosition, count);
        invalidateState();
    }

    @Override
    public void insertGroup(int groupPosition) {
        super.insertGroup(groupPosition);
        invalidateState();
    }

    @Override
    public void insertRangeGroup(int groupPosition, int count) {
        super.insertRangeGroup(groupPosition, count);
        invalidateState();
    }

    @Override
    public void insertHeader(int groupPosition) {
        super.insertHeader(groupPosition);
        invalidateState();
    }

    @Override
    public void insertFooter(int groupPosition) {
        super.insertFooter(groupPosition);
        invalidateState();
    }

    @Override
    public void insertRangeChild(int groupPosition, int childPosition, int count) {
        super.insertRangeChild(groupPosition, childPosition, count);
        invalidateState();
    }
}