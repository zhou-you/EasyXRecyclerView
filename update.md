## 更新日志

### 2019-04-25
V1.0.6
- 增加ArrowRefreshHeader可以设置文案颜色
使用方式例如：

```
IRefreshHeader refreshHeader = mRecyclerView.getRefreshHeader();
        if(refreshHeader instanceof ArrowRefreshHeader){
            ((ArrowRefreshHeader) refreshHeader).setStatusTextViewColor(R.color.color4);
        }
```
### 2018-09-03
V1.0.5
- 解决HelperRecyclerViewDragAdapter数组越界的问题

### 2018-08-08
V1.0.4
- 解决了HelperStateRecyclerViewAdapter适配器GridLayoutManager只有一条数据的时候显示异常的问题

### 2018-07-17
V1.0.3
- 修改了头部刷新会影响底部加载更多的问题

### 2018-06-08
V1.0.2
- 修改了item动画bug

### 2018-05-19
V1.0.1
- 优化代码结构

### 2018-04-11
V1.0.0
- 初始版本上传

