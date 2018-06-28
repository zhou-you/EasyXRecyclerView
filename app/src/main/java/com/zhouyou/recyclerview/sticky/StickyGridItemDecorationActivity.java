package com.zhouyou.recyclerview.sticky;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerview.sticky.adapter.GridItemDecorationAdapter;
import com.zhouyou.recyclerview.sticky.adapter.StockInfo;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：通过ItemDecoration实现 Grid布局的Sticky效果</p>
 * 
 * 注：暂时不支持XRecyclerView,切记，切记，切记！
 * 
 * 作者： zhouyou<br>
 * 日期： 2018/6/27 10:48 <br>
 * 版本： v1.0<br>
 */
public class StickyGridItemDecorationActivity extends BaseActivity {
    PinnedHeaderItemDecoration mHeaderItemDecoration;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3,GridLayoutManager.VERTICAL, false));
        
        mHeaderItemDecoration = new PinnedHeaderItemDecoration.Builder(StockInfo.TYPE_HEADER).setDividerId(R.drawable.divider).enableDivider(true)
                /*.setClickIds(R.id.iv_more, R.id.fl, R.id.checkbox)*/.disableHeaderClick(false)/*.setHeaderClickListener(clickAdapter)*/.create();
        //mHeaderItemDecoration.disableDrawHeader(true);
        recyclerView.addItemDecoration(mHeaderItemDecoration);
        
        
        GridItemDecorationAdapter adapter = new GridItemDecorationAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setListAll(getItems());
    }

    /**
     * 创建模拟数据
     * 带有分组数据信息的集合
     */
    private List<StockInfo> getItems() {
        List<StockInfo> itemTypes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemTypes.add(new StockInfo(StockInfo.TYPE_HEADER,"头" + (i+1),""));
            for (int k = 0; k < 5; k++) {
                itemTypes.add(new StockInfo(StockInfo.TYPE_DATA,"第" + k + "个Item",""));
            }
        }
        return itemTypes;
    }
    
}
