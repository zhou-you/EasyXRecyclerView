package com.zhouyou.recyclerview.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhouyou.recyclerview.refresh.BaseLoadingFooter;


/**
 * description:自定义foot
 * Author:  howard gong
 * Create:  2017/5/19 10:08
 */
public class ClifeLoadingFooterDemo extends LinearLayout implements BaseLoadingFooter {

    private LinearLayout mContainer;
    private CLifeAnimView mCLifeAnimView;
    private int mState;



    public ClifeLoadingFooterDemo(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public ClifeLoadingFooterDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }



    public void initView() {
        mCLifeAnimView = new CLifeAnimView(getContext());
        mContainer = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        mContainer.addView(mCLifeAnimView);
        mContainer.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void setLoadingHint(String hint) {

    }

    @Override
    public void setNoMoreHint(String hint) {

    }

    @Override
    public void setLoadingDoneHint(String hint) {

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
                mCLifeAnimView.startAnim();
                break;
            case STATE_COMPLETE:
                this.setVisibility(View.GONE);
                mCLifeAnimView.stopAnim();
                break;
            case STATE_NOMORE:
                this.setVisibility(View.GONE);
                mCLifeAnimView.stopAnim();
                break;
        }
    }

    @Override
    public View getFooterView() {
        return this;
    }

}
