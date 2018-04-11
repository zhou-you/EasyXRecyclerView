package com.zhouyou.recyclerview.bean;

/**
 * <p>描述：测试用的</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/31 11:24<br>
 * 版本： v2.0<br>
 */
public class ExampleBean {
    private String title;
    private String remark;
    private int id;

    public ExampleBean() {
    }

    public ExampleBean(String title, String remark) {
        this.title = title;
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public ExampleBean setId(int id) {
        this.id = id;
        return this;
    }
}
