package com.zhouyou.recyclerview.divider;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhouyou.recyclerview.adapter.MenuAdapter;
import com.zhouyou.recyclerview.bean.Menu;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class SkipDividerActivity extends BaseActivity {
    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(this);
        recyclerView.setAdapter(adapter);

        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{15.0f, 15.0f}, 0));
        recyclerView.addItemDecoration(new com.zhouyou.recyclerview.divider.HorizontalDividerItemDecoration.Builder(this)
                .paint(paint)
                .margin(15)
                //.showLastDivider()
                
                //表示分割线是否嵌入在Item内容上显示positionInsideItem，不是在两个item之间显示，
                // 这种情况主要用于如果分割线距左10px，那么会出现10px的尾图背景，
                // 采用positionInsideItem把分割线移到item上展示
                //.positionInsideItem(true)
                
                .startSkipCount(3)//跳过开头的3个分割线不展示 
                .endSkipCount(2)//跳过结尾的2个分割线不展示
                .build());


        adapter.setListAll(getList());
    }

    private List<Menu> getList() {
        List<Menu> menus = new ArrayList<>();
        int count = 50;
        for (int i = 0; i < count; i++) {
            menus.add(new Menu(i, "测试", ""));
        }
        return menus;
    }
}
