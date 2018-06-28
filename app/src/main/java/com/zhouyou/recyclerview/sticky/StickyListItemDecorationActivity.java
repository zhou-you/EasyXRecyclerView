package com.zhouyou.recyclerview.sticky;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.sticky.adapter.ListItemDecorationAdapter;
import com.zhouyou.recyclerview.sticky.adapter.StockInfo;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：通过ItemDecoration实现 list布局的Sticky效果</p>
 * 
 * 这种方式主要是通过ItemDecoration实现。此种好处是能够实现悬停，又不影响刷新和加载更多，
 * 不会有事件冲突的问题
 * 作者： zhouyou<br>
 * 日期： 2018/6/27 10:48 <br>
 * 版本： v1.0<br>
 */
public class StickyListItemDecorationActivity extends BaseActivity {
    PinnedHeaderItemDecoration mHeaderItemDecoration;
    XRecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = (XRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        
        mHeaderItemDecoration = new PinnedHeaderItemDecoration.Builder(StockInfo.TYPE_HEADER).setDividerId(R.drawable.divider).enableDivider(true)
                /*.setClickIds(R.id.iv_more, R.id.fl, R.id.checkbox)*/.disableHeaderClick(false)/*.setHeaderClickListener(clickAdapter)*/.create();
        //mHeaderItemDecoration.disableDrawHeader(true);
        recyclerView.addItemDecoration(mHeaderItemDecoration);
        ListItemDecorationAdapter adapter = new ListItemDecorationAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setListAll(getItems());
       
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener(){
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.refreshComplete();   
                    }
                },10000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.loadMoreComplete();
                    }
                },2000);
            }
        });
    }

    /**
     * 创建模拟数据
     * 带有分组数据信息的集合
     */
    private List<StockInfo> getItems() {
        List<StockInfo> itemTypes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            if(i%5==0){
                itemTypes.add(new StockInfo(StockInfo.TYPE_HEADER,"头" + (i/5+1),""));
            }
            itemTypes.add(new StockInfo(StockInfo.TYPE_DATA,"第" + i%5 + "个Item",""));
        }
        return itemTypes;
    }
}
