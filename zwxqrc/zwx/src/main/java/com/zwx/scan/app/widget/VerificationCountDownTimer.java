package com.zwx.scan.app.widget;

import android.os.CountDownTimer;

/**
 * author : lizhilong
 * time   : 2019/05/15
 * desc   : 获取验证码倒计时
 * version: 1.0
 **/
public class VerificationCountDownTimer extends CountDownTimer {
    public static long curMillis =0;
    public static boolean FLAG_FIRST_IN =true;
    /**
     * 类中的构造函数
     * @param millisInFuture   倒计的总时间
     * @param countDownInterval  倒计的时间间隔
     */
    public VerificationCountDownTimer(long millisInFuture,long countDownInterval){
        super(millisInFuture, countDownInterval);
    }
    /**
     * 当前任务每完成一次倒计时间隔时间时回调
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {

    }
    /**
     * 当前任务完成的时候回调
     */
    @Override
    public void onFinish() {

    }

    public void timerStart(boolean onClick){

        if(onClick) {
            VerificationCountDownTimer.curMillis= System.currentTimeMillis();
        }
        VerificationCountDownTimer.FLAG_FIRST_IN=false;
        this.start();
    }
}
