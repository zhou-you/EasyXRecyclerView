package com.zhouyou.recyclerview;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.zhouyou.recyclerview.refresh.ArrowRefreshHeader;
import com.zhouyou.recyclerview.refresh.BaseLoadingFooter;
import com.zhouyou.recyclerview.refresh.IRefreshHeader;
import com.zhouyou.recyclerview.listener.AppBarStateChangeListener;
import com.zhouyou.recyclerview.refresh.LoadingMoreFooter;
import com.zhouyou.recyclerview.refresh.ProgressStyle;

import java.util.List;

/**
 * <p>描述:自定义带有刷新控制的XRecyclerView<br/></p>
 * <p>
 * setArrowImageView(int): //设置下拉刷新的箭头<br/>
 * setEmptyView(View)://没有数据时也可以设置空view<br/>
 * setFootView(View): //添加尾部view<br/>
 * setLoadingListener(LoadingListener): void<br/>
 * setLoadingMoreEnabled(boolean): //是否可以加载更多<br/>
 * setLoadingMoreProgressStyle(int): //设置加载更多动画样式<br/>
 * setNoMore(boolean): //没有更多数据<br/>
 * setPullRefreshEnabled(boolean): //是否可以上拉刷新<br/>
 * setRefreshHeader(ArrowRefreshHeader): void<br/>
 * setRefreshProgressStyle(int): //设置刷新动画样式<br/>
 * <p>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/31 10:29<br>
 * 版本： v3.0<br>
 * <p>
 * 修改者： zhouyou
 * 日期： 2016/12/14 9:47<br>
 * 提供了面向接口编程定制header的功能，去掉了程序中默认的ArrowRefreshHeader
 * 修改为接口BaseRefreshHeader。
 */
