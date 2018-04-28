package com.zhouyou.recyclerview.refresh;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p>描述：fragment基类</p>
 *
 * @author zhouyou
 * @version v1.0
 * @date 2015-8-17 下午8:55:40
 */
@SuppressWarnings(value={"unchecked", "deprecation"})
public abstract class BaseFragment extends Fragment {
    public Context mContext;
    public Resources mResources;
    public LayoutInflater mInflater;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.mResources = mContext.getResources();
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View rootView;//缓存Fragment view

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutID(), container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindEvent();
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /* 摧毁视图 */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContext = null;
    }


    /**
     * 查找View
     *
     * @param view 父view
     * @param id   控件的id
     * @param <VT> View类型
     */
    protected <VT extends View> VT findView(View view, @IdRes int id) {
        return (VT) view.findViewById(id);
    }

    protected <VT extends View> VT findView(@IdRes int id) {
        return (VT) rootView.findViewById(id);
    }

    /**
     * 布局的LayoutID
     *
     * @return
     */
    protected abstract int getLayoutID();

    /**
     * 初始化子View
     */
    protected abstract void initView(View contentView);

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
