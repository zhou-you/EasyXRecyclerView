package com.zhouyou.recyclerview.group.bean;

import java.util.ArrayList;

/**
 * 组数据的实体类
 */
public class GroupBean {

    private String header;
    private String footer;
    private ArrayList<ChildBean> children;

    public GroupBean(String header, String footer, ArrayList<ChildBean> children) {
        this.header = header;
        this.footer = footer;
        this.children = children;
    }

    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public ArrayList<ChildBean> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChildBean> children) {
        this.children = children;
    }
}
