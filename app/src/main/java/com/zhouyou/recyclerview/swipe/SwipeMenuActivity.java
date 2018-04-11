package com.zhouyou.recyclerview.swipe;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.recyclerview.bean.MultipleItemBean;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.swipemenu.SwipeMenuRecyclerView;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;
public class SwipeMenuActivity extends BaseActivity implements SwipeMenuRecyclerView.LoadingListener{

    private SwipeMenuRecyclerView superSwipeMenuRecyclerView;
    private SwipeMenuAdapter swipeMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipemenu_layout);
        initView();
        initAdapter();
    }

    private void initView(){
        superSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.super_swipemenu_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superSwipeMenuRecyclerView.setLayoutManager(layoutManager);
        superSwipeMenuRecyclerView.setPullRefreshEnabled(true);
        superSwipeMenuRecyclerView.setLoadingMoreEnabled(true);
        superSwipeMenuRecyclerView.setLoadingListener(this);
        superSwipeMenuRecyclerView.setSwipeDirection(SwipeMenuRecyclerView.DIRECTION_LEFT);//左滑（默认）
    }
    private void initAdapter(){
        swipeMenuAdapter = new SwipeMenuAdapter(this);
        swipeMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<MultipleItemBean>() {
            @Override
            public void onItemClick(View view, MultipleItemBean item, int position) {
                Toast.makeText(SwipeMenuActivity.this, item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        
        superSwipeMenuRecyclerView.setAdapter(swipeMenuAdapter);

        List<MultipleItemBean> dataList = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++){
            MultipleItemBean bean = new MultipleItemBean("姓名:张三"+i,"年龄:"+i);
            bean.setItemType(i%2);
            dataList.add(bean);
        }
        
        swipeMenuAdapter.setListAll(dataList);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                superSwipeMenuRecyclerView.refreshComplete();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                superSwipeMenuRecyclerView.loadMoreComplete();
            }
        }, 2000);
    }
}
