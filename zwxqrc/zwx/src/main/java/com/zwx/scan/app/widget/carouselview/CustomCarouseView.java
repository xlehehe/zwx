package com.zwx.scan.app.widget.carouselview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zwx.scan.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author : lizhilong
 * time   : 2018/12/22
 * desc   :
 * version: 1.0
 **/
public class CustomCarouseView extends FrameLayout{


    private final int DEFAULT_GRAVITY = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

    private static final int DEFAULT_SLIDE_INTERVAL = 3500;
    private static final int DEFAULT_SLIDE_VELOCITY = 400;
    public static final int DEFAULT_INDICATOR_VISIBILITY = 0;


    private int mPageCount;
    private int slideInterval = DEFAULT_SLIDE_INTERVAL;
    private int mIndicatorGravity = DEFAULT_GRAVITY;
    private int indicatorMarginVertical;
    private int indicatorMarginHorizontal;
    private int pageTransformInterval = DEFAULT_SLIDE_VELOCITY;
    private int indicatorVisibility = DEFAULT_INDICATOR_VISIBILITY;

    public CarouselViewPager containerViewPager;
//    private CirclePageIndicator mIndicator;
    private ViewListener mViewListener = null;
    private ImageListener mImageListener = null;
    private ImageClickListener imageClickListener = null;

    private Timer swipeTimer;
    private SwipeTask swipeTask;

    private boolean autoPlay;
    private boolean disableAutoPlayOnUserInteraction;
    private boolean animateOnBoundary = true;

    private int previousState;

    private ViewPager.PageTransformer pageTransformer;

    //存放圆点的ImageView
    private List<ImageView>  mDotsIV = new ArrayList<>();
    LinearLayout mDotsLayout;

    Context mContext;
    public CustomCarouseView(Context context) {
        super(context);
        mContext = context;
    }

    public CustomCarouseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context, attrs, 0, 0);
    }

    public CustomCarouseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomCarouseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (isInEditMode()) {
            return;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.carousel_layout, this, true);
            containerViewPager = (CarouselViewPager) view.findViewById(R.id.containerViewPager);
