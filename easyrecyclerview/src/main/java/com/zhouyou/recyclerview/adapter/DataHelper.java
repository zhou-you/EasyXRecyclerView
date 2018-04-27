package com.zhouyou.recyclerview.adapter;

import java.util.List;

/**
 * <p>描述:adapter规范数据操作接口<br/></p>
 * 作者： zhouyou<br>
 * 日期： 2016/4/21 10:29<br>
 * 版本： v2.0<br>
 */
public interface DataHelper<T> {

    boolean isEnabled(int position);

    /**
     * 添加单个数据到列表头部
     *
     * @param data
     */
    void addItemToHead(T data);

    /**
     * 添加单个数据到列表尾部
     *
     * @param data 数据
     */
    boolean addItemToLast(T data);

    /**
     * 添加数据集到列表头部
     *
     * @param datas
     */
    boolean addItemsToHead(List<T> datas);

    /**
     * 添加数据集到列表尾部
     *
     * @param datas
     */
    boolean addItemsToLast(List<T> datas);

    /**
     * 添加数据集合到指定位置
     *
     * @param startPosition 数据添加的位置
     * @param datas         数据集合
     */
    boolean addAll(int startPosition, List<T> datas);

    /**
     * 添加单个数据到指定位置
     *
     * @param position 数据添加的位置
     * @param data          数据
     */
    void add(int position, T data);

    /**
     * 获取index对于的数据
     *
     * @param index 数据座标
     * @return 数据对象
     */
    T getData(int index);

    /**
     * 将某一个数据修改
     *
     * @param oldData 旧的数据
     * @param newData 新的数据
     */
    void alterObj(T oldData, T newData);

    /**
     * 修改对应的位置的数据
     *
     * @param index 修改的位置
     * @param data  要代替的的数据
     */
    void alterObj(int index, T data);

    /**
     * 删除对应的数据
     *
     * @param data
     */
    boolean remove(T data);

    /**
     * 删除对应位置的数据
     *
     * @param index
     */
    void removeToIndex(int index);

    /**
     * 替换所有数据
     *
     * @param datas
     */
    void replaceAll(List<T> datas);

    /**
     * 清除所有
     */
    void clear();

    /**
     * 判断数据集合中是否包含这个对象
     *
     * @param data 判断对象
     * @return true|false
     */
    boolean contains(T data);


    /**
     * 覆盖所有数据
     *
     * @param datas
     */
    boolean setListAll(List<T> datas);
}
