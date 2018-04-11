package com.zhouyou.recyclerview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.util.MakePicUtil;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：Grid适配器</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class GridAdapter extends com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter<TestBean> {
    public GridAdapter(Context context) {
        super(context, R.layout.item2);
    }

   //不需要自己再自定义viewHolder类了 库里定义有viewHolder基类HelperRecyclerViewHolder
    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, TestBean item) {
        //baseadapter中提供的有获取item的方法，直接调用就行了，也不用强转
        final TestBean testBean = getData(position);
        
        //采用链式的设计的书写方式，一点到尾。（方式一）
        viewHolder.setText(R.id.text,testBean.getName())
                .setImageResource(R.id.image, MakePicUtil.makePic(position))
        .setOnClickListener(R.id.image, new View.OnClickListener() {//点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "我是子控件" + testBean.getName() + "请看我如何处理View点击事件的", Toast.LENGTH_LONG).show();
            }
        });

        //设置某个view是否可见
        //.setVisible(R.id.text,true);
    }
}
