package com.zhouyou.recyclerview.refresh;

import android.view.View;

/**
 * <p>描述：自定义加载更多的接口</p>
 * 作者： zhouyou<br>
 * 日期： 2017/3/27 10:18 <br>
 * 版本： v1.0<br>
 */
public interface IMoreFooter {
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    public void setLoadingHint(String hint);

    public void setNoMoreHint(String hint);

    public void setLoadingDoneHint(String hint);

    public void setProgressStyle(int style);

    public boolean isLoadingMore();

    public void setState(int state);

    /**
     * 返回当前自定义更多对象 this
     *
     * @return
     */
    View getFooterView();
}
