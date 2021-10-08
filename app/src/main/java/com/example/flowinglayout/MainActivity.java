package com.example.flowinglayout;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cts.flow.BorderTextView;
import com.cts.flow.CommonFlowAdapter;
import com.cts.flow.FlowingLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FlowingLayout mFlow;
    private List<String> list = new ArrayList<>();
    CommonFlowAdapter commonFlowAdapter = new CommonFlowAdapter<String, BorderTextView>(this,BorderTextView.class) {

        @Override
        public void convert(BorderTextView itemView, int position, String item) {
            itemView.setRadiusWidth(10);
            itemView.setStrokeConfig(1,Color.GRAY);
            itemView.setTextColor(Color.RED);
            itemView.setText(item);
        }

        @Override
        public String getItem(int i) {
            return mDatas.get(i);
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        mFlow = (FlowingLayout) findViewById(R.id.flow);
        mFlow.setChildLRMargin(20);
        mFlow.setChildTBMargin(5);
        mFlow.setPadding(15, 15, 15, 15);
//        mFlow.setBorder("#DAE0DC",1, Color.RED,15);
//        mFlow.setChildTextColor(Color);

        mFlow.setAdapter(commonFlowAdapter);

        commonFlowAdapter.addItems(list);


    }
}