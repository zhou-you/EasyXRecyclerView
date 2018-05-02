package com.zhouyou.recyclerview.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class BaseMoreFooter extends LinearLayout implements IMoreFooter {
    private int mState;
    protected String loadingHint;
    protected String noMoreHint;
    protected String loadingDoneHint;

    public BaseMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public BaseMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    protected void initView() {
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
    }

    @Override
    public View getFooterView() {
        return this;
    }
}
