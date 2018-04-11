package com.zhouyou.recyclerview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhouyou.recyclerview.refresh.BaseRefreshHeader;
import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：定制了Clife动画头部动画</p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/14 9:47<br>
 * 版本： v2.0<br>
 *
 */
public class ClifeRefreshHeader extends LinearLayout implements BaseRefreshHeader {
    private int mState = STATE_NORMAL;
    public int mMeasuredHeight;
    private TextView mTextView;
    private AnimationDrawable mAnimationDrawable;
    private LinearLayout allLayout;
    public ClifeRefreshHeader(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public ClifeRefreshHeader(Context context, AttributeSet attrs) {
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
        setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    @Override
    public void setArrowImageView(int resid) {
        
    }

    @Override
    public void setProgressStyle(int style) {
    }

    @Override
    public void setState(int state) {
        if (state == mState) return;
        if (state == STATE_REFRESHING) {    // 显示进度
            mAnimationDrawable.start();
        } else if (state == STATE_DONE) {
            mAnimationDrawable.stop();
        } else {
            mAnimationDrawable.start();
        }

        switch(state){
            case STATE_NORMAL:
                mTextView.setText("下拉加载更多...");
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    mTextView.setText("松手马上加载...");
                }
                break;
            case STATE_REFRESHING:
                mTextView.setText("努力加载中...");
                break;
            case STATE_DONE:
                //mTextView.setText(R.string.refresh_done);
                break;
            default:
        }

        mState = state;
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public boolean isRefreshHreader() {
        return mState!=STATE_NORMAL;
    }

    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        },200);
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        ViewGroup.LayoutParams params = getLayoutParams();
        if(params instanceof StaggeredGridLayoutManager.LayoutParams){
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams)params;
            lp.height = height;
            this.setLayoutParams(lp);
        }else if(params instanceof RecyclerView.LayoutParams){
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams)params;
            lp.height = height;
            this.setLayoutParams(lp);
        }else if(params instanceof GridLayoutManager.LayoutParams){
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams)params;
            lp.height = height;
            this.setLayoutParams(lp);
        }else{
            LayoutParams lp = (LayoutParams)params;
            lp.height = height;
            this.setLayoutParams(lp);
        }
    }

    @Override
    public View getHeaderView() {
        return this;
    }

    @Override
    public int getVisibleHeight() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        return lp.height;
    }

    @Override
    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        int destHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(STATE_NORMAL);
            }
        },500);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}