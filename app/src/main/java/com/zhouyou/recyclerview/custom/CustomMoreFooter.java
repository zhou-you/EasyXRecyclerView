package com.zhouyou.recyclerview.custom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhouyou.recyclerview.refresh.BaseMoreFooter;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：定制了加载更多动画</p>
 * 
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/14 9:47<br>
 * 版本： v2.0<br>
 */
public class CustomMoreFooter extends BaseMoreFooter {
    private AnimationDrawable mAnimationDrawable;
    private LinearLayout allLayout;
    private TextView mTextView;

    public CustomMoreFooter(Context context) {
        super(context);
    }

    public CustomMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        super.initView();//父类是有某人居中显示功能，如果不需要就去掉super.initView();
        allLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_clife, null);
        ImageView imageView = (ImageView) allLayout.findViewById(R.id.lodimg);
        imageView.setImageResource(R.drawable.icon_loading_animation);
        mAnimationDrawable = (AnimationDrawable) imageView.getDrawable();
        mTextView = (TextView) allLayout.findViewById(R.id.lodtext);
        addView(allLayout);
    }

    @Override
    public void setState(int state) {
        super.setState(state);
        //以下是我自定义动画需要用到的状态判断，你可以根据自己需求选择。
        //选择自定义需要处理的状态：STATE_LOADING、STATE_COMPLETE、STATE_NOMORE、STATE_NOMORE
        switch (state) {
            case STATE_LOADING:
                this.setVisibility(View.VISIBLE);
                mAnimationDrawable.start();
                mTextView.setText("努力加载中...");
                mTextView.setVisibility(VISIBLE);
                break;
            case STATE_COMPLETE:
                this.setVisibility(View.GONE);
                mAnimationDrawable.stop();
                mTextView.setText("加载完成");
                break;
            case STATE_NOMORE:
                mAnimationDrawable.stop();
                mTextView.setText("没有更多");
                this.setVisibility(View.GONE);
                break;
        }
    }
}