@SuppressWarnings("unchecked")
public class XRecyclerView extends RecyclerView {
    private Sections listener;
    private boolean isLoadingData = false;
    private boolean isNoMore = false;
    private int mRefreshProgressStyle = ProgressStyle.SysProgress;
    private int mLoadingMoreProgressStyle = ProgressStyle.SysProgress;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private WrapAdapter mWrapAdapter;
    private float mLastY = -1;
    private static final float DRAG_RATE = 3;
    private LoadingListener mLoadingListener;
    private IRefreshHeader mRefreshHeader;
    private BaseLoadingFooter mRefreshFooter;
    private boolean pullRefreshEnabled = true;
    private boolean loadingMoreEnabled = true;
    private boolean isEnabledScroll = true;
    //下面的ItemViewType是保留值(ReservedItemViewType),如果用户的adapter与它们重复将会强制抛出异常。不过为了简化,我们检测到重复时对用户的提示是ItemViewType必须小于10000
    public static final int TYPE_REFRESH_HEADER = 300000;//设置一个很大的数字,尽可能避免和用户的adapter冲突
    public static final int TYPE_LOADMORE_FOOTER = 300001;
    public static final int HEADER_INIT_INDEX = 300002;
    public static final int FOOTER_INIT_INDEX = 400002;
    //adapter没有数据的时候显示,类似于listView的emptyView
    private View mEmptyView;
    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();
    private AppBarStateChangeListener.State appbarState = AppBarStateChangeListener.State.EXPANDED;

    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (pullRefreshEnabled) {
            mRefreshHeader = new ArrowRefreshHeader(getContext());
            mRefreshHeader.setProgressStyle(mRefreshProgressStyle);
        }
        LoadingMoreFooter moreFooter = new LoadingMoreFooter(getContext());
        moreFooter.setProgressStyle(mLoadingMoreProgressStyle);
        mRefreshFooter = moreFooter;
        mRefreshFooter.getFooterView().setVisibility(GONE);
    }

    public void setListener(Sections listener) {
        this.listener = listener;
    }

    public void setFootViewText(String loading, String noMore) {
        mRefreshFooter.setLoadingHint(loading);
        mRefreshFooter.setNoMoreHint(noMore);
    }

    /**
     * 添加头部view
     */
    public void addHeaderView(View view) {
        if (view == null) {
            throw new RuntimeException("header is null");
        }
        mHeaderViews.put(HEADER_INIT_INDEX + mHeaderViews.size(), view);
        if (mWrapAdapter != null) {
            mWrapAdapter.notifyDataSetChanged();
        }
        requestLayout();
    }

    /**
     * 移除头部view
     */
    public void removeHeaderView(View view) {
        if (mHeaderViews.size() < 1) return;
        int index = mHeaderViews.indexOfValue(view);
        if (index == -1) return;
        mHeaderViews.removeAt(index);
        if (mWrapAdapter != null) {
            mWrapAdapter.notifyDataSetChanged();
        }
        requestLayout();
    }

    /**
     * 添加尾部view
     */
    public void addFooterView(View view) {
        if (view == null) {
            throw new RuntimeException("footer is null");
        }
        mFooterViews.put(FOOTER_INIT_INDEX + mFooterViews.size(), view);
        if (mWrapAdapter != null) {
            mWrapAdapter.notifyDataSetChanged();
        }
        requestLayout();
    }

    /**
     * 移除尾部view
     */
    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index == -1) return;
        mFooterViews.removeAt(index);
        if (mWrapAdapter != null) {
            mWrapAdapter.notifyDataSetChanged();
        }
        requestLayout();
    }

    //根据header的ViewType判断是哪个header
    private View getHeaderViewByType(int itemType) {
        if (!isHeaderType(itemType)) {
            return null;
        }
        return mHeaderViews.get(itemType);
    }

    //根据footer的ViewType判断是哪个footer
    private View getFooterViewByType(int itemType) {
        if (!isFooterType(itemType)) {
            return null;
        }
        return mFooterViews.get(itemType);
    }

    //判断一个type是否为HeaderType
    private boolean isHeaderType(int itemViewType) {
        return mHeaderViews.size() > 0 && mHeaderViews.get(itemViewType) != null;
    }

    //判断一个type是否为FooterType
    private boolean isFooterType(int itemViewType) {
        return mFooterViews.size() > 0 && mFooterViews.get(itemViewType) != null;
    }

    //判断是否是XRecyclerView保留的itemViewType
    private boolean isReservedItemViewType(int itemViewType) {
        return itemViewType == TYPE_REFRESH_HEADER || itemViewType == TYPE_LOADMORE_FOOTER || mHeaderViews.get(itemViewType) != null || mFooterViews.get(itemViewType) != null;
    }

    public void loadMoreComplete() {
        isLoadingData = false;
        mRefreshFooter.setState(BaseLoadingFooter.STATE_COMPLETE);
    }

    public void setNoMore(boolean noMore) {
        isLoadingData = false;
        isNoMore = noMore;
        mRefreshFooter.setState(isNoMore ? BaseLoadingFooter.STATE_NOMORE : BaseLoadingFooter.STATE_COMPLETE);
    }

    public void refresh() {
        if (pullRefreshEnabled && mLoadingListener != null) {
            mRefreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
            mLoadingListener.onRefresh();
        }
    }


    /**
     * 增加了是否在刷新,刷新中或者加载更多中
     */
    public boolean isRefresh() {
        return isRefreshing() || isLoadingMore();
    }

    /**
     * 增加了判断是否在刷新的判断
     * 1.上拉刷新中
     */
    public boolean isRefreshing() {
        return mRefreshHeader.isRefreshHreader();
    }

    /**
     * 增加了判断是否在加载更多的判断
     * 1.加载更多中
     */
    public boolean isLoadingMore() {
        //return ((BaseLoadingFooter) mFootView).isLoadingMore();
        return isLoadingData;
    }

    public void reset() {
        setNoMore(false);
        loadMoreComplete();
        refreshComplete();
    }

    public void refreshComplete() {
        mRefreshHeader.refreshComplete();
        setNoMore(false);
    }

    public void setRefreshHeader(IRefreshHeader refreshHeader) {
        mRefreshHeader = refreshHeader;
    }

    public void setLoadingMoreFooter(BaseLoadingFooter loadingFooter) {
        mRefreshFooter = loadingFooter;
        mRefreshFooter.getFooterView().setVisibility(GONE);
    }

    /**
     * 设置下拉刷新是否可用
     */
    public void setPullRefreshEnabled(boolean enabled) {
        pullRefreshEnabled = enabled;
    }

    public boolean isPullRefreshEnabled() {
        return pullRefreshEnabled;
    }

    /**
     * 设置上拉加载是否可用
     */
    public void setLoadingMoreEnabled(boolean enabled) {
        loadingMoreEnabled = enabled;
        if (!enabled) {
            mRefreshFooter.setState(BaseLoadingFooter.STATE_COMPLETE);
        }
    }

    public void setRefreshProgressStyle(int style) {
        mRefreshProgressStyle = style;
        if (mRefreshHeader != null) {
            mRefreshHeader.setProgressStyle(style);
        }
    }

    public void setLoadingMoreProgressStyle(int style) {
        mLoadingMoreProgressStyle = style;
        mRefreshFooter.setProgressStyle(style);
    }

    public void setArrowImageView(int resId) {
        if (mRefreshHeader != null) {
            mRefreshHeader.setArrowImageView(resId);
        }
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        mDataObserver.onChanged();
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    private Adapter mAdapter;

    @Override
    public void setAdapter(Adapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        mAdapter = adapter;
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    //避免用户自己调用getAdapter() 引起的ClassCastException
    @Override
    public Adapter getAdapter() {
        if (mWrapAdapter != null)
            return mWrapAdapter.getOriginalAdapter();
        else
            return null;
    }

    //这个包含了头、尾、内容
    public int getItemCount() {
        if (mWrapAdapter != null) {
            return mWrapAdapter.getItemCount();
        }
        return 0;
    }

    // 获取头的个数 不包含刷新头
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    // 获取尾的个数 不包含加载更多
    public int getFootersCount() {
        return mFooterViews.size();
    }

    //加载更多是否可用
    public boolean isLoadingMoreEnabled() {
        return loadingMoreEnabled;
    }

    public void setEnabledScroll(boolean enabledScroll) {
        isEnabledScroll = enabledScroll;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (mWrapAdapter != null) {
            if (layout instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) layout);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return isHeaderFooter(position) ? gridManager.getSpanCount() : 1;
                    }
                });

            }
        }
    }

    public boolean isHeaderFooter(int position) {
        boolean isHeader = mWrapAdapter.isHeader(position);
        boolean isFooter = mWrapAdapter.isFooter(position);
        boolean isLoadMoreFooter = mWrapAdapter.isLoadMoreFooter(position);
        boolean isRefreshHeader = mWrapAdapter.isRefreshHeader(position);
       /* Log.i("test", position + " ,isHeader: " + isHeader + " isFooter:" + isFooter
                + " isLoadMoreFooter:" + isLoadMoreFooter + " isRefreshHeader:" + isRefreshHeader);*/
        return isHeader || isFooter || isLoadMoreFooter || isRefreshHeader;
    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }


            //int head_foot_items_Count = mHeaderViews.size() + layoutManager.getItemCount() + 2;
