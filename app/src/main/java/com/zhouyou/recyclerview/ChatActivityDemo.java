package com.zhouyou.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhouyou.recyclerview.adapter.MyAdapter;
import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivityDemo extends AppCompatActivity{
    private LinearLayout ll_input;
    private RelativeLayout rl_chat_content;
    private com.zhouyou.recyclerview.XRecyclerView mRecyclerView;
    private Button btn_send_chat;
    private MyAdapter mAdapter;
    private int times = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_demo);
        rl_chat_content = (RelativeLayout)findViewById(R.id.rl_chat_content);
        ll_input = (LinearLayout)findViewById(R.id.ll_input);
        mRecyclerView = (com.zhouyou.recyclerview.XRecyclerView)findViewById(R.id.recyclerview_chat);
        btn_send_chat = (Button) findViewById(R.id.btn_send_chat);
        initRecycler();
        initView();

    }


    private void initView(){
       /* et_input.setOnTouchListener((v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mRecyclerView.scrollToPosition(0);
                        return false;
                    }
                    return false;
                }
        );*/

        btn_send_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    private void send(){
       /* if(TextUtils.isEmpty(et_input.getText())){
            Toast.makeText(this,"不能为空",Toast.LENGTH_SHORT);
            return;
        }

        if(!mRecyclerView.isLoadingMore()){
            String msg = et_input.getText().toString();
            TestBean testBean = new TestBean(msg, msg);
            mAdapter.addItemToHead(testBean);
            et_input.setText("");
            mRecyclerView.scrollToPosition(0);
        }
*/

    }
    private void initRecycler(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        mRecyclerView.setLoadingListener(new com.zhouyou.recyclerview.XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //  mRecyclerView.loadMoreComplete();
                            List<TestBean> list = new ArrayList<TestBean>();
                            int size = mAdapter.getItemCount();//适配器中已有的数据
                            for (int i = 0; i < 10; i++) {
                                String name = "更多 姓名：张三" + (i + size);
                                String age = "更多 年龄：" + (i + size);
                                TestBean testBean = new TestBean(name, age);
                                list.add(testBean);
                            }

                            //mAdapter.notifyDataSetChanged();
                            //追加list.size()个数据到适配器集合最后面
                            //不需要 mAdapter.notifyDataSetChanged();
                            mAdapter.addItemsToLast(list);
                            mRecyclerView.setLoadingMoreEnabled(true);

                        }
                    }, 2000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setLoadingMoreEnabled(false);

                            //
                            Log.e("", "");
                            new Handler().postDelayed(new Runnable() {
                                public void run() {

                                    mAdapter.notifyDataSetChanged();
                                    mRecyclerView.loadMoreComplete();
                                    mRecyclerView.setLoadingMoreEnabled(true);

                                    //
                                    Log.e("", "");
                                }
                            }, 3000);
                        }
                    }, 2000);
                }
                times++;
            }
        });
        List<TestBean> listData = new ArrayList<TestBean>();
        for (int i = 0; i < 4; i++) {
            String name = "初始 姓名 张三" + i;
            String age = "初始 年龄：" + i;
            TestBean testBean = new TestBean(name, age);
            listData.add(testBean);
        }

        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListAll(listData);
        mRecyclerView.scrollToPosition(0);
    }
}
