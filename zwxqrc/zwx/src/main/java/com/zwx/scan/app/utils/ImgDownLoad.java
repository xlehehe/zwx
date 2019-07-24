package com.zwx.scan.app.utils;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.zwx.scan.app.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
/**
 * author : lizhilong
 * time   : 2019/01/14
 * desc   :
 * version: 1.0
 **/
public class ImgDownLoad {

    private static String filePath;
    private static Bitmap mBitmap;
    private static String mFileName="sshs";
    private static String mSaveMessage;
    private final static String TAG = "ImageActivity";
    private static Context context;

    private static ProgressDialog mSaveDialog = null;

    public static void donwloadImg(Context contexts,String filePaths){
        context = contexts;
        filePath = filePaths;
        mSaveDialog = ProgressDialog.show(context, "保存二维码", "二维码图片正在保存中，请稍等...", true);

        new Thread(saveFileRunnable).start();
    }

    private static Runnable saveFileRunnable = new Runnable(){
        @Override
        public void run() {
            try {
//                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));

                if (!TextUtils.isEmpty(filePath)) {                 //网络图片
                    //对资源链接
                    URL url=new URL(filePath);
                    //打开输入流
                    InputStream inputStream=url.openStream();
                    //对网上资源进行下载转换位图图片
                    mBitmap= BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }

                Bitmap    bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_button_normal );   //本地的图片，根据自己情况
//                saveFile(mBitmap, mFileName);
                saveFile(bmp);
                mSaveMessage = "图片保存成功！";
            } catch (IOException e) {
                mSaveMessage = "图片保存失败！";
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }
    };
    private static Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSaveDialog.dismiss();
            Log.d(TAG, mSaveMessage);
            Toast.makeText(context, mSaveMessage, Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * Get image from newwork
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public static InputStream getImageStream(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }
    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(Environment.getExternalStorageDirectory().getPath());
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        fileName = UUID.randomUUID().toString()+".jpg";
        File myCaptureFile = new File(Environment.getExternalStorageDirectory().getPath() +"/DCIM/Camera/"+ fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();

        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
    /**
     * 保存图片到图库
     * @param bmp
     */
    public static void saveFile( Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "sshs");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
    }
}
