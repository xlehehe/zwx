package com.zwx.scan.app.feature.verificationrecord;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.widget.CustomMarkerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chart2Fragment extends BaseFragment  {

    @BindView(R.id.chart)
    protected BarChart chart;

    private String title;

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    private List<Map<String,Object>> dataList = new ArrayList<>();

    //是否显示图例
    private  boolean isLegend = true;
    private String date ;
    public Chart2Fragment() {
        // Required empty public constructor
    }

    public static  Chart2Fragment getInstance(String chartTitle,List<Map<String,Object>> dataList){
        Chart2Fragment chartFragment = new Chart2Fragment();
        chartFragment.dataList = dataList;
        chartFragment.title = chartTitle;
        return chartFragment;
    }


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_chart;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

        if(getArguments() !=null){
            isLegend = getArguments().getBoolean("isLegend");
            date = getArguments().getString("date");
            title = getArguments().getString("title");
            if(date == null){
                date = "day";
            }
        }


    }

    @Override
    protected void initData() {
        if (dataList == null) {
            return;
        }
        initChart();
    }


    public void initChart(){
        initBarChart(chart);

        //处理数据是 记得判断每条柱状图对应的数据集合 长度是否一致
        LinkedHashMap<String, List<Float>> chartDataMap = new LinkedHashMap<>();
        List<String> xValues = new ArrayList<>();
        List<Float> yValue = new ArrayList<>();
        List<Integer> colors = Arrays.asList( getResources().getColor(R.color.bar_color), getResources().getColor(R.color.bar_color)
        );

        for (Map<String,Object> map:dataList){
            xValues.add((String)map.get("countDate"));
            yValue.add(Float.parseFloat(String.valueOf(map.get("total"))));
        }
        chartDataMap.put(title, yValue);

        showBarChart(xValues, chartDataMap, colors);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //得到包含此柱状图的 数据集
                BarDataSet dataSets = (BarDataSet) chart.getBarData().getDataSetForEntry(e);

                StringBuffer allBarChart = new StringBuffer();
                allBarChart.append("所有柱状图：\n");
                for (IBarDataSet dataSet : chart.getBarData().getDataSets()) {
                    BarEntry entry = dataSet.getEntryForIndex((int) e.getX());
                    allBarChart.append(dataSet.getLabel())
                            .append("X轴：")
                            .append((int) entry.getX())
                            .append("    Y轴")
                            .append(entry.getY())
                            .append("\n");
                }
//                dataSet2TextView.setText(allBarChart.toString());
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    // 初始化BarChart图表

    public void initBarChart(BarChart barChart) {

        //背景颜色
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);

        //X轴或Y轴禁止缩放
        if(date.equals("month")){
            barChart.setScaleXEnabled(true);
            barChart.setDragEnabled(true);
            barChart.setScaleEnabled(true);

        }else {
            //禁止拖拽
            barChart.setDragEnabled(false);
            barChart.setScaleEnabled(false);
            barChart.setScaleXEnabled(false);
        }

        barChart.setScaleYEnabled(false);

        //不显示边框
        barChart.setDrawBorders(false);
        //设置动画效果
        barChart.animateY(1000, Easing.Linear);
        //不显示右下角描述
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);


        //XY轴的设置
        //X轴设置显示位置在底部
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //标签显示7个
        xAxis.setLabelCount(7);
        //不显示x轴网格线
        xAxis.setDrawGridLines(false);

        leftAxis = barChart.getAxisLeft();

        rightAxis = barChart.getAxisRight();
        //不显示右侧
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
//        leftAxis.setAxisMaximum(1100);

        //左侧Y轴网格线设置为虚线
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        //折线图例 标签 设置
        legend = barChart.getLegend();
//        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //是否绘制在图表里面
        legend.setDrawInside(false);

//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        if (isLegend){
            legend.setEnabled(true);
        }else {
            legend.setEnabled(false);
        }

    }


    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(false);
//        barDataSet.setValueTextSize(10f);
//        barDataSet.setValueTextColor(color);
    }

    /**
     * @param xValues   X轴的值
     * @param dataLists LinkedHashMap<String, List<Float>>
     *                  key对应柱状图名字  List<Float> 对应每类柱状图的Y值
     * @param colors
     */
    public void showBarChart(final List<String> xValues, LinkedHashMap<String, List<Float>> dataLists,
                             @ColorRes List<Integer> colors) {

        List<IBarDataSet> dataSets = new ArrayList<>();
        int currentPosition = 0;//用于柱状图颜色集合的index

        for (LinkedHashMap.Entry<String, List<Float>> entry : dataLists.entrySet()) {
            String name = entry.getKey();
            List<Float> yValueList = entry.getValue();

            List<BarEntry> entries = new ArrayList<>();

            for (int i = 0; i < yValueList.size(); i++) {

                entries.add(new BarEntry(i, yValueList.get(i)));
            }
            // 每一个BarDataSet代表一类柱状图
            BarDataSet barDataSet = new BarDataSet(entries, name);
            initBarDataSet(barDataSet, colors.get(currentPosition));
            dataSets.add(barDataSet);

            currentPosition++;
        }

        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return xValues.get((int) Math.abs(value) % xValues.size());

            }
        };
//X轴自定义值
        xAxis.setValueFormatter(xAxisFormatter);
//        leftAxis.resetAxisMaximum();

        leftAxis.setAxisMaximum(leftAxis.getAxisMaximum()+70);

        //右侧Y轴自定义值
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) (value) +"";
            }
        });

        BarData data = new BarData(dataSets);

        int barAmount = dataLists.size(); //需要显示柱状图的类别 数量
        //设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
       /* float groupSpace = 0.3f; //柱状图组之间的间距
        float barWidth = (1f - groupSpace) / barAmount;
        float barSpace = 0.3f;*/
        float groupSpace=0.3f;
        float barSpace=0.3f;
        float barWidth=0.55f;
        //设置柱状图宽度

        //(起始点、柱状图组间距、柱状图之间间距)



        xAxis.setAxisMinimum(0f);

        if(xValues.size() == 1){
            data.setBarWidth(0.1f);
            data.groupBars(0f, 0.3f, 0.6f);
            xAxis.setAxisMaximum(xValues.size());
        }else if(xValues.size() == 7){
            xAxis.setAxisMaximum(xValues.size());
            data.setBarWidth(0.8f);
            data.groupBars(0f, groupSpace, 0.4f);
        }else {
            xAxis.setAxisMaximum(xValues.size());
            data.setBarWidth(0.8f);
            data.groupBars(0f, 0.1f, 0.1f);
        }

        chart.setData(data);
        //将X轴的值显示在中央
        xAxis.setCenterAxisLabels(true);
//        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        CustomMarkerView mv = new CustomMarkerView(getActivity(),R.layout.layout_chart_markew);
//        MarkerView mv = new CustomMarkerView(getActivity(),xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv);

       /* if(date.equals("month")){
            chart.invalidate();
            Matrix mMatrix = new Matrix();
            mMatrix.postScale(2f, 1f);
            chart.getViewPortHandler().refresh(mMatrix, chart, false);
        }*/

        chart.invalidate();
    }
}
