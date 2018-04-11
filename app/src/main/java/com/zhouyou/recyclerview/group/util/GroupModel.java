package com.zhouyou.recyclerview.group.util;


import com.zhouyou.recyclerview.group.bean.ChildBean;
import com.zhouyou.recyclerview.group.bean.ExpandableGroupBean;
import com.zhouyou.recyclerview.group.bean.GroupBean;

import java.util.ArrayList;

/**
 * Depiction:
 * Author: teach
 * Date: 2017/3/20 15:51
 */
public class GroupModel {

    /**
     * 获取组列表数据
     *
     * @param groupCount    组数量
     * @param childrenCount 每个组里的子项数量
     * @return
     */
    public static ArrayList<GroupBean> getGroups(int groupCount, int childrenCount) {
        ArrayList<GroupBean> groups = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            ArrayList<ChildBean> children = new ArrayList<>();
            for (int j = 0; j < childrenCount; j++) {
                children.add(new ChildBean("第" + (i + 1) + "组第" + (j + 1) + "项"));
            }
            groups.add(new GroupBean("第" + (i + 1) + "组头部",
                    "第" + (i + 1) + "组尾部", children));
        }
        return groups;
    }

    /**
     * 获取可展开收起的组列表数据(默认展开)
     *
     * @param groupCount    组数量
     * @param childrenCount 每个组里的子项数量
     * @return
     */
    public static ArrayList<ExpandableGroupBean> getExpandableGroups(int groupCount, int childrenCount) {
        ArrayList<ExpandableGroupBean> groups = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            ArrayList<ChildBean> children = new ArrayList<>();
            for (int j = 0; j < childrenCount; j++) {
                children.add(new ChildBean("第" + (i + 1) + "组第" + (j + 1) + "项"));
            }
            groups.add(new ExpandableGroupBean("第" + (i + 1) + "组头部",
                    "第" + (i + 1) + "组尾部", children, true));
        }
        return groups;
    }

}
