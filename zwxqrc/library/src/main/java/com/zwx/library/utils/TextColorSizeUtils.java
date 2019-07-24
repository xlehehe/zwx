package com.zwx.library.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.math.BigDecimal;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class TextColorSizeUtils {
    /**
     * 更改TextView某一段字体的颜色值
     *
     * @param context
     * @param text
     * @param subTextArray
     * @return
     */
    public static SpannableStringBuilder getTextSpan(Context context,
                                                     int subTextBgColor,
                                                     String text, String... subTextArray) {
        if (context == null || text == null || subTextArray == null)
            return null;
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        int begin = 0;
        int end = 0;
        for (int i = 0; i < subTextArray.length; i++) {
            String subText = subTextArray[i];
            begin = text.indexOf(subText, end);
            end = begin + subText.length();
            //
            style.setSpan(new ForegroundColorSpan(subTextBgColor), begin, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return style;
    }


    /**
     * 更改TextView某一段字体的颜色(字体带圆角背景)
     *
     * @param context
     * @param text
     * @param subTextArray
     * @return
     */
    public static SpannableStringBuilder getTextSpan(Context context,
                                                     int subTextColor, int subTextBgColor, int radius,
                                                     String text, String... subTextArray) {
        if (context == null || text == null || subTextArray == null) {
            return null;
        }
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        int begin = 0;
        int end = 0;
        for (int i = 0; i < subTextArray.length; i++) {
            String subText = subTextArray[i];
            begin = text.indexOf(subText, end);
            end = begin + subText.length();
            //
            style.setSpan(new RadiusBackgroundSpan(subTextColor, subTextBgColor, radius), begin, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return style;
    }

    /**
     * @param context
     * @param text
     * @param subTextArray
     * @return
     */
    public static SpannableStringBuilder getTextSpan(Context context,
                                                     int subTextColor, int subTextSize,
                                                     String text, String... subTextArray) {
        if (context == null || text == null || subTextArray == null) {
            return null;
        }
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        int begin = 0;
        int end = 0;
        for (int i = 0; i < subTextArray.length; i++) {
            String subText = subTextArray[i];
            begin = text.indexOf(subText, end);
            end = begin + subText.length();
            //
            style.setSpan(new AbsoluteSizeSpan(subTextSize), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(subTextColor), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return style;
    }


    public static  void main(String[] args){

        // ###########更改TextView部分字体颜色###########
//
        /*int subTextColor = Color.parseColor("#af5050");
        String text = "100minutes10minutes";
        String[] subTextArray = {"100", "10"};*/
//
//        mTextView01.setText(TextColorSizeHelper.getTextSpan(this, subTextColor, text, subTextArray));

// ###########更改TextView部分字体颜色大小背景###########
//
        int subTextColor = Color.parseColor("#af5050");
        int subTextBgColor = Color.parseColor("#ffffff");
        int radius = 10;
        String text = "100minutes10minutes";
        String[] subTextArray = {"100", "10"};
//
//        mTextView02.setText(TextColorSizeHelper.getTextSpan(this, subTextColor, subTextBgColor, radius, text, subTextArray));


// ###########更改TextView部分字体颜色和大小############
//
      /*  int subTextColor = Color.parseColor("#af5050");
        int subTextSize = 180;
        String text = "100minutes10minutes";
        String[] subTextArray = {"100", "10"};*/
//        mTextView03.setText(TextColorSizeHelper.getTextSpan(this, subTextColor, subTextSize, text, subTextArray));

    }

}
