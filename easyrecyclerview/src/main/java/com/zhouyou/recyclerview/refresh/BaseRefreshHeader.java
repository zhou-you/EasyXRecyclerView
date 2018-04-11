package com.zhouyou.recyclerview.refresh;

import android.view.View;


/**
 * <p>描述：自定义刷新动画的接口</p>
 * 之前作者并没有面向接口编程，定制动画还是比较困难的。<br>
 * 后面在作者该接口的基础上扩展了一下方法，真正实现可以灵活定制刷新动画<br>
 * void setProgressStyle(int style);<br>
 * void setArrowImageView(int resid);<br>
 * void setState(int state);<br>
 * int getState();<br>
 * int getVisibleHeight();<br>
 * View getHeaderView();<br>
 * 
 * 作者： zhouyou<br>
 * 日期： 2016/12/14 9:47<br>
 * 版本： v2.0<br>
 *
 */
public interface BaseRefreshHeader {

    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();
    
    boolean isRefreshHreader();

    /**
     * 设置动画样式
     * @param style
     */
    void setProgressStyle(int style);

    /**
     * 设置动画的三角箭头  没有就不用处理
     * @param resid
     */
    void setArrowImageView(int resid);

    /**
     * 设置状态
     * @param state
     */
    void setState(int state);

    /**
     * 获取头部动画当前的转态
     * @return
     */
    int getState();

    /**
     * 获取头部view的高度
     * @return
     */
    int getVisibleHeight();

    /**
     * 返回当前自定义头部对象 this
     * @return
     */
    View getHeaderView();

}