package com.zwx.scan.app.utils;

import android.text.InputFilter;
import android.text.Spanned;

import com.zwx.library.utils.ToastUtils;

/**
 * author : lizhilong
 * time   : 2019/02/17
 * desc   :
 * version: 1.0
 **/
public class MaxTextLengthFilter implements InputFilter {

    private int mMaxLength;

    public MaxTextLengthFilter(int max){
//        mMaxLength = max - 1;
        mMaxLength = max ;
    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart , int dend){
        int keep = mMaxLength - (dest.length() - (dend - dstart));
        if(keep < (end - start)){
            ToastUtils.showShort("最多输入"+mMaxLength+"个字");
        }
        if(keep <= 0){
            return "";
        }else if(keep >= end - start){
            return null;
        }else{
            return source.subSequence(start,start + keep);
        }
    }
}
