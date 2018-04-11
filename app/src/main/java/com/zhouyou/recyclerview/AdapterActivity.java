package com.zhouyou.recyclerview;

import android.os.Bundle;
import android.widget.TextView;

import com.zhouyou.recyclerviewdemo.R;

/**
 * <p>描述：适配器讲解页面</p>
 * 作者： zhouyou<br>
 * 日期： 2016/10/27 16:24<br>
 * 版本： v2.0<br>
 */
public class AdapterActivity extends BaseActivity {
   TextView contentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        contentText = (TextView)findViewById(R.id.adapter_text);
        contentText.setText(getContent());
    }
    
    private String getContent(){
        StringBuilder builder=new StringBuilder();
        builder.append("1.BaseRecyclerViewAdapter采用泛型，万能适配器（采用泛型） 主要是针对RecyclerView的适配器" +
                "继承自RecyclerView.Adapter进行扩展，封装了常用功能，简化了操作")
        .append("\n")
        .append("\n")
        .append("2.HelperRecyclerViewAdapter继承自BaseRecyclerViewAdapter提供更便捷的操作，同时增加了adapter规范数据操作接口DataHelper，DataHelper与适配器数据紧密相关，请熟悉api")
        .append("\n")
        .append("\n")
        .append("3.BaseRecyclerViewHolder万能适配Holder,减少赘于代码和加快开发流程,HelperRecyclerViewHolder提供更便捷的操作")
        .append("\n")
        .append("\n")
        .append("4.可以自己定义和扩展adapter和holder,分别继承BaseRecyclerViewAdapter和BaseRecyclerViewHolder")
        .append("\n")
        .append("\n")
        .append("5.具体使用方式请参考MyAdapter讲解")
                .append("\n");
        return builder.toString();
    }
}
