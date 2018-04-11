package com.zhouyou.recyclerview.header;

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

/**
 * <p>描述：Sticky效果</p>
 * 作者： zhouyou<br>
 * 日期： 2017/12/18 10:37 <br>
 * 版本： v1.0<br>
 */
public class HeaderMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MenuAdapter adapter = new MenuAdapter(this);
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
                        gotoActivity(HeaderAndFooterActivity.class);
                        break;
                    case 1:
                        gotoActivity(HorizontalHeaderActivity.class);
                        break;
                    case 2:
                        gotoActivity(GridHeaderActivity.class);
                        break;
                }
            }
        });

    }

    private List<Menu> getList() {
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(0, "添加头部和尾部", ""));
        menus.add(new Menu(1, "带水平滑动的头部", ""));
        menus.add(new Menu(2, "Grid添加头部和尾部", ""));
        return menus;
    }

    public void gotoActivity(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }
}
