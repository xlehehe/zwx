package com.zwx.instalment.app.feature.main;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zwx.instalment.app.R;
import com.zwx.instalment.app.base.BaseFragment;
import com.zwx.instalment.app.data.bean.ClassBean;
import com.zwx.instalment.app.feature.welcome.ViewPagerAdatper;
import com.zwx.instalment.app.widget.MyGridView;
import com.zwx.instalment.app.widget.viewpager.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

//    @BindView(R.id.tv_title)
//    protected TextView tv_title;


    @BindView(R.id.viewPager)
    protected CustomViewPager viewPager;

    @BindView(R.id.dots)
    protected LinearLayout mDotsLayout;

    @BindView(R.id.grid_view)
    protected MyGridView grid_view;

    private List<View> mViewList = new ArrayList<>();
    private int[]images={R.drawable.home_banner,R.drawable.home_banner,R.drawable.home_banner,R.drawable.home_banner};
    private List<ImageView> mDotsIV = new ArrayList<>();
    private String param;
    private HomeMenuListAdapter adapter = null;


    private int pageMargin = 0 ;
    private int oldPosition = 0 ; //上一个位置
    private int currentIndex = 0 ; //当前位置
    private static long time = 3000 ; //自动播放时间
    private static boolean autoPlay = true ; //是否自动播放
    ViewPagerAdatper mAdapter = null;

    private List<ClassBean> classList = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(String param) {
        HomeFragment fragment = new HomeFragment();
        fragment.param = param;
        return fragment;
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initView() {

        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]);

            mViewList.add(imageView);
        }

        setDotLayout(mViewList);

        mAdapter = new ViewPagerAdatper(mViewList);
        viewPager.setAdapter(mAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mDotsIV.size(); i++) {
                    if (i == position) {
                        mDotsIV.get(i).setImageResource(R.drawable.home_carousel_dot_active);

                    } else {
                        mDotsIV.get(i).setImageResource(R.drawable.home_carousel_dot);
                    }
                }
                //此处currentIndex是从1开始的，要注意
              /*  if (position==0){
                    currentIndex = mAdapter.getCount()-2 ;
                }else if (position == mAdapter.getCount()-1){
                    currentIndex = 0 ;
                }else {
                    currentIndex = position ;
                }*/
                currentIndex = position ;
                if(currentIndex == images.length){
                    currentIndex = 0 ;
                }

                oldPosition = currentIndex ;
                Log.e("wenzhihao","position=="+position+",currentIndex="+currentIndex);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

                if(i==ViewPager.SCROLL_STATE_IDLE){

                    viewPager.setCurrentItem(currentIndex,false);
                    play();
                }else if(i==ViewPager.SCROLL_STATE_DRAGGING){
                    cancel();
                }
            }
        });

        play();
    }

    @Override
    protected void initData() {


        ClassBean classBean = new ClassBean();
        classBean.setKey("1");
        classBean.setValue("邀请码");
        classBean.setDrawable(R.drawable.home_icon_menu1);
        classList.add(classBean);

        classBean = new ClassBean();
        classBean.setKey("2");
        classBean.setValue("现金领取");
        classBean.setDrawable(R.drawable.home_icon_menu2);
        classList.add(classBean);

        classBean = new ClassBean();
        classBean.setKey("3");
        classBean.setValue("新人专享");
        classBean.setDrawable(R.drawable.home_icon_menu3);
        classList.add(classBean);

        classBean = new ClassBean();
        classBean.setKey("4");
        classBean.setValue("抽奖活动");
        classBean.setDrawable(R.drawable.home_icon_menu4);
        classList.add(classBean);

        classBean = new ClassBean();
        classBean.setKey("5");
        classBean.setValue("信用报告");
        classBean.setDrawable(R.drawable.home_icon_menu5);
        classList.add(classBean);

        classBean = new ClassBean();
        classBean.setKey("6");
        classBean.setValue("我的钱包");
        classBean.setDrawable(R.drawable.home_icon_menu6);
        classList.add(classBean);

        classBean = new ClassBean();
        classBean.setKey("7");
        classBean.setValue("买呗账单");
        classBean.setDrawable(R.drawable.home_icon_menu7);
        classList.add(classBean);

        classBean = new ClassBean();
        classBean.setKey("8");
        classBean.setValue("备用金账单");
        classBean.setDrawable(R.drawable.home_icon_menu8);
        classList.add(classBean);
        adapter = new HomeMenuListAdapter(getActivity(),classList);
        grid_view.setAdapter(adapter);


    }

    protected void setDotLayout(List<View> dataList){
        for (int i = 0; i < dataList.size(); i++) {
            ImageView dotIV = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = params.rightMargin = 4;
            mDotsLayout.addView(dotIV, params);
            if (i == 0) {
                dotIV.setImageResource(R.drawable.home_carousel_dot_active);
            } else {
                dotIV.setImageResource(R.drawable.home_carousel_dot);
            }
            mDotsIV.add(dotIV);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        cancel();
    }

    /**
     * 取消播放
     */
    public void cancel(){
        handler.removeCallbacks(runnable);
    }
    /**
     * 播放，根据autoplay
     */
    public void play(){
        if (autoPlay){
            handler.postDelayed(runnable,time);
        }else {
            handler.removeCallbacks(runnable);
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            play();
        }
    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentIndex +=1;

            if(currentIndex == images.length){
                currentIndex = 0;
            }
            Log.e("wenzhihao",",currentIndex="+currentIndex);
            viewPager.setCurrentItem(currentIndex);
        }
    };

    /**
     * 设置是否自动播放
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        if (!autoPlay){
            handler.removeCallbacks(runnable);
        }
    }
}