//            int head_foot_items_Count = layoutManager.getItemCount();

           /* if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= head_foot_items_Count - 1 && 
                    head_foot_items_Count > layoutManager.getChildCount() 
                    && !isNoMore && mRefreshHeader.getState() < BaseRefreshHeader.STATE_REFRESHING) {*/
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 
                   /* && layoutManager.getItemCount() > layoutManager.getChildCount() */ //解决屏幕不满足一屏无法加载更多的问题
                    && !isNoMore && isEnabledScroll && mRefreshHeader.getState() < IRefreshHeader.STATE_REFRESHING) {
                isLoadingData = true;
                if (mRefreshFooter instanceof BaseLoadingFooter) {
                    mRefreshFooter.setState(LoadingMoreFooter.STATE_LOADING);
                } else {
                    mRefreshFooter.getFooterView().setVisibility(View.VISIBLE);
                }
                mLoadingListener.onLoadMore();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (isOnTop() && pullRefreshEnabled && isEnabledScroll && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                    mRefreshHeader.onMove(deltaY / DRAG_RATE);
                    if (mRefreshHeader.getVisibleHeight() > 0 && mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING) {
                        return false;
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if (isOnTop() && pullRefreshEnabled && isEnabledScroll && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                    if (mRefreshHeader.releaseAction()) {
                        if (mLoadingListener != null) {
                            mLoadingListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private boolean isOnTop() {
        if (mRefreshHeader.getHeaderView().getParent() != null) {
            return true;
        } else {
            return false;
        }
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
            if (mWrapAdapter != null && mEmptyView != null) {
                int emptyCount = 1 + mWrapAdapter.getHeadersCount();
                if (loadingMoreEnabled) {
                    emptyCount++;
                }
                if (mWrapAdapter.getItemCount() == emptyCount) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    XRecyclerView.this.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    XRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }

    public class WrapAdapter extends RecyclerView.Adapter<ViewHolder> {

        private RecyclerView.Adapter adapter;

        public WrapAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        public Adapter getOriginalAdapter() {
            return this.adapter;
        }

        public boolean isHeader(int position) {
            return position >= 1 && position < mHeaderViews.size() + 1;
        }

        public boolean isFooter(int position) {
            //Log.i("test", "position>>>>:" + position + " headerscount:" + getHeadersCount() + " contentscount:" + getContentsCount() + " itemcount:" + getItemCount());
            int adjLen = (loadingMoreEnabled ? 2 : 1);
            return position <= getItemCount() - adjLen && position > getItemCount() - adjLen - getFootersCount();
        }

        public boolean isLoadMoreFooter(int position) {
            if (loadingMoreEnabled) {
                return position == getItemCount() - 1;
            } else {
                return false;
            }
        }

        public boolean isRefreshHeader(int position) {
            return position == 0;
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFooterViews.size();
        }

        public int getContentsCount() {
            return adapter.getItemCount();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                return new SimpleViewHolder(mRefreshHeader.getHeaderView());
            } else if (isHeaderType(viewType)) {
                return new SimpleViewHolder(getHeaderViewByType(viewType));
            } else if (isFooterType(viewType)) {
                return new SimpleViewHolder(getFooterViewByType(viewType));
            } else if (viewType == TYPE_LOADMORE_FOOTER) {
                return new SimpleViewHolder(mRefreshFooter.getFooterView());
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (isHeader(position) || isRefreshHeader(position)) {
                return;
            }
            int adjPosition = position - (getHeadersCount() + 1);
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }

        // some times we need to override this
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
            if (isHeader(position) || isRefreshHeader(position)) {
                return;
            }

            int adjPosition = position - (getHeadersCount() + 1);
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    if (payloads.isEmpty()) {
                        adapter.onBindViewHolder(holder, adjPosition);
                    } else {
                        adapter.onBindViewHolder(holder, adjPosition, payloads);
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            int adjLen = (loadingMoreEnabled ? 2 : 1);
            if (adapter != null) {
                return getHeadersCount() + adapter.getItemCount() + adjLen + getFootersCount();
            } else {
                return getHeadersCount() + adjLen + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            int adjPosition = position - (getHeadersCount() + 1);
            if (isRefreshHeader(position)) {
                return TYPE_REFRESH_HEADER;
            }

            if (isHeader(position)) {
                position = position - 1;
                return mHeaderViews.keyAt(position);
            } else if (isFooter(position)) {
                //Log.i("test", "position:" + position + " headerscount:" + getHeadersCount() + " contentscount:" + getContentsCount() + " itemCount:" + getItemCount());
                position = position - getHeadersCount() - getContentsCount() - 1;
                return mFooterViews.keyAt(position);
            }

            if (isLoadMoreFooter(position)) {
                return TYPE_LOADMORE_FOOTER;
            }
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    int type = adapter.getItemViewType(adjPosition);
                    if (isReservedItemViewType(type)) {
                        throw new IllegalStateException("XRecyclerView require itemViewType in adapter should be less than 10000 ");
                    }
                    return type;
                }
            }
            return 0;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount() + 1) {
                int adjPosition = position - (getHeadersCount() + 1);
                if (adjPosition < adapter.getItemCount()) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        boolean isLister = listener != null && listener.isSections(position, isHeaderFooter(position));
                        int spanCount = (isHeaderFooter(position) || isLister) ? gridManager.getSpanCount() : 1;
                        return spanCount;
                    }
                });
            }
            adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            adapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(holder.getLayoutPosition()) || isRefreshHeader(holder.getLayoutPosition()) || isLoadMoreFooter(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            adapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            adapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            adapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
            return adapter.onFailedToRecycleView(holder);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            adapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            adapter.registerAdapterDataObserver(observer);
        }

        private class SimpleViewHolder extends RecyclerView.ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshing && pullRefreshEnabled && mLoadingListener != null) {
            mRefreshHeader.setState(IRefreshHeader.STATE_REFRESHING);
            mRefreshHeader.onMove(mRefreshHeader.getHeaderView().getMeasuredHeight());
            mLoadingListener.onRefresh();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //解决和CollapsingToolbarLayout冲突的问题
        AppBarLayout appBarLayout = null;
        ViewParent p = getParent();
        while (p != null) {
            if (p instanceof CoordinatorLayout) {
                break;
            }
            p = p.getParent();
        }
        if (p instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) p;
            final int childCount = coordinatorLayout.getChildCount();
            for (int i = childCount - 1; i >= 0; i--) {
                final View child = coordinatorLayout.getChildAt(i);
                if (child instanceof AppBarLayout) {
                    appBarLayout = (AppBarLayout) child;
                    break;
                }
            }
            if (appBarLayout != null) {
                appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                    @Override
                    public void onStateChanged(AppBarLayout appBarLayout, State state) {
                        appbarState = state;
                    }
                });
            }
        }
    }

    public interface Sections {
        boolean isSections(int position, boolean isHeadOrRefresh);
    }
}