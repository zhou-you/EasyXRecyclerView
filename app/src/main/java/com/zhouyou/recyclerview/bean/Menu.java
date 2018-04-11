package com.zhouyou.recyclerview.bean;

import java.io.Serializable;

/**
 * <p>描述：菜单对象</p>
 * 作者： zhouyou<br>
 * 日期： 2017/12/21 14:00 <br>
 * 版本： v1.0<br>
 */
public class Menu implements Serializable{
    public int id;
    public String title;
    public String describe;
    public int image;

    public Menu(int id, String title, String describe) {
        this.id = id;
        this.title = title;
        this.describe = describe;
    }
}
