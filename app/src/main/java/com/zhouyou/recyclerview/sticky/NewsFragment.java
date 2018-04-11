package com.zhouyou.recyclerview.sticky;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.MyAdapter;
import com.zhouyou.recyclerview.refresh.BaseFragment;
import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：新闻fragemnt</p>
 * 作者： zhouyou<br>
 * 日期： 2018/1/24 15:25 <br>
 * 版本： v1.0<br>
 */
public class NewsFragment extends BaseFragment {
    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private String new_name;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView(View contentView) {
        mRecyclerView = findView(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);

        //与StickyNestedScrollView嵌套必须设置下面两个属性 1 2 
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(false);

        //与StickyNestedScrollView嵌套必须设置下面两个属性 3 4 
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        //如果你忘记了设置上面4个属性，会出现冲突问题，切记！！！

        mAdapter = new MyAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void bindEvent() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<TestBean> list = new ArrayList<TestBean>();
                        int size = mAdapter.getItemCount();//适配器中已有的数据
                        for (int i = 0; i < 30; i++) {
                            String name = new_name + "  张三:" + (i + size);
                            String age = new_name + "   年龄:" + (i + size);
                            TestBean testBean = new TestBean(name, age);
                            list.add(testBean);
                        }

                        mAdapter.addItemsToLast(list);
                        mRecyclerView.refreshComplete();
                        mRecyclerView.setLoadingMoreEnabled(true);
                    }
                }, 3000);
            }
        });

        //设置item事件监听
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(View view, TestBean item, int position) {
                Toast.makeText(mContext, "我是item " + position, Toast.LENGTH_LONG).show();
            }
        });

       /* mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisiblePosition =((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    if (firstVisiblePosition == 1) {
                        ((StickyTab2Activity)mContext).setExpanded();
                    }
                }
            }});*/
       
    }

    @Override
    protected void initData() {
        new_name = getArguments().getString("name");
        List<TestBean> list = new ArrayList<TestBean>();
        int size = mAdapter.getItemCount();//适配器中已有的数据
        for (int i = 0; i < 30; i++) {
            String name = new_name + "  张三:" + (i + size);
            String age = new_name + "   年龄:" + (i + size);
            TestBean testBean = new TestBean(name, age);
            list.add(testBean);
        }
        mAdapter.setListAll(list);
    }

}
