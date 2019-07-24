package com.zwx.scan.app.feature.countanalysis.campaign;


import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
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
import com.zwx.scan.app.data.bean.CampaignChartBean;
import com.zwx.scan.app.widget.CustomMarkerView;
import com.zwx.scan.app.widget.chart.XYMarkerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountMoreStoreChartFragment extends BaseFragment {

    @BindView(R.id.chart)
    protected BarChart chart;

    private String title;

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    private List<CampaignChartBean> dataList = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    //是否显示图例
    private  boolean isLegend = true;
    private String date ;
    public CountMoreStoreChartFragment() {
        // Required empty public constructor
    }

    public static CountMoreStoreChartFragment getInstance(List<CampaignChartBean> dataList){
        CountMoreStoreChartFragment chartFragment = new CountMoreStoreChartFragment();
        chartFragment.dataList = dataList;
//        chartFragment.title = chartTitle;
        return chartFragment;

    }


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_count_more_store_chart;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        if(getArguments() !=null){
//            isLegend = getArguments().getBoolean("isLegend");
            date = getArguments().getString("date");
            if(date == null){
                date = "day";
            }
        }

    }

    @Override
    protected void initData() {

        if(dataList != null && dataList.size() >0){
            initChart();
        }

    }


    public void initChart(){
        initBarChart(chart);

        //处理数据是 记得判断每条柱状图对应的数据集合 长度是否一致
        LinkedHashMap<String, List<Float>> chartDataMap = new LinkedHashMap<>();
        List<String> xValues = new ArrayList<>();


       /* List<Integer> colors = Arrays.asList( getResources().getColor(R.color.blue),
                getResources().getColor(R.color.orange),
                Color.RED,
                getResources().getColor(R.color.green),
                Color.YELLOW,Color.CYAN
                ,Color.MAGENTA,Color.LTGRAY,Color.DKGRAY
        );*/

        colors = new ArrayList<>();
        for(CampaignChartBean chartBean: dataList){


            String storeName = chartBean.getStoreName();
            colors.add(chartBean.getColor());
            List<Map<String,Object>> countList =chartBean.getCountList();

            List<Float> yValue = new ArrayList<>();
            for (Map<String,Object> map:countList){

                String total =map.get("total")!=null?String.valueOf(map.get("total")):"0";
                if(total==null){
                    total = "0";
                }

                yValue.add(Float.parseFloat(String.valueOf(total)));
//                float ss = 10+new Random().nextFloat()*10;
//                yValue.add(ss);

            }
            chartDataMap.put(storeName, yValue);

        }
        CampaignChartBean chartBean = dataList.get(0);
        if(chartBean!=null){
            List<Map<String,Object>> countList =chartBean.getCountList();
            for (Map<String,Object> map:countList){
                xValues.add((String)map.get("countDate"));
            }
        }
        showBarChart(xValues, chartDataMap, colors);

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
        if("month".equals(date)){
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
        barChart.setNoDataText("暂无数据！");

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

        leftAxis.setStartAtZero(true);
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

        legend.setWordWrapEnabled(false);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setEnabled(false);
    }


    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值（true显示）
        barDataSet.setDrawValues(false);
//        barDataSet.setValueTextSize(10f);
//        barDataSet.setValueTextColor(color);
    }

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

        if(xValues.size() == 0){
            return;
        }
        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                DecimalFormat format = new DecimalFormat("###");

                return xValues.get((int) Math.abs(value) % xValues.size());

            }
        };
        //X轴自定义值
        xAxis.setValueFormatter(xAxisFormatter);

//        leftAxis.resetAxisMaximum();

        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) (value) +"";
            }
        });
        BarData data = new BarData(dataSets);

        //设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
        int barAmount = dataLists.size(); //需要显示柱状图的类别 数量
        //设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
        float groupSpace = 0.3f; //柱状图组之间的间距
        float barWidth = (1f - groupSpace) / barAmount;
        float barSpace = 0.02f;

        //(起始点、柱状图组间距、柱状图之间间距)
        xAxis.setAxisMinimum(0f);
        if(xValues.size() == 1){
//            data.setBarWidth(0.1f);
//            data.groupBars(0f, 0.3f, 0.1f);


            if(barAmount ==1){
                data.setBarWidth(0.1f);
                data.groupBars(0f, 0.3f, 0.6f);
            }else if(barAmount == 2){
                //设置柱状图宽度
                data.setBarWidth(0.1f);
                //(起始点、柱状图组间距、柱状图之间间距)
//                data.groupBars(0.2f, groupSpace, 0.02f);
//                data.groupBars(0f, 0.3f, 0.6f);
                data.groupBars(0.2f, groupSpace, barSpace);
            }else if(barAmount == 3){
//                data.groupBars(0f, groupSpace, barSpace);
                data.setBarWidth(0.1f);
                //(起始点、柱状图组间距、柱状图之间间距)
//                data.groupBars(0.1f, groupSpace, 0.02f);
//                data.setBarWidth(barWidth);
                data.groupBars(0.18f, groupSpace, barSpace);
            }else if(barAmount == 4){
                data.setBarWidth(0.1f);
                //(起始点、柱状图组间距、柱状图之间间距)
//                data.groupBars(0.1f, groupSpace, 0.02f);
//                data.setBarWidth(barWidth);
                data.groupBars(0.2f, groupSpace, barSpace);
            }else if(barAmount == 5){
                data.setBarWidth(0.1f);
                //(起始点、柱状图组间距、柱状图之间间距)
//                data.groupBars(0.1f, groupSpace, 0.02f);
//                data.setBarWidth(barWidth);
                data.groupBars(0.21f, groupSpace, barSpace);
            }

        }else if(xValues.size() == 7){
//            data.setBarWidth(0.8f);
//            data.groupBars(0f, 0.1f, 0.1f);
            data.setBarWidth(barWidth);
            data.groupBars(0f, groupSpace, barSpace);
        }else {
//            data.setBarWidth(0.8f);
//            data.groupBars(0f, 0.1f, 0.1f);
            data.setBarWidth(barWidth);
            data.groupBars(0f, groupSpace, barSpace);
        }


        xAxis.setAxisMaximum(xValues.size());
        chart.setData(data);
        //将X轴的值显示在中央
        xAxis.setCenterAxisLabels(true);

//        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        float max = chart.getAxisLeft().getAxisMaximum();
//        leftAxis.resetAxisMaximum();

  /*      if(max<=10){
            leftAxis.setAxisMaximum(max+10);
        }else if(max <= 20 &&max> 10) {
            leftAxis.setAxisMaximum(max+10);
        }else if(max <= 40 &&max > 20) {
            leftAxis.setAxisMaximum(max+10);
        }else if(max <= 60 &&max > 40) {
            leftAxis.setAxisMaximum(max+10);
        }else if(max <= 80 &&max > 60) {
            leftAxis.setAxisMaximum(max+10);
        }else if(max <= 100 &&max > 80) {
            leftAxis.setAxisMaximum(10);
        }else {
            leftAxis.setAxisMaximum(max+10);
        }*/
        leftAxis.setAxisMaximum(max+10);
        MarkerView mv = new CustomMarkerView(getActivity(),R.layout.layout_chart_markew);
        mv.setChartView(chart);
        chart.setMarker(mv);
        chart.invalidate();
    }
}
