package com.zhouyou.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zhouyou.recyclerview.XRecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * <p>描述:提供便捷操作,可设置状态的错误页面、空页面、加载中页面、内容页面自由切换<br/></p>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/8/25 10:29<br>
 * 版本： v2.0<br>
 */
@SuppressWarnings("unchecked")
public abstract class HelperStateRecyclerViewAdapter<T> extends HelperRecyclerViewAdapter<T> {
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
    private int state = STATE_NORMAL;

    public HelperStateRecyclerViewAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public HelperStateRecyclerViewAdapter(Context context, int... layoutIds) {
        super(context, layoutIds);
    }

    public HelperStateRecyclerViewAdapter(List mList, Context context) {
        super(mList, context);
    }

    private XRecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (recyclerView instanceof XRecyclerView) {
            mRecyclerView = (XRecyclerView) recyclerView;
        }
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setState(@State int state) {
        this.state = state;
        if (mRecyclerView != null) {
            switch (state) {
                case STATE_LOADING:
                case STATE_EMPTY:
                case STATE_ERROR:
                    mRecyclerView.setEnabledScroll(false);
                    break;
                case STATE_NORMAL://恢复之前的状态
                    mRecyclerView.setEnabledScroll(true);
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
        return super.getItemViewType(position);
    }

    @Override
    public BH onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOADING:
                return new HelperRecyclerViewHolder(getLoadingView(parent), 0);
            case TYPE_EMPTY:
                return new HelperRecyclerViewHolder(getEmptyView(parent), 0);
            case TYPE_ERROR:
                return new HelperRecyclerViewHolder(getErrorView(parent), 0);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BH viewHolder, int position) {
        switch (state) {
            case STATE_LOADING:
                onBindLoadingViewHolder((HelperRecyclerViewHolder) viewHolder);
                break;
            case STATE_EMPTY:
                onBindEmptyViewHolder((HelperRecyclerViewHolder) viewHolder);
                break;
            case STATE_ERROR:
                onBindErrorViewHolder((HelperRecyclerViewHolder) viewHolder);
                break;
            default:
                super.onBindViewHolder(viewHolder, position);
                break;
        }
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

    @Override
    public boolean addAll(int startPosition, List<T> datas) {
        boolean b = super.addAll(startPosition, datas);
        invalidateState();
        return b;
    }

    @Override
    public boolean addItemsToLast(List<T> datas) {
        boolean b = super.addItemsToLast(datas);
        invalidateState();
        return b;
    }

    @Override
    public void add(int startPosition, T data) {
        super.add(startPosition, data);
        invalidateState();
        notifyDataSetChanged();
    }

    @Override
    public void alterObj(int index, T data) {
        super.alterObj(index, data);
        invalidateState();
        notifyDataSetChanged();
    }


    @Override
    public boolean remove(T data) {
        boolean b = super.remove(data);
        invalidateState();
        notifyDataSetChanged();
        return b;
    }

    @Override
    public void removeToIndex(int index) {
        super.removeToIndex(index);
        invalidateState();
        notifyDataSetChanged();
    }


    @Override
    public boolean setListAll(List<T> datas) {
        boolean result = super.setListAll(datas);
        invalidateState();
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void clear() {
        super.clear();
        invalidateState();
        notifyDataSetChanged();
    }
}
