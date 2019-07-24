package com.zwx.scan.app.widget;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.zwx.scan.app.R;

import java.text.DecimalFormat;

/**
 * author : lizhilong
 * time   : 2018/12/03
 * desc   :
 * version: 1.0
 **/
public class CustomMarkerView extends MarkerView {

    private TextView tvContent;
    private DecimalFormat format;
    public CustomMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.tv_content_marker_view);
        format = new DecimalFormat("####");
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
//        float ss =e.getY();
        tvContent.setText("" + format.format(e.getY())); // set the entry-value as the display text
    }

 /*   @Override
    public void setOffset(MPPointF offset) {
        super.setOffset(offset);

    }

    @Override
    public void setOffset(float offsetX, float offsetY) {
        super.setOffset(offsetX, offsetY);

    }*/
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
   /*private IAxisValueFormatter xAxisValueFormatter;

    private DecimalFormat format;

    public CustomMarkerView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.layout_chart_markew);

        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = (TextView) findViewById(R.id.tvContent);
        format = new DecimalFormat("###");
    }

    //回调函数每次MarkerView重绘,可以用来更新内容(用户界面)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
//        tvContent.setText("x: " + xAxisValueFormatter.getFormattedValue(e.getX(), null) + ", y: " + format.format(e.getY()));
        tvContent.setText(e.getY()+"");
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }*/
}
