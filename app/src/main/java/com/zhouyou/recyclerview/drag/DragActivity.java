package com.zhouyou.recyclerview.drag;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.listener.ItemDragListener;
import com.zhouyou.recyclerview.listener.OnItemDragListener;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>描述：可以拖拽的例子</p>
 * 作者： zhouyou<br>
 * 日期： 2016/11/1 10:24<br>
 * 版本： v2.0<br>
 */
public class DragActivity extends BaseActivity implements XRecyclerView.LoadingListener {
    private XRecyclerView mRecyclerView;
    private DragAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragListener mItemDragAndSwipeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_layout);
        mRecyclerView = (XRecyclerView)findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setRefreshing(true);
        //mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(this);

        List<String> mData = generateData(50);
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                mRecyclerView.setPullRefreshEnabled(false);//在开始的时候需要禁止下拉刷新，不然在下滑动的时候会与下拉刷新冲突
                HelperRecyclerViewHolder holder = ((HelperRecyclerViewHolder)viewHolder);
                holder.setTextColor(R.id.tv, Color.WHITE);
                ((CardView)viewHolder.itemView).setCardBackgroundColor(ContextCompat.getColor(DragActivity.this, R.color.colorAccent));
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                mRecyclerView.setPullRefreshEnabled(true);//在结束之后需要开启下拉刷新
                HelperRecyclerViewHolder holder = ((HelperRecyclerViewHolder)viewHolder);
                holder.setTextColor(R.id.tv, Color.BLACK);
                ((CardView)viewHolder.itemView).setCardBackgroundColor(Color.WHITE);
            }
        };
        mAdapter = new DragAdapter(this);
        mItemDragAndSwipeCallback = new ItemDragListener(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mAdapter.enableDragItem(mItemTouchHelper);
        mAdapter.setOnItemDragListener(listener);

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, String item, int position) {
                Toast.makeText(DragActivity.this,item,Toast.LENGTH_SHORT).show();
            }
        });

       View headerView = getLayoutInflater().inflate(R.layout.view_header_layout, (ViewGroup) mRecyclerView.getParent(), false);
        View footerView = getLayoutInflater().inflate(R.layout.view_footer_layout, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.addHeaderView(headerView);
        //mRecyclerView.addFootView(footerView);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListAll(mData);
    }

    private List<String> generateData(int size) {
        ArrayList<String> data = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            data.add("item " + i);
        }
        return data;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.refreshComplete();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.loadMoreComplete();
                mRecyclerView.setNoMore(true);
            }
        }, 2000);
    }

}
