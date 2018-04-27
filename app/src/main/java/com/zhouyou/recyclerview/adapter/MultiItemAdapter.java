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
        //设置多item布局adapter_multi_item1_layout，adapter_multi_item2_layout，adapter_multi_item3_layout
        super(context, R.layout.adapter_multi_item1_layout,R.layout.adapter_multi_item2_layout,R.layout.adapter_multi_item3_layout);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, MultipleItemBean item) {
        //方式一，对应checkLayout中的方式一
        if(item.getItemType() == 0){//adapter_multi_item1_layout布局对应的操作
            viewHolder.setText(R.id.name_tv,item.getName());
        } else if(item.getItemType() == 1){//adapter_multi_item2_layout布局对应的操作
            viewHolder.setText(R.id.name_tv,item.getName())
                    .setText(R.id.info_tv,item.getAge());
        }

        /*//方式二，对应checkLayout中的方式二
        int layoutType = getItemViewType(position);
        if(layoutType==R.layout.adapter_multi_item1_layout){
            viewHolder.setText(R.id.name_tv,item.getName());
        }else if(layoutType==R.layout.adapter_multi_item2_layout){
            viewHolder.setText(R.id.name_tv,item.getName())
                    .setText(R.id.info_tv,item.getAge());
        }*/
    }

    /*********多item布局使用方式***********/
    //如果要用多item布局，必须重写checkLayout()方法，来指定哪一条数据对应哪个item布局文件
    //不重写的时候返回默认是0，也就是只会加载第一个布局
    @Override
    public int checkLayout(MultipleItemBean item, int position) {
        //方式一：判断的类型直接写在model中
        return item.getItemType();
        //方式二：根据类型判断
        /*if(item instanceof A){
            return R.layout.adapter_multi_item1_layout;
        }else if(item instanceof B){
            return R.layout.adapter_multi_item2_layout;
        }else {
            return R.layout.adapter_multi_item3_layout;
        }*/
    }
}
