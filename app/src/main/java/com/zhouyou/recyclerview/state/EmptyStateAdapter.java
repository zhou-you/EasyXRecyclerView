package com.zhouyou.recyclerview.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerviewdemo.R;

import java.util.List;

/**
 * <p>描述：状态页面适配器</p>
 * 1.实现具体的getEmptyView，getErrorView，getLoadingView,虽然多了几个方法，但是代码更整洁清晰了，也利于你想定制任意的页面状态<br>
 * 2.如果你的每个页面状态都是一样的，不想每个页面都去实现getEmptyView，getErrorView，getLoadingView，可以自己再封装一个baseAdapter,处理公共的状态页面<br>
 * 作者： zhouyou<br>
 * 日期： 2018/1/19 17:51 <br>
 * 版本： v1.0<br>
 */
public class EmptyStateAdapter extends HelperStateRecyclerViewAdapter<String> {
    public EmptyStateAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    public EmptyStateAdapter(Context context) {
        super(context, R.layout.header_footer_item);
    }

    //不需要自己再自定义viewHolder类了 库里定义有viewHolder基类HelperRecyclerViewHolder
    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, String item) {
        final String value = getData(position);
        viewHolder.setText(R.id.test_btn, value);
    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        //空页面内容布局
        return mLInflater.inflate(R.layout.view_state_empty, parent, false);
    }

    @Override
    public View getErrorView(ViewGroup parent) {
        //错误页面布局
        return mLInflater.inflate(R.layout.view_state_error, parent, false);
    }

    @Override
    public View getLoadingView(ViewGroup parent) {
        //加载中页面布局
        return mLInflater.inflate(R.layout.view_state_loading, parent, false);
    }

    //onBindEmptyViewHolder、onBindErrorViewHolder、onBindLoadingViewHolder根据需要进行选择是否实现。
    @Override
    public void onBindEmptyViewHolder(HelperRecyclerViewHolder holder) {
        //修改空页面内容
        holder.setText(R.id.tv_empty_view, "我更新了空页面内容");
    }

    
   /* @Override
    public void onBindErrorViewHolder(HelperRecyclerViewHolder holder) {
        super.onBindErrorViewHolder(holder);
    }*/

   /* @Override
    public void onBindLoadingViewHolder(HelperRecyclerViewHolder holder) {
        super.onBindLoadingViewHolder(holder);
    }*/
}
