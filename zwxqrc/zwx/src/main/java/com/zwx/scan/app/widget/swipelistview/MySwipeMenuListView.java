package com.zwx.scan.app.widget.swipelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.zwx.library.utils.LogUtils;

/**
 *
 * @author lizhilong
 * @date 2017/08/23
 *   listview item 滑动菜单 自定义
 */
public class MySwipeMenuListView extends SwipeMenuListView {
    public MySwipeMenuListView(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context,onGestureListener);
    }

    public MySwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mGestureDetector = new GestureDetector(context,onGestureListener);
    }

    public MySwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context,onGestureListener);
    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                //setParentScrollAble(false);//当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview 停住不能滚动
//                LogUtil.w("MyLogUtil", "onInterceptTouchEvent down");
//            case MotionEvent.ACTION_MOVE:
//
//                LogUtil.w("MyLogUtil", "onInterceptTouchEvent move");
//                break;
//
//            case MotionEvent.ACTION_UP:
//                LogUtil.w("MyLogUtil", "onInterceptTouchEvent up");
//
//            case MotionEvent.ACTION_CANCEL:
//
//                LogUtil.w("MyLogUtil", "onInterceptTouchEvent cancel");
//                //setParentScrollAble(true);//当手指松开时，让父ScrollView重新拿到onTouch权限
//
//                break;
//            default:
//                break;
//
//        }
//
//        return super.onInterceptTouchEvent(ev) ;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b =  mGestureDetector.onTouchEvent(ev);
        LogUtils.w("MyLogUtil","-- "+ b+" --");
        return super.onTouchEvent(ev);
    }

    private GestureDetector mGestureDetector;
    OnTouchListener mGestureListener;

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener(){
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            LogUtil.w("MyLogUtil","onFling");
//            float x = e2.getX() - e1.getX();
//            float y = e2.getY() - e1.getY();
//            if (Math.abs(y) >= Math.abs(x)){
//                setParentScrollAble(false);
//                return true;
//            }
//            //当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview 停住不能滚动
//            setParentScrollAble(true);
//            return false;
//        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            LogUtils.w("MyLogUtil", "onScroll");
            if (distanceY != 0 && distanceX != 0) {
            }
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            //当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview 停住不能滚动
            setParentScrollAble(false);
            return false;
        }
    };

    /**
     * 是否把滚动事件交给父scrollview
     *
     * @param flag
     */
    private void setParentScrollAble(boolean flag) {
        //这里的parentScrollView就是listview外面的那个scrollview
        LogUtils.w("MyLogUtil", "setParentScrollAble -- " + flag);
        getParent().requestDisallowInterceptTouchEvent(!flag);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
