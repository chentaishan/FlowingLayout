# 1.BorderTextView 边框圆角组件

```
 * 快速实现文字组件添加 边框颜色，边框宽度，内容颜色，圆角
 * <p>
 * 1.可以调用setDefaultConfig 使用默认配置
 * 2.可以使用setSelfConfig 配置自己的实现
```



```java
mBorder = (BorderTextView) findViewById(R.id.border);

mBorder.setText("这是一个单个文本组件，实现边框 内容背景着色，文本颜色，文本大小");
mBorder.setPadding(5, 5, 5, 5);
mBorder.setDefaultConfig();
//mBorder.setSelfConfig(5, getResources().getColor(R.color.selected_bg_color), 1, getResources().getColor(R.color.stroke_color));

```





# 2.FlowingLayout

Android流式布局

```
 flowingLayout 只负责排序子元素，比如计算一行是否放的下子元素，如果放不下换行继续排放，仅此而已
```

##特色

以setAdapter形式注入数据

* 1.支持边框颜色
* 2.支持边框宽度
* 3.支持填充颜色
* 4.支持圆角弧度

效果图： 

![img](https://github.com/chentaishan/FlowingLayout/blob/ef90a5616f5a7a01d2df1ceec26036125318f8a4/images/image1.png)


1.如何使用

project--> build.gradle文件添加
```
allprojects {
    repositories {
        google()
        jcenter()
        
        maven{ url "https://jitpack.io"}
    }
}
```
2.app 添加依赖
```
   implementation 'com.github.cts33:FlowingLayout:2.0.0'
```
3.案例使用
```

     //准备两个资源shape，一个是item 被选中的资源，另一个未被选中的资源shape
        GradientDrawable selectedShape = createShape(getResources().getColor(R.color.selected_bg_color), getResources().getColor(R.color.stroke_color), 1, 10);
        GradientDrawable unSelectedShape = createShape(getResources().getColor(R.color.white), getResources().getColor(R.color.gray), 1, 10);


        mFlow = (FlowingLayout) findViewById(R.id.flow);
        //设置item 选中和未选的shape资源
        mFlow.setBgDrawable(selectedShape, unSelectedShape);
        //设置是否默认选中第一个
        mFlow.setSelectedFirstItem();

        //item 点击事件
        mFlow.setOnItemChangedListener(new FlowingLayout.OnItemChangedListener<String, BorderTextView>() {
            @Override
            public void itemClick(BorderTextView itemView, int pos, String dataItem) {

                Toast.makeText(MainActivity.this, "this is " + dataItem, Toast.LENGTH_SHORT).show();
            }
        });
        //设置child 左右间距
        mFlow.setChildLRMargin(20);
        mFlow.setChildTBMargin(5);
        //设置内间距
        mFlow.setChildPadding(15, 15, 15, 15);

        mFlow.setAdapter(commonFlowAdapter);

        commonFlowAdapter.addItems(list);
```
