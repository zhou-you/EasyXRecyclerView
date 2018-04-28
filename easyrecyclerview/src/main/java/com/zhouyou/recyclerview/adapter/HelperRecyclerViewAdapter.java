package com.zhouyou.recyclerview.adapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述:提供便捷操作的baseAdapter<br/></p>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/8/25 10:29<br>
 * 版本： v2.0<br>
 */
@SuppressWarnings("unchecked")
public abstract class HelperRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T>
        implements DataHelper<T> {

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public HelperRecyclerViewAdapter(List<T> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public HelperRecyclerViewAdapter(Context context, int... layoutIds) {
        super(context, layoutIds);
    }

    @Deprecated
    public HelperRecyclerViewAdapter(List<T> mList, Context context) {
        super(mList, context);
    }


    @Override
    protected BaseRecyclerViewHolder createViewHolder(View view, int layoutId) {
        return new HelperRecyclerViewHolder(view, layoutId);
    }

    @Override
    protected void onBindData(BH viewHolder, int position, T item) {
        HelperRecyclerViewHolder helperViewHolder = (HelperRecyclerViewHolder) viewHolder;

        HelperBindData(helperViewHolder, position, item);

        //1.赋值相关事件,例如点击长按等
        //2.也可用低二种方式，用BaseRecyclerViewAdapter类中的 adapter.setOnItemClickListener()
        setListener(helperViewHolder, position, item);
    }

    protected abstract void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, T item);

    /**
     * 绑定相关事件,例如点击长按等,默认空实现
     *
     * @param viewHolder
     * @param position   数据的位置
     * @param item       数据项
     */
    protected void setListener(HelperRecyclerViewHolder viewHolder, int position, T item) {

    }


    @Override
    public boolean isEnabled(int position) {
        return position >= 0 && position < mList.size();
    }

    @Override
    public void addItemToHead(T data) {
        add(0, data);
    }


    @Override
    public boolean addItemToLast(T data) {
        if (mList == null)
            return false;
        boolean result = mList.add(data);
        notifyDataSetChanged();
        return result;
    }


    @Override
    public boolean addItemsToHead(List<T> datas) {
        return addAll(0, datas);
    }


    @Override
    public boolean addItemsToLast(List<T> datas) {
        if (mList == null)
            return false;
        boolean b = mList.addAll(datas);
        notifyDataSetChanged();
        return b;
    }

    /**
     * 设置了item动画必须用notifyItemRangeInserted刷新适配器，否则没有动画效果
     *
     * @param datas
     * @return
     */
    public boolean addAndNotifyItems(List<T> datas) {
        if (datas == null || datas.isEmpty()) {
            return false;
        }
        int pos = mList.size();
        boolean b = mList.addAll(datas);
        notifyItemRangeInserted(pos, datas.size());
        return b;
    }


    @Override
    public boolean addAll(int startPosition, List<T> datas) {
        if (mList == null || datas == null)
            return false;
        boolean result = mList.addAll(startPosition, datas);
        notifyDataSetChanged();
        return result;
    }


    @Override
    public void add(int startPosition, T data) {
        if (mList == null || data == null) return;
        mList.add(startPosition, data);
        notifyDataSetChanged();
    }

    @Override
    public T getData(int index) {
        return getItemCount() == 0 ? null : mList.get(index);
    }


    @Override
    public void alterObj(T oldData, T newData) {
        alterObj(mList.indexOf(oldData), newData);
    }


    @Override
    public void alterObj(int index, T data) {
        if (mList == null || data == null) return;
        mList.set(index, data);
        notifyDataSetChanged();
    }


    @Override
    public boolean remove(T data) {
        boolean result = false;
        if (data == null) return result;
        result = mList.remove(data);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void removeToIndex(int index) {
        if (mList == null) return;
        mList.remove(index);
        notifyDataSetChanged();
    }


    @Override
    public void replaceAll(List<T> data) {
        if (mList != null)
            mList.clear();
        addAll(0, data);
    }

    @Override
    public boolean setListAll(List<T> datas) {
        if (mList == null) mList = new ArrayList<>();
        mList.clear();
        boolean result = false;
        if (datas != null && !datas.isEmpty()) {
            result = mList.addAll(datas);
        }
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void clear() {
        if (mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public boolean contains(T data) {
        if (mList == null || mList.isEmpty()) return false;
        return mList.contains(data);
    }

    public List<T> getList() {
        return mList;
    }

}
