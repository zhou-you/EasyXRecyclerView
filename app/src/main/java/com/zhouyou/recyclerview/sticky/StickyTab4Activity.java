package com.zhouyou.recyclerview.sticky;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.het.smarttab.SmartTabLayout;
import com.het.smarttab.v4.FragmentPagerItem;
import com.het.smarttab.v4.FragmentPagerItems;
import com.het.smarttab.v4.FragmentStatePagerItemAdapter;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：滑动嵌套tab布局Sticky效果</p>
 * tab4切换（StickyNavLayout+SmartTabLayout+ViewPager+fragment+RecyclerView）<br>
 * 与tab2和tab3不同点是：tab2、tab3头部是AppBarLayout折叠展开的方式，StickyNavLayout头部是滑进去的效果<br>
 * 功能同3只是将TabLayout换成SmartTabLayout<br>
 * 这里对RecyclerView不需要额外的设置，见News3Fragment  不需要其它任何设置，不能设置：mRecyclerView.setHasFixedSize(true);<br>
 * 作者： zhouyou<br>
 * 日期： 2017/12/18 10:48 <br>
 * 版本： v1.0<br>
 */
public class StickyTab4Activity extends BaseActivity {
    private SmartTabLayout mSmartTabLayout;
    private ViewPager mViewPager;
    //private StickyNavLayout mStickyNavLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_sticky);
        mSmartTabLayout = (SmartTabLayout) findViewById(R.id.smartTabLayout);
        //mStickyNavLayout = (StickyNavLayout) findViewById(R.id.id_stick);
        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
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
            pages.add(FragmentPagerItem.of(tabs[i], News3Fragment.class, args));
        }
        mViewPager.removeAllViews();
        fragmentPagerAdapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), pages);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mSmartTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
