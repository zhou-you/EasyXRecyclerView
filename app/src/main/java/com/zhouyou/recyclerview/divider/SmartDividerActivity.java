package com.zhouyou.recyclerview.divider;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.MenuAdapter;
import com.zhouyou.recyclerview.bean.Menu;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：智能分割线</p>
 * 主要针对于使用XRecyclerView，添加头和尾的时候能够自动不加分割线，采用XHorizontalDividerItemDecoration
 * 作者： zhouyou<br>
 * 日期： 2017/12/22 13:25 <br>
 * 版本： v1.0<br>
 */
public class SmartDividerActivity extends BaseActivity {
    MenuAdapter adapter;
    XRecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (XRecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        addHeader();
        addHeader();
        addHeader();
        
        addFooter();
        addFooter();
        
        adapter = new MenuAdapter(this);
        recyclerView.setAdapter(adapter);


        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{15.0f, 15.0f}, 0));
        //方式一：使用XRecyclerView添加头和尾时XHorizontalDividerItemDecoration会帮你智能分析跳过头和尾不要分割线
        recyclerView.addItemDecoration(new com.zhouyou.recyclerview.divider.XHorizontalDividerItemDecoration.Builder(this)
                .paint(paint)//分割线采用paint
                .margin(60, 0)//距离左边60px
                //.positionInsideItem(true)//分割线是否在item里面
                .build());
        
        //方式二：采用startSkipCount和endSkipCount需要自己计算头和尾的个数，然后跳过。
        /*recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .paint(paint)
                .margin(15)
                .startSkipCount(2)
                .endSkipCount(2)
                .build());*/

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

    //添加头部
    private void addHeader() {
        View header = getLayoutInflater().inflate(R.layout.layout_header, (ViewGroup) recyclerView.getParent(), false);
        recyclerView.addHeaderView(header);
        header.findViewById(R.id.btn_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是头部", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //添加尾部
    private void addFooter() {
        View footerView = getLayoutInflater().inflate(R.layout.layout_footer, (ViewGroup) recyclerView.getParent(), false);
        recyclerView.addFooterView(footerView);
        footerView.findViewById(R.id.test_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是尾部", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
