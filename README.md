# FlowingLayout
Android流式布局

##特色

以setAdapter形式注入数据

* 1.支持边框颜色
* 2.支持边框宽度
* 3.支持填充颜色
* 4.支持圆角弧度

效果图：
[1]: https://github.com/chentaishan/FlowingLayout/blob/ef90a5616f5a7a01d2df1ceec26036125318f8a4/images/img1.png


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
 implementation 'com.github.chentaishan:FlowingLayout:-SNAPSHOT'
```
3.案例使用
```

public class MainActivity extends AppCompatActivity {

    private FlowingLayout mFlow;
    private List<String> list = new ArrayList<>();
    CommonFlowAdapter commonFlowAdapter=    new CommonFlowAdapter<String>(this){

        @Override
        public String getItem(int i) {
            return mDatas.get(i);
        }

        @Override
        public void convert(FlowHolder holder, String item, int position) {

            holder.setText(R.id.title,item);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {

        list.add("111");
        list.add("2");
        list.add("3344444444433");
        list.add("YYYYYYYY");
        list.add("EEEEE");
        list.add("FFFFFFFF");

        mFlow = (FlowingLayout) findViewById(R.id.flow);
        mFlow.setChildLRMargin(10);
        mFlow.setChildTBMargin(5);
        mFlow.setPadding(15,5,15,5);
        mFlow.setBorder("#DAE0DC",1, Color.RED,15);
        mFlow.setChildTextColor(Color.parseColor("#262E79"));

        mFlow.setAdapter(commonFlowAdapter);

        commonFlowAdapter.addItems(list);

        commonFlowAdapter.notifyDataSetChanged();

    }
}
```
