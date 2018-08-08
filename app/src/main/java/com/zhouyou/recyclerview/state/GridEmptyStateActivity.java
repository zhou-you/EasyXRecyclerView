package com.zhouyou.recyclerview.state;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerview.manager.StateGridLayoutManager;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridEmptyStateActivity extends BaseActivity {
    private XRecyclerView mRecyclerView;
    private EmptyStateAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (XRecyclerView)this.findViewById(R.id.recyclerview);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.CubeTransition);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        //添加一个头部view
        View header =   LayoutInflater.from(this).inflate(R.layout.layout_header, (ViewGroup)findViewById(android.R.id.content),false);
        mRecyclerView.addHeaderView(header);

        //添加一个尾部view
        View footerView = getLayoutInflater().inflate(R.layout.layout_footer, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.addFooterView(footerView);

        mRecyclerView.setLoadingMoreEnabled(false);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        int value = new Random().nextInt(3);
                        if (value % 3 == 0) {//模拟错误
                            mAdapter.clear();
                            mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_ERROR);
                        } else if (value % 3 == 1) {//模拟为空
                            mAdapter.setState(HelperStateRecyclerViewAdapter.STATE_EMPTY);
                        } else {//模拟有数据
                            List<String> list = new ArrayList<String>();
                            for (int i = 0; i < 40; i++) {
                                String name = "刷新看效果" + i;
                                list.add(name);
                            }
                            mAdapter.setListAll(list);
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
                for (int i = 0; i < 10; i++) {
                    String name = "刷新看效果" + i;
                    list.add(name);
                }
                mAdapter.setListAll(list);
            }
        }, 2000);

        //如果用网格Manager,必须用StateGridLayoutManager，否则Grid状态页面会不起作用
        StateGridLayoutManager layoutManager = new StateGridLayoutManager(this,3);
        //切记！切记！切记！
        //setLayoutManager方法一定要放在setAdapter之后，否则LayoutManager是GridLayoutManager的时候尾部脚显示错误
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
