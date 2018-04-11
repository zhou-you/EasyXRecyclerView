package com.zhouyou.recyclerview.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zhouyou.recyclerview.group.adapter.SickyGroupedListAdapter;
import com.zhouyou.recyclerview.group.util.GroupModel;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：普通布局Sticky效果</p>
 * 注意：
 * 1. 要给需要sticky的View设置tab属性：android:tag="sticky";
 * 2. 也可以Java动态设置：view.setTag("sticky");
 * 3. 如果这个sticky的View是可点击的，那么tag为：android:tag="sticky-nonconstant"或者view.setTag("sticky-nonconstant");
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2017/12/18 10:48 <br>
 * 版本： v1.0<br>
 */
public class GroupedStickyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color)));

        SickyGroupedListAdapter adapter = new SickyGroupedListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setGroups(GroupModel.getGroups(10,5));
    }
}
