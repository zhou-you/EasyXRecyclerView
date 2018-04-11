package com.zhouyou.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhouyou.recyclerview.adapter.MultiItemAdapter;
import com.zhouyou.recyclerview.bean.MultipleItemBean;
import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>描述：不同item的适配器举例 具体使用参考代码</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class MultiItemActivity extends BaseActivity {
    private com.zhouyou.recyclerview.XRecyclerView mRecyclerView;
    private MultiItemAdapter mAdapter;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (com.zhouyou.recyclerview.XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        View header = LayoutInflater.from(this).inflate(R.layout.layout_header, (ViewGroup) findViewById(android.R.id.content), false);
        mRecyclerView.addHeaderView(header);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(new com.zhouyou.recyclerview.XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        //不需要先clear  用了setListAll里面就是覆盖&刷新
                        //listData.clear();
                        /*********传统的方式添加一条数据 add后再通知刷新***************/
                        /*for(int i = 0; i < 10 ;i++){
                            String name = "item name" + i + "after " + refreshTime + " times of refresh";
                            String age = "item age" + i + "after " + refreshTime + " times of refresh";
                            TestBean testBean = new TestBean(name,age);
                            listData.add(testBean);
                        }
                        mAdapter.notifyDataSetChanged();*/
                        List<MultipleItemBean> list = new ArrayList<MultipleItemBean>();
                        for (int i = 0; i < 10; i++) {
                            int type = new Random().nextInt(3);
                            String name = "我是item type"+type+" name:" + i;
                            String age = "我是item type"+type+" age:" + i;
                            MultipleItemBean testBean = new MultipleItemBean(name, age);
                            testBean.setItemType(type);
                            list.add(testBean);
                        }
                        /****讲解*****/
                        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                        //2.如果是addAll()追加
                        mAdapter.setListAll(list);
                        //mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                        mRecyclerView.setLoadingMoreEnabled(true);
                    }

                }, 5000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //  mRecyclerView.loadMoreComplete();
                            List<MultipleItemBean> list = new ArrayList<MultipleItemBean>();
                            int size = mAdapter.getItemCount();//适配器中已有的数据
                            for (int i = 0; i < 10; i++) {
                                int type = new Random().nextInt(3);
                                String name = "我是item type"+type+" name:" + (i+size);
                                String age = "我是item type"+type+" age:" + (i+size);
                                MultipleItemBean testBean = new MultipleItemBean(name, age);
                                testBean.setItemType(type);
                                list.add(testBean);
                            }

                            //mAdapter.notifyDataSetChanged();
                            //追加list.size()个数据到适配器集合最后面
                            //不需要 mAdapter.notifyDataSetChanged();
                            mAdapter.addItemsToLast(list);

                            mRecyclerView.refreshComplete();
                            mRecyclerView.setLoadingMoreEnabled(true);
                        }
                    }, 5000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setLoadingMoreEnabled(false);

                            //
                            Log.e("", "");
                            new Handler().postDelayed(new Runnable() {
                                public void run() {

                                    mAdapter.notifyDataSetChanged();
                                    mRecyclerView.loadMoreComplete();
                                    mRecyclerView.setLoadingMoreEnabled(true);

                                    //
                                    Log.e("", "");
                                }
                            }, 3000);
                        }
                    }, 5000);
                }
                times++;
            }
        });

        //用框架里面的adapter时不需要再建立全局集合存放数据了，数据都和adapter绑定了，里面自带泛型集合
        //如果你外面还建立一个集合，那相当于占用内存两份了。。
        List<MultipleItemBean> listData = new ArrayList<MultipleItemBean>();
        for (int i = 0; i < 10; i++) {
            int type= new Random().nextInt(3);
            String name = "我是item type"+type+" name:" +i;
            String age = "我是item type"+type+" age:" + i;
            MultipleItemBean testBean = new MultipleItemBean(name, age);
            testBean.setItemType(type);
            listData.add(testBean);
        }

        //方式四对应的初始化适配器   也可采用下面的构造方式创建对象  （自己选择）
        //这种方式一定要先setAdapter然后才setListAll（）设置数据
        mAdapter = new MultiItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        
        /****讲解*****/
        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
        //2.如果是addAll()追加
        //3.自己会刷新
        mAdapter.setListAll(listData);

        //方式一对应的初始化适配器
        //mAdapter = new MyAdapter(listData, this, R.layout.item);
        //方式二对应的初始化适配器
        //mAdapter = new MyAdapter(this, R.layout.item);


        //设置item事件监听
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(View view, TestBean item, int position) {
                Toast.makeText(getApplicationContext(),"我是item type "+position,Toast.LENGTH_LONG).show();
            }
        });

      
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
