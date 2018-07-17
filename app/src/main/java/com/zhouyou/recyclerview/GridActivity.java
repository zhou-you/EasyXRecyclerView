package com.zhouyou.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhouyou.recyclerview.adapter.GridAdapter;
import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：格子布局  具体使用参考代码</p>
 * 不满一屏也可以加载更多<br>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class GridActivity extends BaseActivity {
    private com.zhouyou.recyclerview.XRecyclerView mRecyclerView;
    private GridAdapter mAdapter;
    private ArrayList<TestBean> listData;
    private int refreshTime = 0;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (com.zhouyou.recyclerview.XRecyclerView) this.findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.CubeTransition);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        //添加一个头部view
        View header = LayoutInflater.from(this).inflate(R.layout.layout_header, (ViewGroup) findViewById(android.R.id.content), false);
        mRecyclerView.addHeaderView(header);

        mRecyclerView.setLoadingListener(new com.zhouyou.recyclerview.XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime++;
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //不需要先clear  因为用setListAll就是覆盖了
                        //listData.clear();
                        List<TestBean> list = new ArrayList<TestBean>();
                        for (int i = 0; i < 20; i++) {
                            TestBean testBean = new TestBean(i + "", "");
                            list.add(testBean);
                        }
                        mAdapter.setListAll(list);
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRecyclerView.loadMoreComplete();

                            //改造后的用法
                            List<TestBean> list = new ArrayList<TestBean>();
                            int temp = mAdapter.getList().size();
                            for (int i = 0; i < 20; i++) {
                                TestBean testBean = new TestBean((i + temp) + "", "");
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
                            mRecyclerView.setLoadingMoreEnabled(false);
                        }
                    }, 1000);
                }
                times++;
            }
        });

        listData = new ArrayList<TestBean>();
        for (int i = 0; i < 20; i++) {
            TestBean testBean = new TestBean(i + "", "");
            listData.add(testBean);
        }

        //方式四对应的初始化适配器   也可采用下面的构造方式创建对象  （自己选择）
        mAdapter = new GridAdapter(this);
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

        //设置item事件监听
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(View view, TestBean item, int position) {
                Toast.makeText(getApplicationContext(), "我是item " + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
