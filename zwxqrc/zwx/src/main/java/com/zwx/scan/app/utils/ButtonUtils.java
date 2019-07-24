package com.zwx.scan.app.utils;

import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.ToastUtils;

/**
 * author : lizhilong
 * time   : 2019/01/05
 * desc   :
 * version: 1.0
 **/
public class ButtonUtils {

    private static long lastClickTime = 0;
    private static long DIFF = 3000;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            LogUtils.e("isFastDoubleClick", "短时间内按钮多次触发");
//            ToastUtils.showShort("短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }


    private final static int SPACE_TIME = 1000;//2次点击的间隔时间，单位ms
    private static long lastClickTime2;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick;
        if (currentTime - lastClickTime2 > SPACE_TIME) {
            isClick = false;
        } else {
            isClick = true;
        }
        lastClickTime2 = currentTime;
        return isClick;
    }
}
