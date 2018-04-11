package com.zhouyou.recyclerview.header;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.divider.HorizontalDividerItemDecoration;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：支持添加多个头和脚  具体使用参考代码</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class HeaderAndFooterActivity extends AppCompatActivity implements View.OnClickListener {
    private XRecyclerView mRecyclerView;
    private HeaderAndFooterAdapter mAdapter;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer);
        mRecyclerView = (XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //设置分割线
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        //mRecyclerView.setRefreshHeader((BaseRefreshHeader) RefreshLoadingManager.getManager().getCusRefreshHeader(this));
        //mRecyclerView.setLoadingMoreFooter((BaseLoadingFooter) RefreshLoadingManager.getManager().getCusLoadingFooter(this));

        findViewById(R.id.header).setOnClickListener(this);
        findViewById(R.id.footer).setOnClickListener(this);

        addHeader();
        addFooter();

        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.e("test", "=======isRefreshing=======" + mRecyclerView.isRefreshing());
                count = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        List<String> list = new ArrayList<String>();
                        for (int i = 0; i < 12; i++) {
                            String name = "我是按钮" + i;
                            list.add(name);
                        }
                        mAdapter.setListAll(list);
                        mRecyclerView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                Log.e("test", "=======onLoadMore=======" + mRecyclerView.isLoadingMore());
                if (count < 10) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //  mRecyclerView.loadMoreComplete();
                            List<String> list = new ArrayList<String>();
                            int size = mAdapter.getItemCount();//适配器中已有的数据
                            for (int i = 0; i < 12; i++) {
                                String age = "我是按钮" + (i + size);
                                list.add(age);
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
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.loadMoreComplete();
                    mRecyclerView.setLoadingMoreEnabled(false);
                    /*new Handler().postDelayed(new Runnable() {
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
                    }, 2000);*/
                }
                count++;
            }
        });

        //方式四对应的初始化适配器   也可采用下面的构造方式创建对象  （自己选择）
        //这种方式一定要先setAdapter然后才setListAll（）设置数据
        mAdapter = new HeaderAndFooterAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        //用框架里面的adapter时不需要再建立全局集合存放数据了，数据都和adapter绑定了，里面自带泛型集合
        //如果你外面还建立一个集合，那相当于占用内存两份了。。
        List<String> listData = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            String name = "我是按钮" + i;
            listData.add(name);
        }
        /****讲解*****/
        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
        //2.如果是addAll()追加
        //3.自己会刷新
        mAdapter.setListAll(listData);

        //设置item事件监听
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, String item, int position) {
                Toast.makeText(getApplicationContext(), "我是item " + position, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        mAdapter.clear();//清除掉适配器中数据
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header:
                addHeader();
                break;
            case R.id.footer:
                addFooter();
                break;
        }
    }

    View header;

    //添加头部
    private void addHeader() {
        header = getLayoutInflater().inflate(R.layout.layout_header, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.addHeaderView(header);
        header.findViewById(R.id.btn_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是头部", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //添加尾部
    private void addFooter() {
        View footerView = getLayoutInflater().inflate(R.layout.layout_footer, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.addFooterView(footerView);
        footerView.findViewById(R.id.test_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是尾部", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
