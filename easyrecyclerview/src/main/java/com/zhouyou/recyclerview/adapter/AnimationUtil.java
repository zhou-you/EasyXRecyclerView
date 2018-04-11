package com.zhouyou.recyclerview.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Interpolator;

public class AnimationUtil {
    private AnimationType mAnimationType = AnimationType.ALPHA;
    private Animator mCustomAnimator;
    private View mTargetView;
    private Interpolator mInterpolator;
    private int mDuration = 300;

    public AnimationUtil() {
    }

    public AnimationUtil setAnimationType(AnimationType animationType) {
        mAnimationType = animationType;
        return this;
    }

    public AnimationUtil setCustomAnimation(Animator animator) {
        mCustomAnimator = animator;
        return this;
    }

    public AnimationUtil setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    public AnimationUtil setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        return this;
    }

    public AnimationUtil setTargetView(View targetView) {
        mTargetView = targetView;
        return this;
    }

    public void start() {
        if (null != mCustomAnimator) {
            mCustomAnimator.start();
        } else if (null == mTargetView) {
            throw new IllegalArgumentException("You must set a target view!");
        } else {
            startAnimation(mAnimationType);
        }
    }

    private void startAnimation(AnimationType animationType) {
        AnimatorSet animatorSet = new AnimatorSet();
        switch (animationType) {
            case ALPHA:
                animatorSet.play(ObjectAnimator.ofFloat(mTargetView, "alpha", 0.7f, 1f));
                break;
            case SCALE:
                animatorSet.playTogether(ObjectAnimator.ofFloat(mTargetView, "scaleX", 0.6f, 1f)
                        , ObjectAnimator.ofFloat(mTargetView, "scaleY", 0.6f, 1f));
                break;
            case SLIDE_FROM_BOTTOM:
                animatorSet.play(ObjectAnimator.ofFloat(mTargetView, "translationY", mTargetView
                        .getMeasuredHeight(), 0));
                break;
            case SLIDE_FROM_LEFT:
                animatorSet.play(ObjectAnimator.ofFloat(mTargetView, "translationX", -mTargetView
                        .getRootView().getWidth(), 0));
                break;
            case SLIDE_FROM_RIGHT:
                animatorSet.play(ObjectAnimator.ofFloat(mTargetView, "translationX", mTargetView
                        .getRootView().getWidth(), 0));
                break;
        }

        if (null != mInterpolator) {
            animatorSet.setInterpolator(mInterpolator);
        }
        animatorSet.setDuration(mDuration);
        animatorSet.start();
    }
}
