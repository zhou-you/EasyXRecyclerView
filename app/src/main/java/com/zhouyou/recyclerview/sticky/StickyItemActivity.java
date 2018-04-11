package com.zhouyou.recyclerview.sticky;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.BaseActivity;
import com.zhouyou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>描述：item分组布局Sticky效果</p>
 * 注意：
 * 1. 要给需要sticky的View设置tab属性：android:tag="sticky";
 * 2. 也可以Java动态设置：view.setTag("sticky");
 * 3. 如果这个sticky的View是可点击的，那么tag为：android:tag="sticky-nonconstant"或者view.setTag("sticky-nonconstant");
 * <p>
 *     
 * StickyNestedScrollView+XRecyclerView实现，可以对RecyclerView中指定的item设置tag后，该item就可以悬停<br>
 * 支持多view悬停，只要view设置了tag<br>
 * 作者： zhouyou<br>
 * 日期： 2017/12/18 10:48 <br>
 * 版本： v1.0<br>
 */
public class StickyItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_item);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        XRecyclerView recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color)));

        GroupAdapter adapter = new GroupAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setListAll(getItems());
    }

    private class GroupAdapter extends HelperRecyclerViewAdapter<ItemType> {
        public GroupAdapter(Context context) {
            super(context, R.layout.item_group_main, R.layout.item_group_sticky);
        }

        @Override
        protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, ItemType item) {
            if (item.type() == 0) {//是内容item
                viewHolder.setText(R.id.tv_title, ((ListItem) item).text);
            } else if (item.type() == 1) {//是Sticky item
                viewHolder.setText(R.id.tv_title, ((StickyListItem) item).name);
            }
        }

        //不重写的时候返回默认是0，也就是只会加载第一个布局
        @Override
        public int checkLayout(ItemType item, int position) {
            return item.type();
        }
    }

    public interface ItemType {
        int type();
    }

    private static class ListItem implements ItemType {

        protected String text;

        ListItem(String text) {
            this.text = text;
        }

        @Override
        public int type() {
            return 0;
        }
    }

    private static class StickyListItem implements ItemType {
        protected String name;

        StickyListItem(String name) {
            this.name = name;
        }

        @Override
        public int type() {
            return 1;
        }
    }

    /**
     * 创建模拟数据
     * 带有分组数据信息的集合
     */
    private List<ItemType> getItems() {
        List<ListItem> itemTypes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            itemTypes.add(new ListItem("第" + i + "个Item"));
        }

        Collections.sort(itemTypes, new Comparator<ListItem>() {
            @Override
            public int compare(ListItem o1, ListItem o2) {
                return o1.text.compareToIgnoreCase(o2.text);
            }
        });
        List<ItemType> mItems = new ArrayList<>();
        mItems.addAll(itemTypes);

        StickyListItem stickyListItem = null;
        for (int i = 0, size = mItems.size(); i < size; i++) {
            ListItem listItem = (ListItem) mItems.get(i);
            String firstLetter = String.valueOf(listItem.text.charAt(1));
            if (stickyListItem == null || !stickyListItem.name.equals(firstLetter)) {
                stickyListItem = new StickyListItem(firstLetter);
                mItems.add(i, stickyListItem);
                size += 1;
            }
        }
        return mItems;
    }
}
