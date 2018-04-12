package com.zhouyou.recyclerview.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhouyou.recyclerview.refresh.BaseRefreshHeader;

/**
 * <p>描述：定制了自定义头部动画</p>
 * 这个实现方式和其它动画效果实现方式完全不一样，没用采用Canvas绘制<br/>
 * 
 *  <p>《方式二》</p>
 * 
 * 两种方式都可以，内存观察分析这种效率会更高一些<br>
 * 
 * 作者： zhouyou<br>
 * 日期： 2016/12/14 9:47<br>
 * 版本： v2.0<br>
 *
 */
public class CustomRefreshHeader2 extends LinearLayout implements BaseRefreshHeader {
    private LinearLayout mContainer;
    private int mState = STATE_NORMAL;
    public int mMeasuredHeight;
    private CustomAnimView mCLifeAnimView;
    public CustomRefreshHeader2(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomRefreshHeader2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        // 初始情况，设置下拉刷新view高度为0
        mCLifeAnimView = new CustomAnimView(getContext());
        mContainer = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        mContainer.addView(mCLifeAnimView);
        mContainer.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
        
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.CENTER);
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
            mCLifeAnimView.startAnim();
        } else if (state == STATE_DONE) {
            mCLifeAnimView.stopAnim();
        } else {
            mCLifeAnimView.startAnim();
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
                CustomRefreshHeader2.this.reset();
            }
        },200);
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    @Override
    public View getHeaderView() {
        return this;
    }

    @Override
    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
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
                CustomRefreshHeader2.this.setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}