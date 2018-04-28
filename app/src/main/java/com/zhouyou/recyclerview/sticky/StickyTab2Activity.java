package com.zhouyou.recyclerview.sticky;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.het.smarttab.v4.FragmentPagerItem;
import com.het.smarttab.v4.FragmentPagerItems;
import com.het.smarttab.v4.FragmentStatePagerItemAdapter;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：滑动嵌套tab布局Sticky效果</p>
 * <p>
 * AppBarLayout+TabLayout+ViewPager+fragment+RecyclerView,全部采用官方的一套架构实现<br>
 * 优点：对于滑动悬停不需要任何自定义view，全部官方api，可以支持RecyclerView滑动刷新加载更多(这才是最大的优点)<br>
 * 缺点：必须遵循官方的一套设置，对api要求比较高，对这一套东西全部掌握需要一定学习成本<br>
 * <p>
 * 这里对RecyclerView不需要额外的设置，见News2Fragment<br>
 * 作者： zhouyou<br>
 * 日期： 2017/12/18 10:48 <br>
 * 版本： v1.0<br>
 */
@SuppressWarnings(value={"unchecked", "deprecation"})
public class StickyTab2Activity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //private AppBarLayout mBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2_sticky);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //mBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mViewPager = (ViewPager) findViewById(R.id.slidingViewPager);
        mViewPager.setOffscreenPageLimit(3);

        initTabFragment();
    }

    FragmentStatePagerItemAdapter fragmentPagerAdapter;

    private void initTabFragment() {
        String[] tabs = getResources().getStringArray(R.array.news);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < tabs.length; i++) {
            Bundle args = new Bundle();
            args.putString("name", tabs[i]);
            pages.add(FragmentPagerItem.of(tabs[i], News2Fragment.class, args));
        }
        mViewPager.removeAllViews();
        fragmentPagerAdapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), pages);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabs.length; i++) {
            mTabLayout.getTabAt(i).setText(tabs[i]);
        }

        /*mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.getTabAt(0).getCustomView().setSelected(true);*/

        //给TaBlayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(fragmentPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
