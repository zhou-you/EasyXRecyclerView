package com.zhouyou.recyclerview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.util.MakePicUtil;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：自定义适配器（请大家重点参考此类的使用方式和讲解）</p>
 * 
 * 一下代码会提供四种设置数据的构造<br/>
 * 
 * 本代码模拟采用方式四来举例，其实也是方式二的一个变种，大部分适配器都是对应一个item布局。<br/>
 * 
 * 传统的写法请参考：OldMyAdapter.java<br/>
 * 
 * 
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class MyAdapter extends HelperRecyclerViewAdapter<TestBean> {
    //以下提供适配器的几种构造使用方式，请选择自己喜欢的【一种】运用方式就可以了
    /*
    //方式一 data layoutId都从外部传入，例如：new MyAdapter(mList,this,R.layout.item)
    //注意layoutIds是表示可变参数，支持传入多个布局，用于支持多item布局，例如：new MyAdapter(mList,this,R.layout.item，R.layout.item2)
    public MyAdapter(List<TestBean> data, Context context, int... layoutIds) {
        super(data, context, layoutIds);
    }
    //方式二 layoutIds从外部传入，例如：MyAdapter  adapter = new MyAdapter(this,R.layout.item),数据传递用adapter.setListAll(mList);
    //注意layoutIds是表示可变参数，支持传入多个布局，用于支持多item布局，例如：new MyAdapter(this,R.layout.item，R.layout.item2)
    public MyAdapter(Context context, int... layoutIds) {
        super(context, layoutIds);
    }
    
    //方式三 数据从外部传入，布局直接写在里面（推荐使用方式），好处把适配器相关的item布局都放在本来中一块管理
    public MyAdapter(List<TestBean> mList, Context context) {
        super(mList, context,R.layout.item);//单item布局
        //super(mList, context,R.layout.item,R.layout.item2);//多item布局
    }*/

    //方式四 布局直接通过在构造方法中设置（推荐使用方式），数据集合通过setListAll设置
    public MyAdapter(Context context) {
        super(context, R.layout.item);
    }

   //不需要自己再自定义viewHolder类了 库里定义有viewHolder基类HelperRecyclerViewHolder
    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, TestBean item) {
        /****1.数据获取方式*****/
        //旧：传统的写法是从集合中获取再强转,如下：
        //TestBean testBean =(TestBean)datas.get(position);
        //新：baseadapter中提供的有获取当前postion位置对应的数据，直接调用就行了，也不用强转
        final TestBean testBean = getData(position);

        /****2.view赋值*****/
        //方式一：采用链式的设计的书写方式，一点到尾。（方式一）
        viewHolder.setText(R.id.text,testBean.getName())
                .setImageResource(R.id.image, MakePicUtil.makePic(position))
               /* .setVisible(R.id.text,true);//设置某个view是否可见*/
        .setOnClickListener(R.id.image, new View.OnClickListener() {//点击事件
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "我是子控件" + testBean.getName() + "请看我如何处理View点击事件的", Toast.LENGTH_LONG).show();
            }
        });
        //其它更多连写功能请查看viewHolder类中代码
        
        //方式二：不采用链式的方式，通过getView直接获取控件对象，不需要强转了，采用的是泛型
        TextView textView =viewHolder.getView(R.id.text2);
        textView.setText(testBean.getAge());

        /****3.其它更多使用方式，请自己探索*****/
        //举例如果想知道适配器中数据是否为空用isEmpty()就可以了，无需list.size()==0  list.isEmpty()等其它方式
        if(isEmpty()){
            
        }
    }

    /*******************以下两种item点击事件都可以，自己选择合适的方式**********************************/
    //方式一：此方式是另一种处理：绑定相关事件,例如点击长按等,默认空实现，如果你要使用需要覆写setListener()方法
    //方式二：绑定相关事件,例如点击长按等,默认空实现等我们一般会在适配器外部使用，
    // 例如： mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TestBean>(){});
    
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
