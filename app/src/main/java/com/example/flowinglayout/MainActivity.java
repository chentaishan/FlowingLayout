package com.example.flowinglayout;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.cts.flow.CommonFlowAdapter;
import com.cts.flow.FlowingLayout;

import java.util.ArrayList;
import java.util.List;

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

            holder.setText(item);
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
        mFlow.setPadding(15,15,15,15);
//        mFlow.setBorder("#DAE0DC",1, Color.RED,15);
        mFlow.setChildTextColor(Color.parseColor("#262E79"));

        mFlow.setAdapter(commonFlowAdapter);

        commonFlowAdapter.addItems(list);


    }
}