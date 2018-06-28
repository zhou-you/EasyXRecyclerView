package com.zhouyou.recyclerview.sticky.adapter;

/**
 * <p>描述：ItemDecoration 悬停 对应的实体bean</p>
 * 作者： zhouyou<br>
 * 日期： 2018/6/27 11:18 <br>
 * 版本： v1.0<br>
 */
public class StockInfo {
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_DATA = 2;

    private int itemType;
    public String name;
    public String title;

    public int getItemType() {
        return itemType;
    }

    public StockInfo(int itemType, String name, String title) {
        this.itemType = itemType;
        this.name = name;
        this.title = title;
    }

    public StockInfo setItemType(int itemType) {
        this.itemType = itemType;
        return this;
    }

    public String getName() {
        return name;
    }

    public StockInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public StockInfo setTitle(String title) {
        this.title = title;
        return this;
    }
}
