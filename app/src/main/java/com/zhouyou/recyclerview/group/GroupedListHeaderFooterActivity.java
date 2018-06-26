package com.zhouyou.recyclerview.group;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.divider.HorizontalDividerItemDecoration;
import com.zhouyou.recyclerview.group.adapter.GroupedStickyListAdapter;
import com.zhouyou.recyclerview.group.bean.GroupBean;
import com.zhouyou.recyclerview.group.util.GroupModel;
import com.zhouyou.recyclerviewdemo.R;

/**
 * 分组的列表
 */
public class GroupedListHeaderFooterActivity extends BaseActivity {

    private XRecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_recyclerview);

        rvList = (XRecyclerView) findViewById(R.id.recyclerview);
        //rvList.setPullRefreshEnabled(true);
        //rvList.setLoadingMoreEnabled(true);

        View header = getLayoutInflater().inflate(R.layout.layout_header, (ViewGroup) rvList.getParent(), false);
        rvList.addHeaderView(header);
        header.findViewById(R.id.btn_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是头部", Toast.LENGTH_SHORT).show();
            }
        });

        //添加一个地部view
        View footerView = getLayoutInflater().inflate(R.layout.layout_footer, (ViewGroup) rvList.getParent(), false);
        TextView textView = (TextView) footerView.findViewById(R.id.test_txt);
        textView.setText("我是尾部1，点我！");
        footerView.findViewById(R.id.test_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是尾部", Toast.LENGTH_SHORT).show();
            }
        });
        rvList.addFooterView(footerView);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        //设置分割线
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));

        rvList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .paint(paint)//分割线采用paint
                //.showLastDivider()
                .margin(60, 0)//距离左边60px
                //.positionInsideItem(true)//分割线是否在item里面
                .startSkipCount(2)//设置跳过开头的2条数据不要分割线
                .endSkipCount(2)//设置跳过结尾的2条数据不要分割线
                .build());
        GroupedStickyListAdapter adapter = new GroupedStickyListAdapter(this, GroupModel.getGroups(10, 5));
        adapter.setOnHeaderClickListener(new com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter.OnHeaderClickListener<GroupBean>() {
            @Override
            public void onHeaderClick(com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter adapter, HelperRecyclerViewHolder holder, int groupPosition, GroupBean item) {
                Toast.makeText(GroupedListHeaderFooterActivity.this, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnFooterClickListener(new com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter.OnFooterClickListener<GroupBean>() {
            @Override
            public void onFooterClick(com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter adapter, HelperRecyclerViewHolder holder,
                                      int groupPosition, GroupBean item) {
                Toast.makeText(GroupedListHeaderFooterActivity.this, "组尾：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnChildClickListener(new com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter.OnChildClickListener<GroupBean>() {
            @Override
            public void onChildClick(com.zhouyou.recyclerview.group.GroupedRecyclerViewAdapter adapter, HelperRecyclerViewHolder holder,
                                     int groupPosition, int childPosition, GroupBean item) {
                Toast.makeText(GroupedListHeaderFooterActivity.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        rvList.setAdapter(adapter);

    }
}
