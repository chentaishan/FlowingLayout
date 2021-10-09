package com.example.flowinglayout;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cts.flow.BorderTextView;
import com.cts.flow.CommonAdapter;
import com.cts.flow.FlowingLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FlowingLayout mFlow;
    private List<String> list = new ArrayList<>();

    private BorderTextView mBorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBorder = (BorderTextView) findViewById(R.id.border);

        mBorder.setText("这是一个单个文本组件，实现边框 内容背景着色，文本颜色，文本大小");
        mBorder.setPadding(5, 5, 5, 5);
        mBorder.setDefaultConfig();
//        mBorder.setSelfConfig(5, getResources().getColor(R.color.selected_bg_color), 1, getResources().getColor(R.color.stroke_color));


        initView();


    }

    private void initView() {


        list.add("YYYYYYYY");
        list.add("EEEEE");
        list.add("FFFFFFFF");
        list.add("布局");
        list.add("乌鲁木齐");
        list.add("布局");
        list.add("齐齐哈尔");
        list.add("布局");
        list.add("布局");
        list.add("齐齐哈尔");
        list.add("齐齐哈尔");
        list.add("乌鲁木齐");
        list.add("布局");
        list.add("乌鲁木齐");

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

    }


    /**
     * 设置shape
     *
     * @param shapeColor  内容区域颜色
     * @param strokeColor 边框颜色
     * @return
     */
    private GradientDrawable createShape(int shapeColor, int strokeColor, int strokeWidth, float cornerRadius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(shapeColor);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        gradientDrawable.setCornerRadius(cornerRadius);
        return gradientDrawable;
    }

    CommonAdapter commonFlowAdapter = new CommonAdapter<String, BorderTextView>(this, BorderTextView.class) {

        @Override
        public void convert(BorderTextView itemView, int position, String item) {

            itemView.setText(item);
        }

        @Override
        public String getItem(int i) {
            return mDatas.get(i);
        }
    };
}