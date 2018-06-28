package com.zhouyou.recyclerview.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhouyou.recyclerview.adapter.MenuAdapter;
import com.zhouyou.recyclerview.bean.Menu;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerview.divider.HorizontalDividerItemDecoration;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class GroupMainActivity extends BaseActivity {
    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                //.drawable(R.drawable.divider_sample)//.9图
                .drawable(R.drawable.divider_shape)//shape文件
                .size(10)
                .build());

        adapter.setListAll(getList());

        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<Menu>() {
            @Override
            public void onItemClick(View view, Menu item, int position) {
                switch (item.id) {
                    case 0:
                        gotoActivity(GroupedListActivity.class);
                        break;
                    case 1:
                        gotoActivity(NoHeaderActivity.class);
                        break;
                    case 2:
                        gotoActivity(NoFooterActivity.class);
                        break;
                    case 3:
                        gotoActivity(Grid1Activity.class);
                        break;
                    case 4:
                        gotoActivity(Grid2Activity.class);
                        break;
                    case 5:
                        gotoActivity(ExpandableActivity.class);
                        break;
                    case 6:
                        gotoActivity(VariousActivity.class);
                        break;
                    case 7:
                        gotoActivity(VariousChildActivity.class);
                        break;
                    case 8:
                        gotoActivity(StickyActivity.class);
                        break;
                    case 9:
                        gotoActivity(GroupedListHeaderFooterActivity.class);
                        break;
                    case 10:
                        gotoActivity(GroupGridHeaderFooterActivity.class);
                        break;
                    case 11:
                        gotoActivity(GroupedAppendStateListActivity.class);
                        break;
                    case 12:
                        gotoActivity(GroupedAppendStateGridActivity.class);
                        break;
                }
            }
        });

    }


    private List<Menu> getList() {
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(0, "分组的列表", ""));
        menus.add(new Menu(1, "不带组头列表", ""));
        menus.add(new Menu(2, "不带组尾的列表", ""));
        menus.add(new Menu(3, "子项为Grid的列表", ""));
        menus.add(new Menu(4, "子项为Grid的列表（各组子项的Span不同）", ""));
        menus.add(new Menu(5, "可展开收起的列表", ""));
        menus.add(new Menu(6, "头、尾和子项都支持多种类型的列表", ""));
        menus.add(new Menu(7, "多子项类型的列表", ""));
        menus.add(new Menu(8, "分组带有Sticky的列表", ""));
        menus.add(new Menu(9, "支持Xrecyclerview 子项为List的列表", ""));
        menus.add(new Menu(10, "支持Xrecyclerview 子项为Grid的列表", ""));
        menus.add(new Menu(11, "分组List显示状态页面（追加占位状态方式）", ""));
        menus.add(new Menu(12, "分组Grid显示状态页面（追加占位状态方式）", ""));
        return menus;
    }

    public void gotoActivity(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }
}
