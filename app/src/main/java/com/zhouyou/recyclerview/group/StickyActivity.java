package com.zhouyou.recyclerview.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.group.adapter.GroupedListStickyAdapter;
import com.zhouyou.recyclerview.group.bean.GroupBean;
import com.zhouyou.recyclerview.group.util.GroupModel;
import com.zhouyou.recyclerviewdemo.R;


/**
 * 分组的列表
 */
public class StickyActivity extends BaseActivity {

    private RecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_item);

        rvList = (RecyclerView) findViewById(R.id.recycler_view);


        rvList.setLayoutManager(new LinearLayoutManager(this));
        GroupedListStickyAdapter adapter = new GroupedListStickyAdapter(this, GroupModel.getGroups(10, 5));
        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener<GroupBean>() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, HelperRecyclerViewHolder holder, int groupPosition, GroupBean item) {
                Toast.makeText(StickyActivity.this, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener<GroupBean>() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, HelperRecyclerViewHolder holder,
                                     int groupPosition, int childPosition, GroupBean item) {
                Toast.makeText(StickyActivity.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        rvList.setAdapter(adapter);

    }
}
