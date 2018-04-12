package com.zhouyou.recyclerview.custom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhouyou.recyclerview.refresh.BaseLoadingFooter;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：定制了加载更多动画</p>
 * 同时可以支持本库中自带的其它27种动画<br>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/14 9:47<br>
 * 版本： v2.0<br>
 */
public class ClifeLoadingMoreFooter extends LinearLayout implements BaseLoadingFooter {
    private int mState;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;
    private AnimationDrawable mAnimationDrawable;
    private LinearLayout allLayout;
    private TextView mTextView;

    public ClifeLoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public ClifeLoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        allLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_clife, null);
        ImageView imageView = (ImageView) allLayout.findViewById(R.id.lodimg);
        imageView.setImageResource(R.drawable.icon_loading_animation);
        mAnimationDrawable = (AnimationDrawable) imageView.getDrawable();
        mTextView = (TextView) allLayout.findViewById(R.id.lodtext);
        addView(allLayout);
    }

    @Override
    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    @Override
    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    @Override
    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    @Override
    public void setProgressStyle(int style) {
    }

    @Override
    public boolean isLoadingMore() {
        return mState == STATE_LOADING;
    }

    @Override
    public void setState(int state) {
        this.mState = state;
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

    @Override
    public View getFooterView() {
        return this;
    }
}
