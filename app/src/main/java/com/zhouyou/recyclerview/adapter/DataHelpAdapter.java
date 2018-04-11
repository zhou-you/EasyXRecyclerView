package com.zhouyou.recyclerview.adapter;

import android.content.Context;

import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.util.MakePicUtil;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：展示数据规范接口使用适配器</p>
 *
 * 作者： zhouyou<br>
 * 日期： 2016/10/31 16:24<br>
 * 版本： v2.0<br>
 */
public class DataHelpAdapter extends com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter<TestBean> {
    public DataHelpAdapter(Context context) {
        super(context, R.layout.adapter_draggable_layout);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, TestBean item) {
        viewHolder.setText(R.id.tv,item.getName()).setVisible(R.id.iv,false).setImageResource(R.id.image, MakePicUtil.makePic(position));
    }
}
