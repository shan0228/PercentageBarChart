package com.yss.percentagebarchart;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//TextView  tv;
    PercentageBarChart2View percentageBarChart2View;
    PercentageBarChartView percentageBarChartView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        percentageBarChartView = (PercentageBarChartView) findViewById(R.id.chart1View);
        int[] color={Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN};
        percentageBarChartView.setBarColor(color);
        List<PercentageBarChartView.ItemData> list=new ArrayList<>();
        double[]data={55,55,55,55};
        double[]data1={55,20,30,70};
        PercentageBarChartView.ItemData itemData=new PercentageBarChartView(this).new ItemData("2012-2-2",data);
        list.add(itemData);
        PercentageBarChartView.ItemData itemData1=new PercentageBarChartView(this).new ItemData("2014-2-2",data1);
        list.add(itemData1);
        list.add(itemData1);
        list.add(itemData1);
        list.add(itemData1);
        list.add(itemData1);
        list.add(itemData);
        list.add(itemData);
        list.add(itemData);

        percentageBarChartView.setChartData(list );
//        percentageBarChart2View = (PercentageBarChart2View) findViewById(R.id.chart2View);
//
//        tv= (TextView) findViewById(R.id.tv);
//        tv.setBackground(getResources().getDrawable(R.drawable.bg_shape));
//        double[] scal={0.2,0.3,0.4,0.1};
//        percentageBarChart2View.setScales(scal);
    }

}
