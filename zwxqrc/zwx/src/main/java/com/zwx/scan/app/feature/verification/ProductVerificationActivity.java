package com.zwx.scan.app.feature.verification;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwx.library.utils.ActivityUtils;
import com.zwx.library.utils.SPUtils;
import com.zwx.library.utils.StringUtils;
import com.zwx.library.utils.ToastUtils;
import com.zwx.scan.app.R;
import com.zwx.scan.app.base.BaseActivity;
import com.zwx.scan.app.feature.AppContext;
import com.zwx.scan.app.feature.modulemore.ModuleMoreListActivity;
import com.zwx.scan.app.feature.shop.ShopOrderActivity;
import com.zwx.scan.app.feature.shop.ShopOrderDetailActivity;
import com.zwx.scan.app.feature.staffmanage.StaffEditActivity;
import com.zwx.scan.app.feature.verificationrecord.VerificationRecordActivity;
import com.zwx.scan.app.widget.qrcode.BarcodeType;
import com.zwx.scan.app.widget.qrcode.QRCodeView;
import com.zwx.scan.app.widget.qrcode.ZXingView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * author : lizhilong
 * time   : 2019/04/26
 * desc   :  商品核销
 * version: 1.0
 **/
public class ProductVerificationActivity extends BaseActivity<VerificationContract.Presenter>  implements VerificationContract.View,QRCodeView.Delegate,View.OnClickListener {

