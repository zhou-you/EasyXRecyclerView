package com.zhouyou.recyclerview.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.zhouyou.recyclerview.refresh.BaseRefreshHeader;

/**
 * <p>描述：定制了自定义头部动画</p>
 * 
 * 作者： zhouyou<br>
 * 日期： 2016/12/14 9:47<br>
 * 版本： v2.0<br>
 *
 */
public class CustomRefreshHeader2 extends BaseRefreshHeader {
    private CustomAnimView mCustomAnimView;
    public CustomRefreshHeader2(Context context) {
        super(context);
    }

    public CustomRefreshHeader2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getView() {
        //方式一  
        mCustomAnimView = new CustomAnimView(getContext());
        return  mCustomAnimView;
        //方式二
        //return LayoutInflater.from(getContext()).inflate(R.layout.clife_loading_header,null);
    }
    
    //根据状态改变View
    @Override
    public void setState(int state) {
        super.setState(state);
        //选择自定义需要处理的状态：STATE_NORMAL、STATE_RELEASE_TO_REFRESH、STATE_REFRESHING、STATE_DONE
        //以下是我自定义动画需要用到的状态判断，你可以根据自己需求选择。
        if (state == STATE_REFRESHING) {    // 显示进度
            //这里处理自己的逻辑、刷新中
            mCustomAnimView.startAnim();
        } else if (state == STATE_DONE) {
            //这里处理自己的逻辑、刷新完成
            mCustomAnimView.stopAnim();
        } else {
            mCustomAnimView.startAnim();
        }
    }
    

   /* @Override
    public void refreshComplete() {
        //有默认处理、可以覆盖该方法处理，刷新完成的动作
    }*/

    /*@Override
    public void smoothScrollTo(int destHeight) {
        //super.smoothScrollTo(destHeight);
        //有默认处理、可以覆盖该方法处理，顺滑改变高度
    }*/
    
    //更多方法，可以通过覆写实现，自定义
}