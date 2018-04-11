package com.zhouyou.recyclerview.divider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhouyou.recyclerview.adapter.MenuAdapter;
import com.zhouyou.recyclerview.bean.Menu;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>描述：分割线住界面</p>
 * 作者： zhouyou<br>
 * 日期： 2017/12/22 13:38 <br>
 * 版本： v1.0<br>
 */
public class DividerMainActivity extends BaseActivity {
    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new com.zhouyou.recyclerview.divider.HorizontalDividerItemDecoration.Builder(this)
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
                        gotoActivity(SimpleListDividerActivity.class);
                        break;
                    case 1:
                        gotoActivity(PaintDividerActivity.class);
                        break;
                    case 2:
                        gotoActivity(DrawableDividerActivity.class);
                        break;
                    case 3:
                        gotoActivity(ComplexDividerActivity.class);
                        break;
                    case 4:
                        gotoActivity(SimpleGridDividerActivity.class);
                        break;
                    case 5:
                        gotoActivity(SkipDividerActivity.class);
                        break;
                    case 6:
                        gotoActivity(SmartDividerActivity.class);
                        break;
                }
            }
        });

    }

    private List<Menu> getList() {
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(0, "简单的列表分割线", ""));
        menus.add(new Menu(1, "Paint 分割线", ""));
        menus.add(new Menu(2, "Drawable/shape 分割线", ""));
        menus.add(new Menu(3, "复杂的分割线", ""));
        menus.add(new Menu(4, "简单的Grid分割线", ""));
        menus.add(new Menu(5, "Skip 分割线", ""));
        menus.add(new Menu(6, "Smart(智能) 分割线", ""));
        return menus;
    }

    public void gotoActivity(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }
}
