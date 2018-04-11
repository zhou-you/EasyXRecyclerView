package com.zhouyou.recyclerview.anim;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.divider.HorizontalDividerItemDecoration;
import com.zhouyou.recyclerview.adapter.AnimationType;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：动画效果的XRecyclerView</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class AnimationActivity extends BaseActivity {

    private XRecyclerView superRecyclerView;
    private AnimationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_layout);
        initView();
        initAdapter();
    }

    private void initView() {
        superRecyclerView = (XRecyclerView) findViewById(R.id.superrecycleview_animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        superRecyclerView.setLayoutManager(layoutManager);
        superRecyclerView.setPullRefreshEnabled(false);
        superRecyclerView.setLoadingMoreEnabled(false);

        superRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                //.drawable(R.drawable.divider_sample)//.9图
                .drawable(R.drawable.divider_shape)//shape文件
                .size(3)
                .build());


        View emptyView = findViewById(R.id.tv_empty_view);
        superRecyclerView.setEmptyView(emptyView);
    }

    private void initAdapter() {
        mAdapter = new AnimationAdapter(this);
        mAdapter.setItemAnimation(AnimationType.SLIDE_FROM_LEFT);//设置显示的动画
        mAdapter.setShowItemAnimationEveryTime(true);//是否每次都会执行动画,默认是false,该方便测试
        superRecyclerView.setAdapter(mAdapter);

        List<String> dataList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            dataList.add("Animation"+i);
        }

        mAdapter.setListAll(dataList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alpha:
                mAdapter.setItemAnimation(AnimationType.ALPHA);
                break;
            case R.id.action_scale:
                mAdapter.setItemAnimation(AnimationType.SCALE);
                break;
            case R.id.action_slide_from_left:
                mAdapter.setItemAnimation(AnimationType.SLIDE_FROM_LEFT);
                break;
            case R.id.action_slide_from_right:
                mAdapter.setItemAnimation(AnimationType.SLIDE_FROM_RIGHT);
                break;
            case R.id.action_slide_from_bottom:
                mAdapter.setItemAnimation(AnimationType.SLIDE_FROM_BOTTOM);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
