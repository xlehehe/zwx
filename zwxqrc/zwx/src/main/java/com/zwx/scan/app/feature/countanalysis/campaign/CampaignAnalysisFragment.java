package com.zwx.scan.app.feature.countanalysis.campaign;


import android.graphics.RectF;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
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
import com.github.mikephil.charting.utils.MPPointF;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseFragment;
import com.zwx.scan.app.widget.chart.DayAxisValueFormatter;
import com.zwx.scan.app.widget.chart.XYMarkerView;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CampaignAnalysisFragment extends BaseFragment {

    @BindView(R.id.tv_campaign)
    protected TextView tvCampaign;

    @BindView(R.id.tv_get)
    protected TextView tvGet;

    @BindView(R.id.tv_receive)
    protected TextView tvReceive;

    @BindView(R.id.tv_date)
    protected TextView tvDate;


    @BindView(R.id.chart)
    protected BarChart chart;

    @BindView(R.id.ll_left)
    protected LinearLayout llLeft;

    @BindView(R.id.ll_right)
    protected LinearLayout llRight;


    private final RectF onValueSelectedRectF = new RectF();

    String params;
    public CampaignAnalysisFragment() {
        // Required empty public constructor
    }


    public static CampaignAnalysisFragment getInstance(String params) {
        // Required empty public constructor
        CampaignAnalysisFragment fragment = new CampaignAnalysisFragment();
        fragment.params = params;

        return fragment;

    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_campaign_analysis;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        if(params.equals("month")){
            llLeft.setVisibility(View.VISIBLE);
            llRight.setVisibility(View.VISIBLE);
        }else {
            llLeft.setVisibility(View.GONE);
            llRight.setVisibility(View.GONE);
        }
        initChart();
    }

    @Override
    protected void initData() {

    }

    private void initChart(){

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;

                RectF bounds = onValueSelectedRectF;
                chart.getBarBounds((BarEntry) e, bounds);
                MPPointF position = chart.getPosition(e, YAxis.AxisDependency.LEFT);

                Log.i("bounds", bounds.toString());
                Log.i("position", position.toString());

                Log.i("x-index",
                        "low: " + chart.getLowestVisibleX() + ", high: "
                                + chart.getHighestVisibleX());

                MPPointF.recycleInstance(position);
            }

            @Override
            public void onNothingSelected() {

            }
        });

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

      /*  IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)*/


      /*  Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);*/

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

           /* if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }*/
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
    }
}
