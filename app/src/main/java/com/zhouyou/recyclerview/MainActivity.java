package com.zhouyou.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.anim.AnimationActivity;
import com.zhouyou.recyclerview.bean.ExampleBean;
import com.zhouyou.recyclerview.custom.CustomAnimActivity;
import com.zhouyou.recyclerview.divider.DividerMainActivity;
import com.zhouyou.recyclerview.drag.DragActivity;
import com.zhouyou.recyclerview.group.GroupMainActivity;
import com.zhouyou.recyclerview.header.HeaderMainActivity;
import com.zhouyou.recyclerview.state.StateMainActivity;
import com.zhouyou.recyclerview.sticky.StickyMainActivity;
import com.zhouyou.recyclerview.swipe.SwipeMenuActivity;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：普通RecyclerView+HelperRecyclerViewAdapter</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/31 10:24<br>
 * 版本： v2.0<br>
 */
public class MainActivity extends AppCompatActivity {
    private com.zhouyou.recyclerview.XRecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //actionBar.setHomeButtonEnabled(true);
            //actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.openOptionsMenu();
        }
        //RefreshLoadingManager.getManager().setCusRefreshHeader(new CommonCusHeader());
       // RefreshLoadingManager.getManager().setCusLoadingFooter(new CommonCusFooter());

        mRecyclerView = (com.zhouyou.recyclerview.XRecyclerView) findViewById(R.id.main_recyclerview);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ExampleAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListAll(getData());

        mAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<ExampleBean>() {
            @Override
            public void onItemLongClick(View view, ExampleBean item, int position) {
                Toast.makeText(MainActivity.this, "我是长按item" + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        //Item点击事件
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<ExampleBean>() {
            @Override
            public void onItemClick(View view, ExampleBean item, int position) {
                switch (mAdapter.getData(position).getId()) {
                    case 0://RecyclerView+HelperRecyclerViewAdapter
                        Toast.makeText(MainActivity.this, "用法很简单，使用RecyclerView就可以了", Toast.LENGTH_SHORT).show();
                        break;
                    case 1://XRecyclerView+HelperRecyclerViewAdapter+LinearLayoutManager
                        gotoActivity(LinearActivity.class);
                        break;
                    case 2:
                        gotoActivity(GridActivity.class);
                        break;
                    case 3:
                        gotoActivity(StaggeredGridActivity.class);
                        break;
                    case 4:
                        gotoActivity(AdapterActivity.class);
                        break;
                    case 5://XRecyclerView讲解
                        Toast.makeText(MainActivity.this, "查看XRecyclerView代码", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(MainActivity.this, "MyAdapter与OldMyAdapter具体代码，代码讲解很详细", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        gotoActivity(MultiItemActivity.class);
                        break;
                    case 8:
                        gotoActivity(DragActivity.class);
                        break;
                    case 9:
                        gotoActivity(SwipeMenuActivity.class);
                        break;
                    case 10:
                        gotoActivity(AnimationActivity.class);
                        break;
                    case 11:
                        gotoActivity(DataHelpAdapterActivity.class);
                        break;
                    case 12:
                        gotoActivity(LinearActivity.class);
                        break;
                    case 13:
                        gotoActivity(CustomAnimActivity.class);
                        break;
                   /* case 14:
                        gotoActivity(ChatActivityDemo.class);
                        break;*/
                    case 15:
                        gotoActivity(HeaderMainActivity.class);
                        break;
                    case 16:
                        gotoActivity(StickyMainActivity.class);
                        break;
                    case 17:
                        gotoActivity(GroupMainActivity.class);
                        break;
                    case 18:
                        gotoActivity(DividerMainActivity.class);
                        break;
                    case 19:
                        gotoActivity(StateMainActivity.class);
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.adout_layout:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<ExampleBean> getData() {
        List<ExampleBean> list = new ArrayList<>();
        ExampleBean bean1 = new ExampleBean();
        bean1.setTitle("RecyclerView+HelperRecyclerViewAdapter");
        bean1.setRemark("系统的RecyclerView配合HelperRecyclerViewAdapter使用");
        bean1.setId(0);
        list.add(bean1);

        ExampleBean bean2 = new ExampleBean();
        bean2.setTitle("XRecyclerView+HelperRecyclerViewAdapter+LinearLayoutManager");
        bean2.setRemark("XRecyclerView配合万能适配器HelperRecyclerViewAdapter的用法，" +
                "类似ListView功能，同时可以增加头部view,代码讲解请详看LinearActivity代码中，是如何使用适配器的。(重点看)");
        bean2.setId(1);
        list.add(bean2);

        ExampleBean bean3 = new ExampleBean();
        bean3.setTitle("XRecyclerView+HelperRecyclerViewAdapter+GridLayoutManager");
        bean3.setRemark("XRecyclerView配合万能适配器HelperRecyclerViewAdapter的用法，" +
                "类似GrideView功能。");
        bean3.setId(2);
        list.add(bean3);

        ExampleBean bean4 = new ExampleBean();
        bean4.setTitle("XRecyclerView+HelperRecyclerViewAdapter+StaggeredGridLayoutManager");
        bean4.setRemark("XRecyclerView配合万能适配器HelperRecyclerViewAdapter和StaggeredGridLayoutManager的用法，" +
                "实现瀑布流功能。");
        bean4.setId(3);
        list.add(bean4);

        ExampleBean bean5 = new ExampleBean();
        bean5.setTitle("BaseRecyclerViewAdapter、BaseRecyclerViewHolder、HelperRecyclerViewAdapter、HelperRecyclerViewHolder相互关系");
        bean5.setRemark("HelperRecyclerViewAdapter是继承于BaseRecyclerViewAdapter，" +
                "提供便捷操作的baseAdapter，BaseRecyclerViewHolder是万能的Holder减少赘于代码和加快开发流程,HelperRecyclerViewHolder继承于BaseRecyclerViewHolder。");
        bean5.setId(4);
        list.add(bean5);

        ExampleBean bean6 = new ExampleBean();
        bean6.setTitle("XRecyclerView讲解");
        bean6.setRemark("XRecyclerView是一个自定义view继承自系统的RecyclerView，进行扩展实现刷新加载更多等功能");
        bean6.setId(5);
        list.add(bean6);

        ExampleBean bean7 = new ExampleBean();
        bean7.setTitle("MyAdapter与OldMyAdapter对比，适配器讲解");
        bean7.setRemark("适配器写法对比，MyAdapter是采用继承HelperRecyclerViewAdapter，" +
                "OldMyAdapter是继承自系统RecyclerView.Adapter，以前OldMyAdapter写法真是累死宝宝了");
        bean7.setId(6);
        list.add(bean7);

        ExampleBean bean8 = new ExampleBean();
        bean8.setTitle("XRecyclerView+MultiItemAdapter");
        bean8.setRemark("实现RecyclerView对应多个不同item的情况");
        bean8.setId(7);
        list.add(bean8);

        ExampleBean bean9 = new ExampleBean();
        bean9.setTitle("XRecyclerView+HelpRecyclerViewDragAdapter");
        bean9.setRemark("实现拖拽功能，HelpRecyclerViewDragAdapter是继承自HelperRecyclerViewAdapter");
        bean9.setId(8);
        list.add(bean9);

        ExampleBean bean10 = new ExampleBean();
        bean10.setTitle("SwipeMenuRecyclerView+swipemenu+SwipeMenuAdapter");
        bean10.setRemark("实现item侧滑菜单功能，例如：侧滑删除,SwipeMenuRecyclerView是继承XRecyclerView");
        bean10.setId(9);
        list.add(bean10);

        ExampleBean bean11 = new ExampleBean();
        bean11.setTitle("XRecyclerView +HelperRecyclerViewAnimAdapter 实现具有item 具有动画效果");
        bean11.setRemark("item具有各种加载动画效果,也支持自定义动画效果。进入页面后按菜单键可以查看更多的效果。");
        bean11.setId(10);
        list.add(bean11);

        ExampleBean bean12 = new ExampleBean();
        bean12.setTitle("adapter+DataHelper");
        bean12.setRemark("adapter规范数据操作接口，适配器已经自带拥有DataHelper功能，使用方式看详情");
        bean12.setId(11);
        list.add(bean12);

        ExampleBean bean13 = new ExampleBean();
        bean13.setTitle("自定义XRecyclerView刷新动画");
        bean13.setRemark("目前只有默认的28种加载动画效果（进入页面后按菜单键可以查看），适合和而泰产品的效果等产品确定好后再提供");
        bean13.setId(12);
        list.add(bean13);

        ExampleBean bean14 = new ExampleBean();
        bean14.setTitle("自定义刷新动画");
        bean14.setRemark("外部自定义刷新动画和加载更多动画");
        bean14.setId(13);
        list.add(bean14);

       /* ExampleBean bean15 = new ExampleBean();
        bean15.setTitle("聊天界面demo");
        bean15.setRemark("聊天界面demo");
          bean15.setId(14);
        list.add(bean15);*/

        ExampleBean bean16 = new ExampleBean();
        bean16.setTitle("添加头部和尾部");
        bean16.setRemark("支持添加多头部和尾部");
        bean16.setId(15);
        list.add(bean16);

        ExampleBean bean17 = new ExampleBean();
        bean17.setTitle("Sticky效果");
        bean17.setRemark("支持普通布局和列表分组Sticky效果");
        bean17.setId(16);
        list.add(bean17);

        ExampleBean bean18 = new ExampleBean();
        bean18.setTitle("Group RecyclerView");
        bean18.setRemark("支持各种分组效果");
        bean18.setId(17);
        list.add(bean18);

        ExampleBean bean19 = new ExampleBean();
        bean19.setTitle("RecyclerView divider");
        bean19.setRemark("强大灵活个性的RecyclerView分割线设置");
        bean19.setId(18);
        list.add(bean19);

        ExampleBean bean20 = new ExampleBean();
        bean20.setTitle("RecyclerView state状态页面");
        bean20.setRemark("不影响头部尾部显示的情况下，支持空页面、加载中、错误页面展示");
        bean20.setId(19);
        list.add(bean20);

        return list;
    }

    class ExampleAdapter extends HelperRecyclerViewAdapter<ExampleBean> {
        public ExampleAdapter(Context context) {
            super(context, R.layout.example_item);
        }

        @Override
        protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, ExampleBean item) {
            viewHolder.setText(R.id.number, (position + 1) + "").setText(R.id.title, item.getTitle())
                    .setText(R.id.remark, item.getRemark());
        }
    }

    public void gotoActivity(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }
}
