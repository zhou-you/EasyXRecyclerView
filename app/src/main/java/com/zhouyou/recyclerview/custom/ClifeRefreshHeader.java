package com.zhouyou.recyclerview.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zhouyou.recyclerview.progressindicator.AVLoadingIndicatorView;
import com.zhouyou.recyclerview.refresh.BaseRefreshHeader;
import com.zhouyou.recyclerview.refresh.ProgressStyle;
import com.zhouyou.recyclerview.refresh.SimpleViewSwitcher;
import com.zhouyou.recyclerviewsdk.R;

/**
 * <p>描述：定制了Clife动画头部动画</p>
 * 同时可以支持本库中自带的其它27种动画<br>
 *     
 *      <p>《方式一》</p>
 *     
 * 作者： zhouyou<br>
 * 日期： 2016/12/14 9:47<br>
 * 版本： v2.0<br>
 *
 */
public class ClifeRefreshHeader extends LinearLayout implements BaseRefreshHeader {
    private LinearLayout mContainer;
    private SimpleViewSwitcher mProgressBar;
    private int mState = STATE_NORMAL;
    public int mMeasuredHeight;
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
        // 初始情况，设置下拉刷新view高度为0
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.clife_loading_header, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);
        mProgressBar = (SimpleViewSwitcher) findViewById(R.id.clife_listview_header_progressbar);
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.ClifeIndicator);
        mProgressBar.setView(progressView);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }


    @Override
    public void setArrowImageView(int resid) {
        
    }

    @Override
    public void setProgressStyle(int style) {
        if (style == ProgressStyle.SysProgress) {
            mProgressBar.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            mProgressBar.setView(progressView);
        }
    }

    @Override
    public void setState(int state) {
        if (state == mState) return;
        if (state == STATE_REFRESHING) {    // 显示进度
            mProgressBar.setVisibility(View.VISIBLE);
        } else if (state == STATE_DONE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {   
            mProgressBar.setVisibility(View.VISIBLE);
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
                                           setVisibleHeight((int) animation.getAnimatedValue());  
                                       }
                                   }
        );
        animator.start();
    }
}