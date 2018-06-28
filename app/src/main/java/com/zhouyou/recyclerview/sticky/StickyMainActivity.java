package com.zhouyou.recyclerview.sticky;

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
 * <p>描述：Sticky菜单效果</p>
 * 作者： zhouyou<br>
 * 日期： 2017/12/18 10:37 <br>
 * 版本： v1.0<br>
 */
public class StickyMainActivity extends BaseActivity {
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
                //请根据自己的需求，选择合适的悬停方式！！！
                switch (item.id) {
                    case 0://支持多个悬停
                        //是采用自定义StickyNestedScrollView（继承官方NestedScrollView扩展）和普通布局构成，支持某一个view设置tag就可以悬停
                        //支持多view悬停，只要view设置了tag
                        gotoActivity(StickyLayoutActivity.class);
                        break;
                    case 1://支持多个悬停
                        //StickyNestedScrollView+XRecyclerView实现，可以对RecyclerView中指定的item设置tag后，该item就可以悬停
                        //支持多view悬停，只要view设置了tag
                        gotoActivity(StickyItemActivity.class);
                        break;
                    case 2://支持多个悬停
                        //tab切换(StickyNestedScrollView+SmartTabLayout+SlidingViewPager+fragment+RecyclerView) Sticky效果
                        //优点：悬停比较简单只要设置了tag就可以，对StickyNestedScrollView里的内容比较固定不是很多的情况下，可以选择使用。
                        //缺点：1.这一套逻辑可以实现滑动悬停，但是如果RecyclerView要实现加载更多是非常坑的一件事，
                        // 因为这种做法其实是将RecyclerView的滑动事件全部交给了NestedScrollView，自身已经没有了滑动能力，自然也就不能加载更多
                        //如果想解决这个问题是非常困难的，目前还没有好的办法。即使有好的办法解决了也不建议这么使用，因为如果加载数据多了会报OOM，因为这种方式已经
                        // 失去了RecyclerView的回收复用特性。
                        //缺点：2.这种使用方式要对RecyclerView进行配套的很多设置才行，如对LinearLayoutManager进行setSmoothScrollbarEnabled(true)和
                        //setAutoMeasureEnabled(true)设置，对mRecyclerView.setHasFixedSize(true);和mRecyclerView.setNestedScrollingEnabled(false);
                        //详情见NewsFragment。
                        gotoActivity(StickyTabActivity.class);
                        break;
                    case 3://可以支持RecyclerView滑动刷新加载更多
                        //AppBarLayout+TabLayout+ViewPager+fragment+RecyclerView,全部采用官方的一套架构实现
                        //优点：对于滑动悬停不需要任何自定义view，全部官方api，可以支持RecyclerView滑动刷新加载更多(这才是最大的优点)
                        //缺点：必须遵循官方的一套设置，对api要求比较高，对这一套东西全部掌握需要一定学习成本

                        //这里对RecyclerView不需要额外的设置，见News2Fragment
                        gotoActivity(StickyTab2Activity.class);
                        break;
                    case 4://可以支持RecyclerView滑动刷新加载更多
                        //功能同3只是将TabLayout换成SmartTabLayout
                        //这里对RecyclerView不需要额外的设置，见News2Fragment
                        gotoActivity(StickyTab3Activity.class);
                        break;
                    case 5://可以支持RecyclerView滑动刷新加载更多
                        //tab4切换（StickyNavLayout+SmartTabLayout+ViewPager+fragment+RecyclerView）
                        //与tab2和tab3不同点是：tab2、tab3头部是AppBarLayout折叠展开的方式，StickyNavLayout头部是滑进去的效果
                        //功能同3只是将TabLayout换成SmartTabLayout
                        //这里对RecyclerView不需要额外的设置，见News3Fragment  不需要其它任何设置，不能设置：mRecyclerView.setHasFixedSize(true);
                        gotoActivity(StickyTab4Activity.class);
                        break;
                    case 6://通过ItemDecoration实现 list布局的Sticky效果
                        //这种方式实现悬停，不会影响刷新和加载更多
                        gotoActivity(StickyListItemDecorationActivity.class);
                        break;
                    case 7://通过ItemDecoration实现 Grid布局的Sticky效果
                        gotoActivity(StickyGridItemDecorationActivity.class);
                        break;
                }
            }
        });

    }

    private List<Menu> getList() {
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(0, "普通布局 Sticky效果", ""));
        menus.add(new Menu(1, "Item分组Sticky效果", ""));
        menus.add(new Menu(2, "tab切换(StickyNestedScrollView+SmartTabLayout+SlidingViewPager+fragment+RecyclerView) Sticky效果(tab悬停)", ""));
        menus.add(new Menu(3, "tab2切换（AppBarLayout+TabLayout+ViewPager+fragment+RecyclerView） Sticky效果(tab悬停)支持刷新加载更多", ""));
        menus.add(new Menu(4, "tab3切换（AppBarLayout+SmartTabLayout+ViewPager+fragment+RecyclerView） Sticky效果(tab悬停)支持刷新加载更多", ""));
        menus.add(new Menu(5, "tab4切换（StickyNavLayout+SmartTabLayout+ViewPager+fragment+RecyclerView） Sticky效果(tab悬停)支持刷新加载更多", ""));
        menus.add(new Menu(6, "ItemDecoration 实现List Sticky效果", ""));
        menus.add(new Menu(7, "ItemDecoration 实现Grid Sticky效果", ""));
        return menus;
    }

    public void gotoActivity(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }
}