    private static final String TAG = VerificationActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    @BindView(R.id.iv_back)
    protected ImageView ivBack;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.iv_right)
    protected ImageView ivRight;


    @BindView(R.id.zxingview)
    protected ZXingView mZXingView;


    @Inject
    VerificationContract.Presenter presenter;


    public MediaPlayer mediaPlayer=new MediaPlayer();
    AlertDialog.Builder builder  = null;
    AlertDialog dialog = null;


    SensorManager sm = null;
    Sensor ligthSensor = null;
    private String title;

    protected boolean qrcFlag = true;

    //是否从订单管理 列表跳转还是订单详情跳转
    protected String intentOrderListAndDetailFlag  = "YES";


    //    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_verification;
    }

    @Override
    protected void initView() {

        ivRight.setVisibility(View.INVISIBLE);
        title = getIntent().getStringExtra("title");
        if(title != null &&!"".equals(title)){
            tvTitle.setText(title);
        }else {
            tvTitle.setText("商品核销");
        }

        mZXingView.setDelegate(this);

        DaggerVerificationComponent.builder()
                .applicationComponent(AppContext.getApplicationComponent())
                .verificationModule(new VerificationModule(this))
                .build()
                .inject(this);


    }

    @Override
    protected void initData() {
        mZXingView.setType(BarcodeType.ALL, null); // 识别所有类型的码
//        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别  2018-12-17注释
        //增加闪光灯
//        setSensorManager();

        intentOrderListAndDetailFlag = getIntent().getStringExtra("intentOrderListAndDetailFlag");

    }



    private void setSensorManager(){
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(new MySensorListener(), ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // 自动打开摄像头灯光 ，传感器监听

    public class MySensorListener implements SensorEventListener {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }


        //获取光线的强度
        public void onSensorChanged(SensorEvent event) {

            float lux = event.values[0];//获取光线强度


            int retval = Float.compare(lux, (float) 10.0);
            if(retval<10){//光线强度>10.0
                if(mZXingView != null){
                    mZXingView.closeFlashlight(); // 关闭闪光灯
                }

            }
            else {
                if(mZXingView != null) {
                    mZXingView.openFlashlight(); // 打开闪光灯
                }
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别


    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();

        if (mediaPlayer.isPlaying()){
            mediaPlayer.reset();
        }
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();

        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }






  /*  public void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }*/

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
//        vibrate();
//        mZXingView.startSpotDelay(3000); // 延迟0.5秒后开始识别

        mZXingView.stopSpot();
        if(StringUtils.isEmpty(result)){
            ToastUtils.showToastCenter("扫码失败！");
            mZXingView.startSpotDelay(3000);
            return;
        }
        String type = getParamByUrl(result,"type");
        String storeId = getParamByUrl(result,"storeId");

        String curStoreId = SPUtils.getInstance().getString("storeId");
        String memberId = getParamByUrl(result,"memberId");

        String epoch = getParamByUrl(result,"epoch");
        String compMemberTypeId = getParamByUrl(result,"compMemberTypeId");


        //商品核销
        if("3".equals(type)){
            String orderCouponId = getParamByUrl(result,"id");
            String compMemId = getParamByUrl(result,"compMemId");
            presenter.productVerification(ProductVerificationActivity.this,curStoreId,memberId,compMemId,orderCouponId,compMemberTypeId);

        }else {
            Intent intent = null;
            String postCode =result;
            if("YES1".equals(intentOrderListAndDetailFlag)){  //订单列表跳转进入该核销页面
                intent = new Intent(ProductVerificationActivity.this,ShopOrderActivity.class);
                intent.putExtra("postCode",postCode);
                setResult(2222,intent);
                finish();
            }else if("YES2".equals(intentOrderListAndDetailFlag)){
                intent = new Intent(ProductVerificationActivity.this,ShopOrderDetailActivity.class);
                intent.putExtra("postCode",postCode);
                setResult(2222,intent);
                finish();
            }else{
                ToastUtils.showToastCenter("二维码不识别！");
            }


        }

        /*
        String postCode  = getParamByUrl(result,"postCode");
        if(result!= null && result.contains("staff.do?id=")){


        }*/

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错！");
    }

    @OnClick({R.id.iv_back,R.id.zxingview})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:

//                ActivityUtils.startActivity(ModuleMoreListActivity.class,R.anim.slide_in_left, R.anim.slide_out_right);
                ActivityUtils.finishActivity(ProductVerificationActivity .class,R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.zxingview:

                mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
                mZXingView.setType(BarcodeType.ALL, null); // 识别所有类型的码
                mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
        }
    }


    @Override
    public void onBackPressed() {

        ActivityUtils.finishActivity(ProductVerificationActivity .class,R.anim.slide_in_left, R.anim.slide_out_right);

    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
//            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
            final String picturePath = data.getStringArrayListExtra("EXTRA_SELECTED_PHOTOS").get(0);
            // 本来就用到 QRCodeView 时可直接调 QRCodeView 的方法，走通用的回调
            mZXingView.decodeQRCode(picturePath);

        }
    }*/



    /**
     * 获取指定url中的某个参数
     * @param url
     * @param name
     * @return
     */
    public static String getParamByUrl(String url, String name) {
        url += "&";
        String pattern =  name + "=[a-zA-Z0-9,.]*(&{1})";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(url);
        if (m.find( )) {
            System.out.println(m.group(0));
            return m.group(0).split("=")[1].replace("&", "");
        } else {
            return null;
        }
    }





    private long epoch(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String t = df.format(d);
        long epoch = 0;
        try {
            epoch = df.parse(t).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return epoch;
    }

    /**http
     * 打开raw目录下的音乐mp3文件
     */
    public void openRawMusic(String type) {

        if("1".equals(type)){
            mediaPlayer = MediaPlayer.create(this, R.raw.verification);
        }else {
            mediaPlayer = MediaPlayer.create(this, R.raw.verification);
        }

        //用prepare方法，会报错误java.lang.IllegalStateExceptio
        //mediaPlayer1.prepare();
        mediaPlayer.start();
    }



    public void setFailDialog(String tip){



        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
        builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView textView = (TextView)rootView.findViewById(R.id.message);
        textView.setText(Html.fromHtml(tip));
        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               /* Intent intent = new Intent(VerificationActivity.this,StaffEditActivity.class);
                intent.putExtra("isMember","NO");
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);*/

            }
        });
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.GONE);

    }

    public void setSuccessDialog(String memberId){
        String storeName = SPUtils.getInstance().getString("storeName");


        String tip = "是否确认要将该会员添加到 "+'"'+"<font color=\'#0486FE\'>"+storeName+"</font>"+'"'+" 的员工中";

        View rootView = View.inflate(this, R.layout.dialog_confirm_cancel_util, null);
//        final Dialog dialog = DialogUIUtils.showCustomAlert(this, rootView, Gravity.CENTER, true, false).show();
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialog.setView(rootView, 0, 0, 0, 0);
        TextView message = (TextView)rootView.findViewById(R.id.message);
        message.setText(Html.fromHtml(tip));
        TextView tvTitle = (TextView)rootView.findViewById(R.id.title);
        tvTitle.setText("加入提示");

        rootView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(ProductVerificationActivity.this,StaffEditActivity.class);
                intent.putExtra("isMember","YES");
                intent.putExtra("memberId",memberId);
                ActivityUtils.startActivity(intent,R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });

        rootView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        rootView.findViewById(R.id.cancelBtn).setVisibility(View.VISIBLE);



    }
}
