package com.zhouyou.recyclerview.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zhouyou.recyclerview.group.adapter.VariousAdapter;
import com.zhouyou.recyclerview.group.bean.GroupBean;
import com.zhouyou.recyclerview.group.util.GroupModel;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

/**
 * 分组的列表
 */
public class VariousActivity extends BaseActivity {

    private RecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        rvList = (RecyclerView) findViewById(R.id.rv_list);


        //rvList.setLayoutManager(new LinearLayoutManager(this));
        VariousAdapter adapter = new VariousAdapter(this, GroupModel.getGroups(50,5));
        adapter.setOnHeaderClickListener(new com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter.OnHeaderClickListener<GroupBean>() {
            @Override
            public void onHeaderClick(com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter adapter, HelperRecyclerViewHolder holder, int groupPosition, GroupBean item) {
                Toast.makeText(VariousActivity.this, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnFooterClickListener(new com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter.OnFooterClickListener<GroupBean>() {
            @Override
            public void onFooterClick(com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter adapter, HelperRecyclerViewHolder holder,
                                      int groupPosition, GroupBean item) {
                Toast.makeText(VariousActivity.this, "组尾：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnChildClickListener(new com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter.OnChildClickListener<GroupBean>() {
            @Override
            public void onChildClick(com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter adapter, HelperRecyclerViewHolder holder,
                                     int groupPosition, int childPosition, GroupBean item) {
                Toast.makeText(VariousActivity.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
            }
        });


        //必须放在setAdapter之前，切记！切记！切记！
        rvList.setLayoutManager(new GridLayoutManager(this, 3));
        
        rvList.setAdapter(adapter);

    }
}
