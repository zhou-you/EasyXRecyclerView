package com.zhouyou.recyclerview.bean;

/**
 * <p>描述：多itemben</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/31 15:18<br>
 * 版本： v2.0<br>
 */
public class MultipleItemBean extends TestBean {
    public MultipleItemBean(String name, String age) {
        super(name, age);
    }

    public MultipleItemBean(String name, String age, int itemType) {
        super(name, age);
        this.itemType = itemType;
    }

    private int itemType;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
