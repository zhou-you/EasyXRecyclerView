package com.zhouyou.recyclerview.header;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhouyou.recyclerview.adapter.MyAdapter;
import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class GridHeaderActivity extends AppCompatActivity {
    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<TestBean> listData;
    private int refreshTime = 0;
    private int times = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (XRecyclerView)this.findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.CubeTransition);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        //添加一个头部view
        View header =   LayoutInflater.from(this).inflate(R.layout.layout_header, (ViewGroup)findViewById(android.R.id.content),false);
        View header1 =   LayoutInflater.from(this).inflate(R.layout.layout_header, (ViewGroup)findViewById(android.R.id.content),false);
        mRecyclerView.addHeaderView(header);
        mRecyclerView.addHeaderView(header1);
        
        //添加一个地部view
        View footerView = getLayoutInflater().inflate(R.layout.layout_footer, (ViewGroup) mRecyclerView.getParent(), false);
        TextView textView = (TextView) footerView.findViewById(R.id.test_txt);
        textView.setText("我是尾部1，点我！");
        footerView.findViewById(R.id.test_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是尾部", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.addFooterView(footerView);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {

                        //不需要先clear  因为用setListAll就是覆盖了
                        //listData.clear();
                        List<TestBean> list = new ArrayList<TestBean>();
                        for(int i = 0; i < 10 ;i++){
                            //listData.add("item" + i + "after " + refreshTime + " times of refresh");
                            String name = "姓名：张三"+i;
                            String age = "年龄："+i;
                            TestBean testBean = new TestBean(name, age);
                            list.add(testBean);
                        }
                        mAdapter.setListAll(list);
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if(times < 10){
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            mRecyclerView.loadMoreComplete();
                            /*for(int i = 0; i < 20 ;i++){
                                listData.add("item" + (i + listData.size()) );
                            }
                            mAdapter.notifyDataSetChanged();*/
                            
                            //改造后的用法
                            List<TestBean> list = new ArrayList<TestBean>();
                            for (int i = 0; i < 10; i++) {
                                String name = "姓名：张三" + (i + listData.size());
                                String age = "年龄：" + (i + listData.size());
                                TestBean testBean = new TestBean(name, age);
                                list.add(testBean);
                            }

                            //mAdapter.notifyDataSetChanged();
                            //追加list.size()个数据到适配器集合最后面
                            //不需要 mAdapter.notifyDataSetChanged();
                            mAdapter.addItemsToLast(list);
                            
                            
                            mRecyclerView.refreshComplete();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);
                }
                times ++;
            }
        });

        listData = new ArrayList<TestBean>();
        for (int i = 0; i < 10; i++) {
            String name = "姓名：张三" + (i + listData.size());
            String age = "年龄：" + (i + listData.size());
            TestBean testBean = new TestBean(name, age);
            listData.add(testBean);
        }


        //方式四对应的初始化适配器   也可采用下面的构造方式创建对象  （自己选择）
        mAdapter = new MyAdapter(this);
        /****讲解*****/
        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
        //2.如果是addAll()追加
        //3.自己会刷新
        mAdapter.setListAll(listData);

        //方式一对应的初始化适配器
        //mAdapter = new MyAdapter(listData, this, R.layout.item);
        //方式二对应的初始化适配器
        //mAdapter = new MyAdapter(this, R.layout.item);
        
        mRecyclerView.setAdapter(mAdapter);
        
        //切记！切记！切记！
        //setLayoutManager方法一定要放在setAdapter之后，否则LayoutManager是GridLayoutManager的时候尾部脚显示错误
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
