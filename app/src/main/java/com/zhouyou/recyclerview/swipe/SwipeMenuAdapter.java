package com.zhouyou.recyclerview.swipe;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.zhouyou.recyclerview.bean.MultipleItemBean;
import com.zhouyou.recyclerview.adapter.HelpRecyclerViewDragAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.swipemenu.SwipeMenuLayout;
import com.zhouyou.recyclerview.util.MakePicUtil;
import com.zhouyou.recyclerviewdemo.R;

import static com.zhouyou.recyclerviewdemo.R.id.image_iv;

/**
 * <p>描述：具有侧滑菜单的适配器</p>
 *
 * 作者： zhouyou<br>
 * 日期： 2016/11/1 16:24<br>
 * 版本： v2.0<br>
 */
public class SwipeMenuAdapter extends HelpRecyclerViewDragAdapter<MultipleItemBean> {
    public SwipeMenuAdapter(Context context) {
        super(context, R.layout.adapter_swipemenu1_layout,R.layout.adapter_swipemenu_layout);
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, MultipleItemBean item) {
        final SwipeMenuLayout superSwipeMenuLayout = (SwipeMenuLayout) viewHolder.itemView;
        superSwipeMenuLayout.setSwipeEnable(true);   //设置是否可以侧滑
        switch (item.getItemType()){
            case 0:
                viewHolder.setText(R.id.name_tv,item.getName()).setImageResource(R.id.image_iv, MakePicUtil.makePic(position))
                        .setOnClickListener(R.id.btFavorite, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(mContext,"show Favorite",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnClickListener(R.id.btGood, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(mContext,"show good",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnClickListener(image_iv, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(mContext,"show image",Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case 1:
                viewHolder.setText(R.id.name_tv,item.getName()).setOnClickListener(R.id.btOpen, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext,"show open",Toast.LENGTH_SHORT).show();
                    }
                }).setOnClickListener(R.id.btDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext,"show delete",Toast.LENGTH_SHORT).show();
                    }
                }).setImageResource(R.id.image_iv, MakePicUtil.makePic(position));
                
                /**
                 * 设置可以非滑动触发的开启菜单
                 */
                viewHolder.getView(image_iv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (superSwipeMenuLayout.isOpen()) {
                            superSwipeMenuLayout.closeMenu();
                        } else {
                            superSwipeMenuLayout.openMenu();
                        }
                    }
                });
                break;
        }
    }


    @Override
    public int checkLayout(MultipleItemBean item, int position) {
        return item.getItemType();
    }
}
