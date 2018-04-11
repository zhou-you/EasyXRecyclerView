package com.zhouyou.recyclerview.header;

import android.content.Context;
import android.widget.TextView;

import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.util.MakePicUtil;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：自定义适配器（请大家重点参考此类的使用方式和讲解）</p>
 * <p>
 * 一下代码会提供四种设置数据的构造<br/>
 * <p>
 * 本代码模拟采用方式四来距离，其实也是方式二的一个变种，大部分适配器都是对应一个item布局。<br/>
 * <p>
 * 传统的写法请参考：OldMyAdapter.java<br/>
 * <p>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class HeaderMyAdapter extends HelperRecyclerViewAdapter<TestBean> {
    
/*
    //方式一
    public MyAdapter(List<TestBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }
    //方式二
    public MyAdapter(Context context, int... layoutIds) {
        super(context, layoutIds);
    }
    
    //方式三
    public MyAdapter(List<TestBean> mList, Context context) {
        super(mList, context);
    }
*/

    /***方式四***/
    public HeaderMyAdapter(Context context) {
        super(context, R.layout.header_item);
    }

    //不需要自己再自定义viewHolder类了 库里定义有viewHolder基类HelperRecyclerViewHolder
    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, TestBean item) {
        //传统的写法是从集合中获取再强转,如下：
        //TestBean testBean =(TestBean)datas.get(position);

        //baseadapter中提供的有获取item的方法，直接调用就行了，也不用强转
        final TestBean testBean = getData(position);

        //采用链式的设计的书写方式，一点到尾。（方式一）
        viewHolder.setText(R.id.text, testBean.getName())
                .setImageResource(R.id.image, R.mipmap.ic_launcher).setImageResource(R.id.image, MakePicUtil.makePic(position));
       /* .setOnClickListener(R.id.image, new View.OnClickListener() {//点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "我是子控件" + testBean.getName() + "请看我如何处理View点击事件的", Toast.LENGTH_LONG).show();
            }
        });*/

        //设置某个view是否可见
        //.setVisible(R.id.text,true);

        //其它更多连写功能请查看viewHolder类中代码

        //通过getView直接获取控件对象，不需要强转了，采用的是泛型（方式二）
        TextView textView = viewHolder.getView(R.id.text2);
        textView.setText(testBean.getAge());


        //举例  如果想知道适配器中数据是否为空isEmpty()  就可以了  无需list.size()==0  list.isEmpty()等其它方式
        if (isEmpty()) {

        }
    }

    /*******************注意**********************************/
    //方式一：此方式是另一种处理：绑定相关事件,例如点击长按等,默认空实现，如果你要使用需要覆写setListener()方法
    //方式二：绑定相关事件,例如点击长按等,默认空实现等我们一般会在适配器外部使用，
    // 例如： mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TestBean>(){});

    //以上两种item点击事件都可以，自己选择合适的方式
    
   /* @Override
    protected void setListener(HelperRecyclerViewHolder viewHolder, final int position, TestBean item) {
        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"我是Item："+position,Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
