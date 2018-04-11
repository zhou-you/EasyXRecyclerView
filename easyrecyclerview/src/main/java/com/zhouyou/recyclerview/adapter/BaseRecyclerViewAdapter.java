package com.zhouyou.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <p>描述:万能适配器（采用泛型） 主要是针对RecyclerView的适配器<br/></p>
 * <p>
 * 1.(更加厉害)  抽取出来的模板适配器，任何适配器继承该适配器，就很容易实现适配器功能。<br/>
 * 2.支持多个item<br/>
 * 3.RecyclerView不同与AbsListView(ListView、GrideView)具有点击setOnItemClickListener事件监听
 * 故本代码中自己新增了setOnItemClickListener、setOnItemLongClickListener，来解决RecyclerView的OnItem事件
 * <br/>
 * 作者： zhouyou<br>
 * 日期： 2016/8/25 10:29<br>
 * 版本： v3.0<br>
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BH> {
    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater mLInflater;
    protected int[] mLayoutIds;
    private SparseArray<View> mConvertViews = new SparseArray<View>();
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public BaseRecyclerViewAdapter(List<T> data, Context context, int... layoutId) {
        this.mList = data;
        this.mLayoutIds = layoutId;
        this.mContext = context;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    public BaseRecyclerViewAdapter(Context context, int... layoutIds) {
        this.mLayoutIds = layoutIds;
        this.mContext = context;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    /**
     * <p>在初始化的时候不能确定layoutId,才可以不提供,但是必须重checkLayoutId方法</p>
     *
     * @param data    数据源
     * @param context 上下文
     */
    public BaseRecyclerViewAdapter(List<T> data, Context context) {
        this.mList = data;
        this.mContext = context;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position < getItemCount())
            return checkLayout(mList.get(position), position);
        else
            return position;
    }

    @Override
    public BH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0 || viewType > mLayoutIds.length) {
            throw new ArrayIndexOutOfBoundsException("checkLayout > LayoutId.length");
        }
        if (mLayoutIds.length == 0) {
            throw new IllegalArgumentException("not layoutId");
        }
        int layoutId = mLayoutIds[viewType];
        View view = inflateItemView(layoutId, parent);
        //因为Sticky也要用到tag,所有采用多tag的方式处理，产生一个唯一的key值
        BaseRecyclerViewHolder viewHolder = (BaseRecyclerViewHolder) view.getTag("holder".hashCode());
        if (viewHolder == null || viewHolder.getLayoutId() != layoutId) {
            viewHolder = createViewHolder(view, layoutId);
            return viewHolder;
        }
        return viewHolder;
    }

    //若果想用自定义的ViewHolder，可以进行自定义，但是必须继承自BaseRecyclerViewHolder
    protected BaseRecyclerViewHolder createViewHolder(View view,int layoutId) {
        return new BaseRecyclerViewHolder(view, layoutId);
    }

    /**
     * 解析布局资源
     *
     * @param layoutId
     * @param viewGroup
     */
    protected View inflateItemView(int layoutId, ViewGroup viewGroup) {
        View convertView = mConvertViews.get(layoutId);
        if (convertView == null) {
            convertView = mLInflater.inflate(layoutId,
                    viewGroup, false);
        }
        return convertView;
    }

    @Override
    public void onBindViewHolder(BH holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {// payloads 为 空，说明是更新整个 ViewHolder
            onBindViewHolder(holder, position);
        } else {// payloads 不为空，这只更新需要更新的 View 即可。
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onBindViewHolder(BH holder, int position) {
        if (position < 0)
            throw new RuntimeException("The position is less than 0 !!!!!!" + position);
        final T item = mList.get(position);
        // 绑定数据
        onBindData(holder, position, item);

        //绑定监听事件
        onBindItemClickListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 绑定数据到Item View上
     *
     * @param viewHolder
     * @param position   数据的位置
     * @param item       数据项
     */
    protected abstract void onBindData(BH viewHolder, int position, T item);

    public int checkLayout(T item, int position) {
        return 0;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public interface OnItemClickListener<T> {
        void onItemClick(View view, T item, int position);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View view, T item, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 注册item点击、长按事件
     *
     * @param holder
     * @param position
     */
    protected final void onBindItemClickListener(final BH holder, final int position) {
        if (null != mOnItemClickListener)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //final int position = holder.getAdapterPosition() - getHeaderViewCount() - 1;
                    mOnItemClickListener.onItemClick(view, mList.get(position), position);
                }
            });

        if (null != mOnItemLongClickListener)
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //final int position = holder.getAdapterPosition() - getHeaderViewCount() - 1;
                    mOnItemLongClickListener.onItemLongClick(v, mList.get(position), position);
                    return true;
                }
            });
    }
}
