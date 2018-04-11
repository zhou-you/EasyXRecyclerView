package com.zhouyou.recyclerview.custom;

import android.app.Activity;

import com.zhouyou.recyclerview.custom.ClifeRefreshHeader2;
import com.zhouyou.recyclerview.listener.RefreshLoadingListener;


/**
 * description:自定义刷新头尾替换demo
 * Author:  howard gong
 * Create:  2017/5/24 19:03
 */
public class CommonCusHeader implements RefreshLoadingListener<ClifeRefreshHeader2> {


    @Override
    public ClifeRefreshHeader2 getHeaderFooter(Activity activity) {
        ClifeRefreshHeader2 header = new ClifeRefreshHeader2(activity);
        return header;
    }
}
