package com.zhouyou.recyclerview.state;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.divider.HorizontalDividerItemDecoration;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>描述：状态页面展示</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class ListEmptyStateActivity extends BaseActivity {
    private XRecyclerView mRecyclerView;
    private EmptyStateAdapter mAdapter;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (XRecyclerView) this.findViewById(R.id.main_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //设置分割线
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

       // mRecyclerView.setRefreshHeader((BaseRefreshHeader) RefreshLoadingManager.getManager().getCusRefreshHeader(this));
       // mRecyclerView.setLoadingMoreFooter((BaseLoadingFooter) RefreshLoadingManager.getManager().getCusLoadingFooter(this));


        addHeader();

        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.e("test", "=======isRefreshing=======" + mRecyclerView.isRefreshing());
                count = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        int value = new Random().nextInt(3);
                        if (value % 3 == 0) {//模拟空页面
                            mAdapter.clear();//执行clear()或者移除操作会自动进入空页面
                        } else if (value % 3 == 1) {//模拟错误页面
                            mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_ERROR);
                        } else {//模拟有数据
                            List<String> list = new ArrayList<String>();
                            for (int i = 0; i < 12; i++) {
                                String name = "刷新看效果" + i;
                                list.add(name);
                            }
                            mAdapter.setListAll(list);//不需要设置任何东西setListAll有数据了会自动到内容页面
                        }
                        mRecyclerView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
            }
        });

        mAdapter = new EmptyStateAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);//模拟加载中

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 12; i++) {
                    String name = "刷新看效果" + i;
                    list.add(name);
                }
                mAdapter.setListAll(list);
            }
        }, 5000);

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
}
