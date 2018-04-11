package com.zhouyou.recyclerview.header;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhouyou.recyclerview.LinearActivity;
import com.zhouyou.recyclerview.adapter.MyAdapter;
import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：添加头部-水平滑动 布局  具体使用参考代码</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class HorizontalHeaderActivity extends AppCompatActivity {
    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

//        mRecyclerView.setRefreshProgressStyle(ProgressStyle.ClifeIndicator);
//        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.ClifeIndicator);
//        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
//        mRecyclerView.setRefreshHeader((BaseRefreshHeader) RefreshLoadingManager.getManager().getCusRefreshHeader(this));
//        mRecyclerView.setLoadingMoreFooter((BaseLoadingFooter) RefreshLoadingManager.getManager().getCusLoadingFooter(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        //mRecyclerView.setRefreshHeader((BaseRefreshHeader) RefreshLoadingManager.getManager().getCusRefreshHeader(this));
        //mRecyclerView.setLoadingMoreFooter((BaseLoadingFooter) RefreshLoadingManager.getManager().getCusLoadingFooter(this));

        //添加头部view
        //View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup) findViewById(android.R.id.content), false);
        View header = LayoutInflater.from(this).inflate(R.layout.horizontal_recyclerview, (ViewGroup) findViewById(android.R.id.content), false);
        LinearLayoutManager header_layoutManager = new LinearLayoutManager(this);
        header_layoutManager.setAutoMeasureEnabled(true);
        header_layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView headerRecyclerView= (RecyclerView) header.findViewById(R.id.head_recyclerview);
        headerRecyclerView.setLayoutManager(header_layoutManager);
        HeaderMyAdapter headerAdapter = new HeaderMyAdapter(this);
        headerRecyclerView.setAdapter(headerAdapter);
        List<TestBean> header_listData = new ArrayList<TestBean>();
        for (int i = 0; i < 10; i++) {
            String name = "姓名：" + i;
            String age = "年龄：" + i;
            TestBean testBean = new TestBean(name, age);
            header_listData.add(testBean);
        }
        /****讲解*****/
        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
        //2.如果是addAll()追加
        //3.自己会刷新
        headerAdapter.setListAll(header_listData);
        headerAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                Intent intent = new Intent(HorizontalHeaderActivity.this, LinearActivity.class);
                startActivity(intent);
            }
        });
        
//        View header2 = LayoutInflater.from(this).inflate(R.layout.bind_device_indicate_header_layout, (ViewGroup) findViewById(android.R.id.content), false);
//        mRecyclerView.addHeaderView(header);
//        mRecyclerView.addHeaderView(header2);
        mRecyclerView.addHeaderView(header);
        //mRecyclerView.setLoadingMoreEnabled(false);
        //mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.e("test","=======isRefreshing======="+mRecyclerView.isRefreshing());
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
                        List<TestBean> list = new ArrayList<TestBean>();
                        for (int i = 0; i < 3; i++) {
                            String name = "刷新 姓名 张三" + i;
                            String age = "刷新 年龄：" + i;
                            TestBean testBean = new TestBean(name, age);
                            list.add(testBean);
                        }
                        /****讲解*****/
                        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                        //2.如果是addAll()追加
                        mAdapter.setListAll(list);
                        //mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
//                        mRecyclerView.setLoadingMoreEnabled(true);
                    }

                }, 2000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                Log.e("test","=======onLoadMore======="+mRecyclerView.isLoadingMore());
                if (times < 20) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //  mRecyclerView.loadMoreComplete();
                            List<TestBean> list = new ArrayList<TestBean>();
                            int size = mAdapter.getItemCount();//适配器中已有的数据
                            for (int i = 0; i < 10; i++) {
                                String name = "更多 姓名：张三" + (i + size);
                                String age = "更多 年龄：" + (i + size);
                                TestBean testBean = new TestBean(name, age);
                                list.add(testBean);
                            }

                            //mAdapter.notifyDataSetChanged();
                            //追加list.size()个数据到适配器集合最后面
                            //不需要 mAdapter.notifyDataSetChanged();
                            mAdapter.addItemsToLast(list);

                            mRecyclerView.refreshComplete();
                            mRecyclerView.setLoadingMoreEnabled(true);
                        }
                    }, 2000);
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
                            }, 2000);
                        }
                    }, 2000);
                }
                times++;
            }
        });

        //用框架里面的adapter时不需要再建立全局集合存放数据了，数据都和adapter绑定了，里面自带泛型集合
        //如果你外面还建立一个集合，那相当于占用内存两份了。。
        List<TestBean> listData = new ArrayList<TestBean>();

        //方式四对应的初始化适配器   也可采用下面的构造方式创建对象  （自己选择）
        //这种方式一定要先setAdapter然后才setListAll（）设置数据
        mAdapter = new MyAdapter(this);
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
                Toast.makeText(getApplicationContext(),"我是item "+position,Toast.LENGTH_LONG).show();
            }
        });

      
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mRecyclerView !=null){
            mRecyclerView.setRefreshing(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
