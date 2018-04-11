package com.zhouyou.recyclerview.anim;


import android.content.Context;


import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAnimAdapter;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：具有动画效果的适配器</p>
 *
 * 作者： zhouyou<br>
 * 日期： 2016/10/31 16:24<br>
 * 版本： v2.0<br>
 */
public class AnimationAdapter extends HelperRecyclerViewAnimAdapter<String> {

    public AnimationAdapter(Context context) {
        super(context, R.layout.adapter_animation_layout);
    }
    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, Object item) {
        
    }
}
