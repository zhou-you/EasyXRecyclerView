package com.zhouyou.recyclerview.custom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhouyou.recyclerviewsdk.R;


/**
 * <p>描述：自定义一个Clife的头部动画效果</p>
 * 作者： zhouyou<br>
 * 为什么这里要独立出这么一个动画效果,而不直接写在刷新的头部View里面？？<br>
 * 不管什么样的刷新框架，都有动画效果，但是他们的实现逻辑的回调接口（下拉刷新、松开、刷新结束等）可能不一样。<br>
 * 但是动画效果本质都是小c动画，本类CLifeAnimView配合其他框架的回调接口就行了，不用重复定制这个动画，其他有需要这个动画的<br>
 * 把该类拷贝到你需要修改的框架中加以改造就可以了，该类基本不用动，除非动画效果变了！！<br>
 *     
 *     
 * 日期： 2016/12/14 14:21<br>
 * 版本： v2.0<br>
 */
public class CustomAnimView extends LinearLayout {
    private ImageView mImageView;
    private AnimationDrawable mAnimationDrawable;

    public CustomAnimView(Context context) {
        super(context);
        init(context,null);
    }

    public CustomAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        View header = LayoutInflater.from(context).inflate(R.layout.clife_loading_header2, this);
        mImageView =(ImageView) header.findViewById(R.id.pull_to_refresh_image);
        mImageView.setImageResource(R.drawable.clife_refresh_loading);
        mAnimationDrawable = (AnimationDrawable) mImageView.getDrawable();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    public void startAnim() {
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    public void stopAnim() {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }
}
