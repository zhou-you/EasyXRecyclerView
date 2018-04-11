package com.zhouyou.recyclerview.header;

import android.content.Context;

import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：自定义适配器（请大家重点参考此类的使用方式和讲解）</p>
 * 
 * 一下代码会提供四种设置数据的构造<br/>
 * 
 * 本代码模拟采用方式四来距离，其实也是方式二的一个变种，大部分适配器都是对应一个item布局。<br/>
 * 
 * 传统的写法请参考：OldMyAdapter.java<br/>
 * 
 * 
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class HeaderAndFooterAdapter extends HelperRecyclerViewAdapter<String> {
    
    public HeaderAndFooterAdapter(Context context) {
        super(context, R.layout.header_footer_item);
    }

   //不需要自己再自定义viewHolder类了 库里定义有viewHolder基类HelperRecyclerViewHolder
    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, String item) {
        final String value = getData(position);
        viewHolder.setText(R.id.test_btn,value);
        /*.setOnClickListener(R.id.image, new View.OnClickListener() {//点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "我是子控件" +value + "请看我如何处理View点击事件的", Toast.LENGTH_LONG).show();
            }
        });*/
    }
}
