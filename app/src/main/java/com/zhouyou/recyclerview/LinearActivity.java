package com.zhouyou.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.recyclerview.adapter.MyAdapter;
import com.zhouyou.recyclerview.bean.TestBean;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.refresh.ArrowRefreshHeader;
import com.zhouyou.recyclerview.refresh.IRefreshHeader;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：线性布局  具体使用参考代码</p>
 * 不满一屏也可以加载更多<br>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class LinearActivity extends BaseActivity {
    private com.zhouyou.recyclerview.XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        
        mRecyclerView = (com.zhouyou.recyclerview.XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        /*IRefreshHeader refreshHeader = mRecyclerView.getRefreshHeader();
        if(refreshHeader instanceof ArrowRefreshHeader){
            ((ArrowRefreshHeader) refreshHeader).setStatusTextViewColor(R.color.color4);
        }*/

        //mRecyclerView.setRefreshProgressStyle(ProgressStyle.ClifeIndicator);
        //mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.ClifeIndicator);
        //mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        //mRecyclerView.setRefreshHeader((BaseRefreshHeader) RefreshLoadingManager.getManager().getCusRefreshHeader(this));
        //mRecyclerView.setLoadingMoreFooter((BaseLoadingFooter) RefreshLoadingManager.getManager().getCusLoadingFooter(this));

        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(new com.zhouyou.recyclerview.XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.e("test","=======isRefreshing======="+mRecyclerView.isRefreshing());
                times = 0;
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        //不需要先clear  用了setListAll里面就是覆盖&刷新
                        //listData.clear();
                        /*********传统的方式添加一条数据 add后再通知刷新***************/
                        /*for(int i = 0; i < 10 ;i++){
                            String name = "item name" + i + "after " + refreshTime + " times of refresh";
                            String age = "item age" + i + "after " + refreshTime + " times of refresh";
                            TestBean testBean = new TestBean(name,age);
                            listData.add(testBean);
                        }
                        mAdapter.notifyDataSetChanged();*/
                        List<TestBean> list = new ArrayList<TestBean>();
                        for (int i = 0; i < 10; i++) {
                            String name = "刷新 姓名 张三" + i;
                            String age = "刷新 年龄：" + i;
                            TestBean testBean = new TestBean(name, age);
                            list.add(testBean);
                        }
                        /****讲解*****/
                        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
                        //2.如果是addAll()追加
                        mAdapter.setListAll(list);
                        //mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
//                        mRecyclerView.setLoadingMoreEnabled(true);
                    }

                }, 2000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                Log.e("test","=======onLoadMore======="+mRecyclerView.isLoadingMore());
                if (times < 3) {
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

                            mRecyclerView.refreshComplete();
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
                            }, 2000);
                        }
                    }, 2000);
                }
                times++;
            }
        });

        //用框架里面的adapter时不需要再建立全局集合存放数据了，数据都和adapter绑定了，里面自带泛型集合
        //如果你外面还建立一个集合，那相当于占用内存两份了。。
        List<TestBean> listData = new ArrayList<TestBean>();

        //方式四对应的初始化适配器   也可采用下面的构造方式创建对象  （自己选择）
        //这种方式一定要先setAdapter然后才setListAll（）设置数据
        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        /****讲解*****/
        //1.使用setListAll（覆盖数据）后就不需要再调用notifyDataSetChanged（）
        //2.如果是addAll()追加
        //3.自己会刷新
        mAdapter.setListAll(listData);

        //方式一对应的初始化适配器
        //mAdapter = new MyAdapter(listData, this, R.layout.item);
        //方式二对应的初始化适配器
        //mAdapter = new MyAdapter(this, R.layout.item);


        //设置item事件监听
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(View view, TestBean item, int position) {
                Toast.makeText(getApplicationContext(),"我是item "+position,Toast.LENGTH_LONG).show();
            }
        });

      
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        if(mRecyclerView !=null){
            mRecyclerView.setRefreshing(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.progress, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SysProgress:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
                break;
            case R.id.BallPulse:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
                break;
            case R.id.BallGridPulse:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallGridPulse);
                break;
            case R.id.BallClipRotate:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
                break;
            case R.id.BallClipRotatePulse:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
                break;
            case R.id.SquareSpin:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.SquareSpin);
                break;
            case R.id.BallClipRotateMultiple:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotateMultiple);
                break;
            case R.id.BallPulseRise:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulseRise);
                break;
            case R.id.BallRotate:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallRotate);
                break;
            case R.id.CubeTransition:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.CubeTransition);
                break;
            case R.id.BallZigZag:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallZigZag);
                break;
            case R.id.BallZigZagDeflect:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallZigZagDeflect);
                break;
            case R.id.BallTrianglePath:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallTrianglePath);
                break;
            case R.id.BallScale:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallScale);
                break;
            case R.id.LineScale:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScale);
                break;
            case R.id.BallScaleMultiple:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallScaleMultiple);
                break;
            case R.id.BallPulseSync:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulseSync);
                break;
            case R.id.BallBeat:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
                break;
            case R.id.LineScalePulseOut:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOut);
                break;
            case R.id.LineScalePulseOutRapid:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOutRapid);
                break;
            case R.id.BallScaleRipple:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallScaleRipple);
                break;
            case R.id.BallScaleRippleMultiple:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallScaleRippleMultiple);
                break;
            case R.id.BallSpinFadeLoader:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
                break;
            case R.id.LineSpinFadeLoader:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
                break;
            case R.id.TriangleSkewSpin:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.TriangleSkewSpin);
                break;
            case R.id.BallGridBeat:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallGridBeat);
                break;
            case R.id.Pacman:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.Pacman);
                break;
            case R.id.SemiCircleSpin:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.SemiCircleSpin);
                break;
            case R.id.ClifeIndicator:
                mRecyclerView.setRefreshProgressStyle(ProgressStyle.ClifeIndicator);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
