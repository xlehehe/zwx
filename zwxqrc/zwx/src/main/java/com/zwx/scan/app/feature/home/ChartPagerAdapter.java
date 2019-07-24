package com.zwx.scan.app.feature.home;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
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
import com.zwx.scan.app.data.bean.Index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/28
 * desc   :
 * version: 1.0
 **/
public class ChartPagerAdapter extends FragmentPagerAdapter {

    private Index index;
    private Context context;
    private String title;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    private List<Map<String,Object>> memberList = new ArrayList<>();

    private List<Map<String,Object>> staffList = new ArrayList<>();

    private List<Map<String,Object>> campaignList = new ArrayList<>();

    public ChartPagerAdapter(FragmentManager fm, Index index, Context context) {
        super(fm);
        this.index = index;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
     /*   TextView textView = new TextView(container.getContext());
        textView.setText(mDataList.get(position));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(24);
        container.addView(textView);*/



        View view = LayoutInflater.from(context).inflate(R.layout.layout_chart,container,false);

        BarChart barChart = view.findViewById(R.id.chart);
        TextView tvDesc = view.findViewById(R.id.tv_desc);
        TextView tvdate = view.findViewById(R.id.tv_date);

        if(index == null){
            return view ;
        }
        memberList = index.getMemberTotalReport();

        staffList = index.getStaffTotalReport();
        campaignList = index.getCampaignTotalReport();
        tvdate.setText(index.getDate());
        if(position == 0){
            initChart(barChart,memberList);
        }else if(position == 1){
            initChart(barChart,campaignList);
        }else if(position == 2){
            initChart(barChart,staffList);
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    protected void initChart(BarChart barChart,List<Map<String,Object>> params){
        initBarChart(barChart);

        //处理数据是 记得判断每条柱状图对应的数据集合 长度是否一致
        LinkedHashMap<String, List<Float>> chartDataMap = new LinkedHashMap<>();
        List<String> xValues = new ArrayList<>();
        List<Float> yValue1 = new ArrayList<>();
//        List<Float> yValue2 = new ArrayList<>();
        List<Integer> colors = Arrays.asList( context.getResources().getColor(R.color.bar_color), context.getResources().getColor(R.color.bar_color)
        );
        if(params == null || params.size() == 0){
            return;
        }

        for (Map<String,Object> map:params){
            xValues.add((String)map.get("countDate"));
            yValue1.add(Float.parseFloat(String.valueOf(map.get("total"))));
        }
        chartDataMap.put(title, yValue1);

        showBarChart(barChart,xValues, chartDataMap, colors);
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
    public void showBarChart(BarChart barChart,final List<String> xValues, LinkedHashMap<String, List<Float>> dataLists,
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

}
