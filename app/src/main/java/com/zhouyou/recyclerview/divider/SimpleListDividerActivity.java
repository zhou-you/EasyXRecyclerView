package com.zhouyou.recyclerview.divider;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhouyou.recyclerview.adapter.MenuAdapter;
import com.zhouyou.recyclerview.bean.Menu;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleListDividerActivity extends BaseActivity {
    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        recyclerView.addItemDecoration(new com.zhouyou.recyclerview.divider.HorizontalDividerItemDecoration.Builder(this).build());
        
        adapter = new MenuAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setListAll(getList());
    }

    private List<Menu> getList() {
        List<Menu> menus = new ArrayList<>();
        int count=50;
        for (int i = 0; i < count; i++) {
            menus.add(new Menu(i, "测试", ""));
        }
        return menus;
    }
}