//            mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
//             mDotsLayout = (LinearLayout) view.findViewById(R.id.dots);

            containerViewPager.addOnPageChangeListener(carouselOnPageChangeListener);


            //Retrieve styles attributes
           /* TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CarouselView, defStyleAttr, 0);
            try {
                indicatorMarginVertical = a.getDimensionPixelSize(R.styleable.CarouselView_indicatorMarginVertical, getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_vertical));
                indicatorMarginHorizontal = a.getDimensionPixelSize(R.styleable.CarouselView_indicatorMarginHorizontal, getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_horizontal));
                setPageTransformInterval(a.getInt(R.styleable.CarouselView_pageTransformInterval, DEFAULT_SLIDE_VELOCITY));
                setSlideInterval(a.getInt(R.styleable.CarouselView_slideInterval, DEFAULT_SLIDE_INTERVAL));
                setOrientation(a.getInt(R.styleable.CarouselView_indicatorOrientation, LinearLayout.HORIZONTAL));
                setIndicatorGravity(a.getInt(R.styleable.CarouselView_indicatorGravity, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL));
                setAutoPlay(a.getBoolean(R.styleable.CarouselView_autoPlay, true));
                setDisableAutoPlayOnUserInteraction(a.getBoolean(R.styleable.CarouselView_disableAutoPlayOnUserInteraction, false));
                setAnimateOnBoundary(a.getBoolean(R.styleable.CarouselView_animateOnBoundary, true));
                setPageTransformer(a.getInt(R.styleable.CarouselView_pageTransformer, CarouselViewPagerTransformer.DEFAULT));

                indicatorVisibility = a.getInt(R.styleable.CarouselView_indicatorVisibility, DEFAULT_INDICATOR_VISIBILITY);

                setIndicatorVisibility(indicatorVisibility);

                if (indicatorVisibility == View.VISIBLE) {
                    int fillColor = a.getColor(R.styleable.CarouselView_fillColor, 0);
                    if (fillColor != 0) {
                        setFillColor(fillColor);
                    }
                    int pageColor = a.getColor(R.styleable.CarouselView_pageColor, 0);
                    if (pageColor != 0) {
                        setPageColor(pageColor);
                    }
                    float radius = a.getDimensionPixelSize(R.styleable.CarouselView_radius, 0);
                    if (radius != 0) {
                        setRadius(radius);
                    }
                    setSnap(a.getBoolean(R.styleable.CarouselView_snap, getResources().getBoolean(R.bool.default_circle_indicator_snap)));
                    int strokeColor = a.getColor(R.styleable.CarouselView_strokeColor, 0);
                    if (strokeColor != 0) {
                        setStrokeColor(strokeColor);
                    }
                    float strokeWidth = a.getDimensionPixelSize(R.styleable.CarouselView_strokeWidth, 0);
                    if (strokeWidth != 0) {
                        setStrokeWidth(strokeWidth);
                    }
                }
            } finally {
                a.recycle();
            }*/
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetScrollTimer();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        playCarousel();
    }

    public int getSlideInterval() {
        return slideInterval;
    }

    /**
     * Set interval for one slide in milliseconds.
     *
     * @param slideInterval integer
     */
    public void setSlideInterval(int slideInterval) {
        this.slideInterval = slideInterval;

        if (null != containerViewPager) {
            playCarousel();
        }
    }

    /**
     * Set interval for one slide in milliseconds.
     *
     * @param slideInterval integer
     */
    public void reSetSlideInterval(int slideInterval) {
        setSlideInterval(slideInterval);

        if (null != containerViewPager) {
            playCarousel();
        }
    }

    /**
     * Sets speed at which page will slide from one to another in milliseconds
     *
     * @param pageTransformInterval integer
     */
    public void setPageTransformInterval(int pageTransformInterval) {
        if (pageTransformInterval > 0) {
            this.pageTransformInterval = pageTransformInterval;
        } else {
            this.pageTransformInterval = DEFAULT_SLIDE_VELOCITY;
        }

        containerViewPager.setTransitionVelocity(pageTransformInterval);
    }

    public ViewPager.PageTransformer getPageTransformer() {
        return pageTransformer;
    }

    /**
     * Sets page transition animation.
     *
     * @param pageTransformer Choose from zoom, flow, depth, slide_over .
     */
    public void setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;
        containerViewPager.setPageTransformer(true, pageTransformer);
    }

    /**
     * Sets page transition animation.
     *
     * @param transformer Pass {@link CarouselViewPagerTransformer#FLOW}, {@link CarouselViewPagerTransformer#ZOOM}, {@link CarouselViewPagerTransformer#DEPTH} or {@link CarouselViewPagerTransformer#SLIDE_OVER}
     * @attr
     */
    public void setPageTransformer(@CarouselViewPagerTransformer.Transformer int transformer) {
        setPageTransformer(new CarouselViewPagerTransformer(transformer));

    }

    /**
     * Sets whether to animate transition from last position to first or not.
     *
     * @param animateOnBoundary .
     */
    public void setAnimateOnBoundary(boolean animateOnBoundary) {
        this.animateOnBoundary = animateOnBoundary;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    private void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public boolean isDisableAutoPlayOnUserInteraction() {
        return disableAutoPlayOnUserInteraction;
    }

    private void setDisableAutoPlayOnUserInteraction(boolean disableAutoPlayOnUserInteraction) {
        this.disableAutoPlayOnUserInteraction = disableAutoPlayOnUserInteraction;
    }

    private void setData() {
        CarouselPagerAdapter carouselPagerAdapter = new CarouselPagerAdapter(getContext());
        containerViewPager.setAdapter(carouselPagerAdapter);
        if(getPageCount() > 1) {
//            mIndicator.setViewPager(containerViewPager);
//            mIndicator.requestLayout();
//            mIndicator.invalidate();

           /* for (int i = 0; i < 5; i++) {
                ImageView dotIV = new ImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = params.rightMargin = 4;
                mDotsLayout.addView(dotIV, params);
                mDotsIV.add(dotIV);
            }*/

            containerViewPager.setOffscreenPageLimit(getPageCount());
            playCarousel();
        }
    }

    private void stopScrollTimer() {
        if (null != swipeTimer) {
            swipeTimer.cancel();
        }

        if (null != swipeTask) {
            swipeTask.cancel();
        }
    }


    private void resetScrollTimer() {
        stopScrollTimer();

        swipeTask = new SwipeTask();
        swipeTimer = new Timer();
    }


    /**
     * Starts auto scrolling if
     */
    public void playCarousel() {

        resetScrollTimer();

        if (autoPlay && slideInterval > 0 && containerViewPager.getAdapter() != null && containerViewPager.getAdapter().getCount() > 1) {

            swipeTimer.schedule(swipeTask, slideInterval, slideInterval);
        }
    }

    /**
     * Pause auto scrolling unless user interacts provided autoPlay is enabled.
     */
    public void pauseCarousel() {

        resetScrollTimer();
    }

    /**
     * Stops auto scrolling.
     */
    public void stopCarousel() {

        this.autoPlay = false;
    }


    private class CarouselPagerAdapter extends PagerAdapter {
        private Context mContext;

        public CarouselPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

            Object objectToReturn;

            //Either let user set image to ImageView
            if (mImageListener != null) {

                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));  //setting image position
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                objectToReturn = imageView;
                mImageListener.setImageForPosition(position, imageView);

                collection.addView(imageView);

                //Or let user add his own ViewGroup
            } else if (mViewListener != null) {

                View view = mViewListener.setViewForPosition(position);

                if (null != view) {
                    objectToReturn = view;
                    collection.addView(view);
                } else {
                    throw new RuntimeException("View can not be null for position " + position);
                }

            } else {
                throw new RuntimeException("View must set " + ImageListener.class.getSimpleName() + " or " + ViewListener.class.getSimpleName() + ".");
            }

            return objectToReturn;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return getPageCount();
        }
    }

    ViewPager.OnPageChangeListener carouselOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //Programmatic scroll

        }

        @Override
        public void onPageSelected(int position) {

            /*for (int i = 0; i < mDotsIV.size(); i++) {
                if (i == position) {
                    mDotsIV.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    mDotsIV.get(i).setImageResource(R.drawable.dot_blur);
                }
            }*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            //User initiated scroll

            if (previousState == ViewPager.SCROLL_STATE_DRAGGING
                    && state == ViewPager.SCROLL_STATE_SETTLING) {

                if (disableAutoPlayOnUserInteraction) {
                    pauseCarousel();
                } else {
                    playCarousel();
                }

            } else if (previousState == ViewPager.SCROLL_STATE_SETTLING
                    && state == ViewPager.SCROLL_STATE_IDLE) {
            }

            previousState = state;

        }
    };

    private class SwipeTask extends TimerTask {
        public void run() {
            containerViewPager.post(new Runnable() {
                public void run() {

                    int nextPage = (containerViewPager.getCurrentItem() + 1) % getPageCount();

                    containerViewPager.setCurrentItem(nextPage, 0 != nextPage || animateOnBoundary);
                }
            });
        }
    }

    public void setImageListener(ImageListener mImageListener) {
        this.mImageListener = mImageListener;
    }

    public void setViewListener(ViewListener mViewListener) {
        this.mViewListener = mViewListener;
    }

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
        containerViewPager.setImageClickListener(imageClickListener);
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setPageCount(int mPageCount) {
        this.mPageCount = mPageCount;

        setData();
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        containerViewPager.addOnPageChangeListener(listener);
    }

    public void clearOnPageChangeListeners() {
        containerViewPager.clearOnPageChangeListeners();
    }

    public void setCurrentItem(int item) {
        containerViewPager.setCurrentItem(item);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        containerViewPager.setCurrentItem(item, smoothScroll);
    }

    public int getCurrentItem() {
        return containerViewPager.getCurrentItem();
    }

    public int getIndicatorMarginVertical() {
        return indicatorMarginVertical;
    }

    public void setIndicatorMarginVertical(int _indicatorMarginVertical) {
        indicatorMarginVertical = _indicatorMarginVertical;
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.topMargin = indicatorMarginVertical;
        params.bottomMargin = indicatorMarginVertical;
    }

    public int getIndicatorMarginHorizontal() {
        return indicatorMarginHorizontal;
    }

    public CarouselViewPager getContainerViewPager() {
        return containerViewPager;
    }

    public void setIndicatorMarginHorizontal(int _indicatorMarginHorizontal) {
        indicatorMarginHorizontal = _indicatorMarginHorizontal;
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.leftMargin = indicatorMarginHorizontal;
        params.rightMargin = indicatorMarginHorizontal;
    }

    public int getIndicatorGravity() {
        return mIndicatorGravity;
    }
    public void setIndicatorGravity(int gravity) {
        mIndicatorGravity = gravity;
    }
   /* public void setIndicatorGravity(int gravity) {
        mIndicatorGravity = gravity;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = mIndicatorGravity;
        params.setMargins(indicatorMarginHorizontal, indicatorMarginVertical, indicatorMarginHorizontal, indicatorMarginVertical);
        mIndicator.setLayoutParams(params);
    }

    public void setIndicatorVisibility(int visibility) {
        mIndicator.setVisibility(visibility);
    }

    public int getOrientation() {
        return mIndicator.getOrientation();
    }

    public int getFillColor() {
        return mIndicator.getFillColor();
    }

    public int getStrokeColor() {
        return mIndicator.getStrokeColor();
    }

    public void setSnap(boolean snap) {
        mIndicator.setSnap(snap);
    }

    public void setRadius(float radius) {
        mIndicator.setRadius(radius);
    }

    public float getStrokeWidth() {
        return mIndicator.getStrokeWidth();
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }

    public Drawable getIndicatorBackground() {
        return mIndicator.getBackground();
    }

    public void setFillColor(int fillColor) {
        mIndicator.setFillColor(fillColor);
    }

    public int getPageColor() {
        return mIndicator.getPageColor();
    }

    public void setOrientation(int orientation) {
        mIndicator.setOrientation(orientation);
    }

    public boolean isSnap() {
        return mIndicator.isSnap();
    }

    public void setStrokeColor(int strokeColor) {
        mIndicator.setStrokeColor(strokeColor);
    }

    public float getRadius() {
        return mIndicator.getRadius();
    }

    public void setPageColor(int pageColor) {
        mIndicator.setPageColor(pageColor);
    }

    public void setStrokeWidth(float strokeWidth) {
        mIndicator.setStrokeWidth(strokeWidth);
        int padding = (int) strokeWidth;
        mIndicator.setPadding(padding, padding, padding, padding);
    }*/
}
