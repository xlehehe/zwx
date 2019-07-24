package com.zwx.scan.app.feature.home;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.zwx.scan.app.widget.chart.XYMarkerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeChartFragment extends Fragment {

   private  List<Map<String,Object>> params  = new ArrayList<>();

    @BindView(R.id.tv_date)
    protected TextView tvDate;
    @BindView(R.id.tv_desc)
    protected TextView tvDesc;

    @BindView(R.id.chart)
    protected BarChart barChart;
    private String title;
    private String date;

    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线


    private View view;

  /*  public HomeChartFragment() {
    }
*/


 /*   public static HomeChartFragment getInstance(String title,String date,List<Map<String,Object>> params) {
        HomeChartFragment fragment = new HomeChartFragment();
        fragment.params = params;
        fragment.title = title;
        fragment.date = date;
        return fragment;

    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//          setImmersiveStatusBar(true,getActivity().getResources().getColor(R.color.font_color_status_gray));
        if (view == null) {
            view = inflater.inflate(getlayoutId(),  container, false);
            ButterKnife.bind(this, view);
            initInjector();
            initView();
            initData();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    protected int getlayoutId() {
        return R.layout.fragment_home_chart;
    }

    protected void initInjector() {

    }

    protected void initView() {
        tvDesc.setText(title);
        tvDate.setText(date);
        initChart();
    }

    protected void initData() {

    }


     protected void initChart(){
        initBarChart(barChart);

        //处理数据是 记得判断每条柱状图对应的数据集合 长度是否一致
        LinkedHashMap<String, List<Float>> chartDataMap = new LinkedHashMap<>();
        List<String> xValues = new ArrayList<>();
        List<Float> yValue1 = new ArrayList<>();
//        List<Float> yValue2 = new ArrayList<>();
        List<Integer> colors = Arrays.asList( getResources().getColor(R.color.bar_color), getResources().getColor(R.color.bar_color)
        );



        if(params == null || params.size() == 0){
            return;
        }

       for (Map<String,Object> map:params){
           xValues.add((String)map.get("countDate"));
           yValue1.add(Float.parseFloat(String.valueOf(map.get("total"))));
       }
        chartDataMap.put(title, yValue1);

        showBarChart(xValues, chartDataMap, colors);
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //得到包含此柱状图的 数据集
                BarDataSet dataSets = (BarDataSet) barChart.getBarData().getDataSetForEntry(e);
//                dataSet1TextView.setText("被点击的柱状图名称：\n" + dataSets.getLabel() + "X轴：" + (int) e.getX() + "    Y轴" + e.getX());

                StringBuffer allBarChart = new StringBuffer();
                allBarChart.append("所有柱状图：\n");
                for (IBarDataSet dataSet : barChart.getBarData().getDataSets()) {
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

    private void initBarChart(BarChart barChart) {

        //背景颜色
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        //禁止拖拽
        barChart.setDragEnabled(false);
        //X轴或Y轴禁止缩放
        barChart.setScaleXEnabled(false);
        barChart.setScaleYEnabled(false);
        barChart.setScaleEnabled(false);
        //不显示边框
        barChart.setDrawBorders(false);
        //设置动画效果
        barChart.animateY(1000, Easing.Linear);
        barChart.animateX(1000, Easing.Linear);
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
        //不显示x轴网格线
        xAxis.setDrawGridLines(false);

        leftAxis = barChart.getAxisLeft();
        rightAxis = barChart.getAxisRight();
        //不显示右侧
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
//        rightAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(1100);
        //左侧Y轴网格线设置为虚线
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        //折线图例 标签 设置
        legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(true);
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
                /**
                 *  如果需要添加TAG标志 可使用以下构造方法
                 *  BarEntry(float x, float y, Object data)
                 *  e.getData()
                 */
                entries.add(new BarEntry(i, yValueList.get(i)));
            }
            // 每一个BarDataSet代表一类柱状图
            BarDataSet barDataSet = new BarDataSet(entries, name);
            initBarDataSet(barDataSet, colors.get(currentPosition));
            dataSets.add(barDataSet);

            currentPosition++;
        }

//X轴自定义值
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues.get((int) Math.abs(value) % xValues.size());

            }
        });
        //右侧Y轴自定义值
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) (value) +"";
            }
        });

        BarData data = new BarData(dataSets);

        /**
         * float groupSpace = 0.3f;   //柱状图组之间的间距
         * float barSpace =  0.05f;  //每条柱状图之间的间距  一组两个柱状图
         * float barWidth = 0.3f;    //每条柱状图的宽度     一组两个柱状图
         * (barWidth + barSpace) * 2 + groupSpace = (0.3 + 0.05) * 2 + 0.3 = 1.00
         * 3个数值 加起来 必须等于 1 即100% 按照百分比来计算 组间距 柱状图间距 柱状图宽度
         */
        int barAmount = dataLists.size(); //需要显示柱状图的类别 数量
        //设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
        float groupSpace = 0.3f; //柱状图组之间的间距
        float barWidth = (1f - groupSpace) / barAmount;
        float barSpace = 0f;
        //设置柱状图宽度
        data.setBarWidth(barWidth);
        //(起始点、柱状图组间距、柱状图之间间距)
        data.groupBars(0f, groupSpace, barSpace);
        barChart.setData(data);

        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(xValues.size());
        //将X轴的值显示在中央
        xAxis.setCenterAxisLabels(true);
    }


    /*private void showBarChart(final List<Map<String,Object>> dateValueList, String name, int color) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dateValueList.size(); i++) {

            BarEntry barEntry = new BarEntry(i, (float) dateValueList.get(i).get("total"));
            entries.add(barEntry);
        }
        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, name);
        initBarDataSet(barDataSet, color);
        //X轴自定义值
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (String)dateValueList.get((int) value % dateValueList.size()).get("countDate");

            }
        });


//        // 添加多个BarDataSet时
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(barDataSet);
//        BarData data = new BarData(dataSets);

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
    }
*/


    /*private void initChart() {
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(8);
        xAxis.setAxisMinimum(0);

        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
//        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, true);
//        leftAxis.setValueFormatter(xAxisFormatter);
        leftAxis.setSpaceTop(20f);

        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(1000);
        YAxis axisRight = chart.getAxisRight();
        axisRight.setEnabled(false);



        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv);
        chart.animateY(1500);
        setchartData();
    }


    private void setchartData() {
        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < 7; i++) {
            float val = (float) (Math.random() * (1000 + 1));

            values.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "趋势图");

            set1.setDrawIcons(false);


            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }*/
}
