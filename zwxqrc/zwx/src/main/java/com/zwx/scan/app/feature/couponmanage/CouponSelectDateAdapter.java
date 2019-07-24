package com.zwx.scan.app.feature.couponmanage;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwx.library.pickerview.picker.builder.TimePickerBuilder;
import com.zwx.library.pickerview.picker.listener.CustomListener;
import com.zwx.library.pickerview.picker.listener.OnTimeSelectListener;
import com.zwx.library.pickerview.picker.view.TimePickerView;
import com.zwx.library.utils.LogUtils;
import com.zwx.library.utils.TimeUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.data.bean.UnavailableDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lizhilong
 * time   : 2019/03/01
 * desc   :
 * version: 1.0
 **/
public class CouponSelectDateAdapter extends BaseAdapter {

    private Boolean isStartDate = true;
    private List<UnavailableDate> mData = new ArrayList<>();
    private int index = -1;
    private  Context mContext;

    private  CouponNewActivity activity;
    protected TimePickerView pvStartTime,pvEndTime;
    public CouponSelectDateAdapter(Context context) {
        mContext = context;

    }


    public void setData(List<UnavailableDate> data) {
        mData = data;
        this.notifyDataSetChanged();
    }

    public void addItem() {
        final UnavailableDate timeBean = new UnavailableDate();
        mData.add(0, timeBean);
        this.notifyDataSetChanged();
    }

    public void deletItem(int pos) {
        LogUtils.e(pos + "删除的position");
        mData.remove(pos);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_coupon_new_date_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }


        activity = (CouponNewActivity)mContext;

        //使listview中的edittext获取焦点
      /*  etMac.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // 在TOUCH的UP事件中，要保存当前的行下标，因为弹出软键盘后，整个画面会被重画
                // 在getView方法的最后，要根据index和当前的行下标手动为EditText设置焦点
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = i;
                }
                return false;
            }
        });*/
/*
        etMac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtils.e(i + " onTextChanged-----" + s.toString());
                //如果该edittext有默认内容，还要在if那里进行过滤
                if (index >= 0 && s.length() > 0 && index == i) {
                    mData.get(index).setMac(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.e(" mac " + s.toString());

            }
        });*/


        if (index != -1 && index == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
//            etMac.requestFocus();
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.e(position+""+holder.tv_start_time.getText());
            }
        });
        holder.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e(" 删除设备 position = " + position);
                deletItem(position);
            }
        });
       /* ((CouponNewActivity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initStartDatePicker(holder.tv_start_time,holder.tv_end_time);

                initEndDatePicker(holder.tv_start_time,holder.tv_end_time);
            }
        });*/
        int posit = position;
        holder.ll_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvStartTime.show();
            }
        });

        holder.ll_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvEndTime.show();
            }
        });

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvStartTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                holder.tv_start_time.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")).replace("-","."));

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .isCyclic(true)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartTime.returnData();
                                String startDate  = holder.tv_start_time.getText().toString().trim();
                                String endDate = holder.tv_end_time.getText().toString().trim();

                                if(!startDate.isEmpty() && !endDate.isEmpty()){
                                    Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                    Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                    if(start.after(end)){  //start 大于end时 返回true; 反之 小于等于返回false
                                        holder.tv_start_time.setText("");
                                        ToastUtils.showToast("开始时间不可大于结束时间！");
                                    }
                                }
                                pvStartTime.dismiss();


                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartTime.dismiss();
                            }
                        });

                    }

                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
//时间选择器 ，自定义布局
        pvEndTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                holder.tv_end_time.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")).replace("-","."));

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .isCyclic(true)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvEndTime.returnData();
                                String startDate  = holder.tv_start_time.getText().toString().trim();
                                String endDate = holder.tv_end_time.getText().toString().trim();

                                if(!startDate.isEmpty() && !endDate.isEmpty()){
                                    Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                    Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                    if(end.before(start)){  //start 大于end时 返回true; 反之 小于等于返回false
                                        ToastUtils.showToast("结束时间不可小于开始时间！");
                                        holder.tv_end_time.setText("");
                                    }
                                }

                                pvEndTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvEndTime.dismiss();
                            }
                        });

                    }

                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_start_time)
        TextView tv_start_time;
        @BindView(R.id.tv_end_time)
        TextView tv_end_time;
//        @BindView(R.id.tv_end_time)
//        ImageView tv_end_time;
        @BindView(R.id.ll_delete)
        LinearLayout ll_delete;
        @BindView(R.id.ll_start)
        LinearLayout ll_start;
        @BindView(R.id.ll_end)
        LinearLayout ll_end;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    protected void initStartDatePicker(final TextView tvStartTime,final TextView tvEndTime) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvStartTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvStartTime.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")).replace("-","."));

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .isCyclic(true)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartTime.returnData();
                                String startDate  = tvStartTime.getText().toString().trim();
                                String endDate = tvEndTime.getText().toString().trim();

                                if(!startDate.isEmpty() && !endDate.isEmpty()){
                                    Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                    Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                    if(start.after(end)){  //start 大于end时 返回true; 反之 小于等于返回false
                                        tvStartTime.setText("");
                                        ToastUtils.showToast("开始时间不可大于结束时间！");
                                    }
                                }
                                pvStartTime.dismiss();


                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartTime.dismiss();
                            }
                        });

                    }

                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    protected void initEndDatePicker(final TextView tvStartTime,final TextView tvEndTime) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 28);
        //时间选择器 ，自定义布局
        pvEndTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                tvEndTime.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")).replace("-","."));

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .isCyclic(true)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final ImageView tvSubmit = (ImageView) v.findViewById(R.id.iv_submit);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvEndTime.returnData();
                                String startDate  = tvStartTime.getText().toString().trim();
                                String endDate = tvEndTime.getText().toString().trim();

                                if(!startDate.isEmpty() && !endDate.isEmpty()){
                                    Date start = TimeUtils.string2Date(startDate.replace(".","-"));
                                    Date end = TimeUtils.string2Date(endDate.replace(".","-"));

                                    if(end.before(start)){  //start 大于end时 返回true; 反之 小于等于返回false
                                        ToastUtils.showToast("结束时间不可小于开始时间！");
                                        tvEndTime.setText("");
                                    }
                                }

                                pvEndTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvEndTime.dismiss();
                            }
                        });

                    }

                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }




}
