package com.zhouyou.recyclerview.sticky;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：普通布局Sticky效果</p>
 * 注意：
 * 1. 要给需要sticky的View设置tab属性：android:tag="sticky";<br>
 * 2. 也可以Java动态设置：view.setTag("sticky");<br>
 * 3. 如果这个sticky的View是可点击的，那么tag为：android:tag="sticky-nonconstant"或者view.setTag("sticky-nonconstant");<br>
 * 
 *
 * 是采用自定义StickyNestedScrollView（继承官方NestedScrollView扩展）和普通布局构成，支持某一个view设置tag就可以悬停<br>
 * 支持多view悬停，只要view设置了tag<br>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2017/12/18 10:48 <br>
 * 版本： v1.0<br>
 */
public class StickyLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_layout);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        initTabLayout();

    }

    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText("商品预览");
        tabLayout.addTab(tab);

        tab = tabLayout.newTab();
        tab.setText("商品详情");
        tabLayout.addTab(tab);

        tab = tabLayout.newTab();
        tab.setText("商品描述");
        tabLayout.addTab(tab);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(StickyLayoutActivity.this, "第" + tab.getPosition() + "个Tab", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
