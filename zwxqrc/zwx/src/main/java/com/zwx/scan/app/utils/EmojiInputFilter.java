package com.zwx.scan.app.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : lizhilong
 * time   : 2019/03/19
 * desc   :  处理表情
 * version: 1.0
 **/
public class EmojiInputFilter implements InputFilter {

    Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        Matcher matcher=  pattern.matcher(charSequence);
        if(!matcher.find()){
            return null;
        }else{
//                Toast.makeText(MyApplication.context, "非法字符！", Toast.LENGTH_SHORT).show();
            return "";
        }

    }

}
