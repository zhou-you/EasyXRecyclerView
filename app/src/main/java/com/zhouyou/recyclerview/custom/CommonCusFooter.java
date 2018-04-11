package com.zhouyou.recyclerview.custom;

import android.app.Activity;

import com.zhouyou.recyclerview.listener.RefreshLoadingListener;

/**
 * description:xxxxx
 * Author:  howard gong
 * Create:  2017/5/24 20:17
 */
public class CommonCusFooter implements RefreshLoadingListener<ClifeLoadingFooterDemo> {


    @Override
    public ClifeLoadingFooterDemo getHeaderFooter(Activity activity) {
        ClifeLoadingFooterDemo footer = new ClifeLoadingFooterDemo(activity);
        return footer;
    }
}
