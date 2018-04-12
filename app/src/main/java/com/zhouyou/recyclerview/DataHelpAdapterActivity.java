package com.zhouyou.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.recyclerview.adapter.DataHelpAdapter;
import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：展示数据规范接口使用页面</p>
 * 
 * 重点看数据是如何使用的
 *
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class DataHelpAdapterActivity extends BaseActivity {
    private com.zhouyou.recyclerview.XRecyclerView mRecyclerView;
    private DataHelpAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (com.zhouyou.recyclerview.XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);

        //用框架里面的adapter时不需要再建立全局集合存放数据了，数据都和adapter绑定了，里面自带泛型集合
        //如果你外面还建立一个集合，那相当于占用内存两份了。。
        List<TestBean> listData = new ArrayList<TestBean>();
        listData.add(new TestBean("头部添加一条数据","1"));
        listData.add(new TestBean("添加单个数据到列表尾部","2"));
        listData.add(new TestBean("添加n个数据集到列表头部","3"));
        listData.add(new TestBean("添加n个数据集到列表尾部","4"));
        listData.add(new TestBean("在第n(3)个位置添加n条数据","5"));
        listData.add(new TestBean("在第n(3)个位置添加1条数据","6"));
        listData.add(new TestBean("获取index对于的item数据","7"));
        listData.add(new TestBean("修改我","8"));
        listData.add(new TestBean("删除我","9"));
        listData.add(new TestBean("数据覆盖","10"));
        listData.add(new TestBean("判断数据中是否包含某个对象","12"));
        listData.add(new TestBean("清除所有","11"));

        mAdapter = new DataHelpAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListAll(listData);

        //设置item事件监听
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(View view, TestBean item, int position) {
                //所有操作都不需要  notifyDataSetChanged();
                switch (item.getAge()){
                    case "1":
                        mAdapter.addItemToHead(new TestBean("我是新添加到头部的",""));
                        break;
                    case "2":
                        mAdapter.addItemToLast(new TestBean("我是新添加到尾部的",""));
                        break;
                    case "3":
                        List<TestBean> listData = new ArrayList<TestBean>();
                        listData.add(new TestBean("头部1",""));
                        listData.add(new TestBean("头部2",""));
                        listData.add(new TestBean("头部3",""));
                        mAdapter.addItemsToHead(listData);
                        break;
                    case "4":
                        List<TestBean> lastData = new ArrayList<TestBean>();
                        lastData.add(new TestBean("尾部1",""));
                        lastData.add(new TestBean("尾部2",""));
                        lastData.add(new TestBean("尾部3",""));
                        mAdapter.addItemsToLast(lastData);
                        break;
                    case "5":
                        List<TestBean> indexsData = new ArrayList<TestBean>();
                        indexsData.add(new TestBean("插入1",""));
                        indexsData.add(new TestBean("插入2",""));
                        indexsData.add(new TestBean("插入3",""));
                        mAdapter.addAll(2, indexsData);
                        break;
                    case "6":
                        mAdapter.add(2, new TestBean("插入4", ""));
                        break;
                    case "7"://数据获取不用强转
                        TestBean testBean = mAdapter.getData(position);
                        Toast.makeText(DataHelpAdapterActivity.this,testBean.toString(),Toast.LENGTH_SHORT).show();
                        break;
                    case "8":
                        mAdapter.alterObj(position, new TestBean("我被修改了", ""));
                        break;
                    case "9":
                        mAdapter.removeToIndex(8);
                        break;
                    case "10":
                        List<TestBean> allDatas = new ArrayList<TestBean>();
                        allDatas.add(new TestBean("新数据1",""));
                        allDatas.add(new TestBean("新数据2",""));
                        allDatas.add(new TestBean("新数据3",""));
                        mAdapter.setListAll(allDatas);
                        break;
                    case "11":
                        mAdapter.clear();
                        break;
                }
            }
        });

      
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    
}
