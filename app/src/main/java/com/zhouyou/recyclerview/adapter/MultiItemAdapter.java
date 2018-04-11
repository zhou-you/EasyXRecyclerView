package com.zhouyou.recyclerview.adapter;

import android.content.Context;

import com.zhouyou.recyclerview.bean.MultipleItemBean;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：多item适配器</p>
 *
 * 作者： zhouyou<br>
 * 日期： 2016/10/31 16:24<br>
 * 版本： v2.0<br>
 */
public class MultiItemAdapter extends com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter<MultipleItemBean> {

    public MultiItemAdapter(Context context) {
        super(context, R.layout.adapter_multi_item1_layout,R.layout.adapter_multi_item2_layout,R.layout.adapter_multi_item3_layout);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, MultipleItemBean item) {
        if(item.getItemType() == 0){
            viewHolder.setText(R.id.name_tv,item.getName());
        } else if(item.getItemType() == 1){
            viewHolder.setText(R.id.name_tv,item.getName())
                    .setText(R.id.info_tv,item.getAge());
        }
    }

    //不重写的时候返回默认是0，也就是只会加载第一个布局
    @Override
    public int checkLayout(MultipleItemBean item, int position) {
        return item.getItemType();
    }
}
