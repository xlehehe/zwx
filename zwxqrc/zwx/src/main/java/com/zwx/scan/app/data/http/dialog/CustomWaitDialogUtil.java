package com.zwx.scan.app.data.http.dialog;

import android.content.Context;

import com.zwx.scan.app.R;


/**
 *  author : lizhilong
 *  time   : 2018/9/18
 *  desc   :
 *  version: 1.0
 **/
public class CustomWaitDialogUtil {
	public static WaitDialog waitDialog = null;
	public static Context context;

	/**
	 * 自定义用于等待的dialog,有动画和message提示
	 * 调用stopWaitDialog()方法来取消
	 * @param context
	 * @param message
	 * @param canceledOnTouchOutside
	 */
	public static void showWaitDialog(Context context,String message,boolean canceledOnTouchOutside){
		if(waitDialog != null && waitDialog.isShowing()) waitDialog.dismiss();
		waitDialog = new WaitDialog(context, R.style.MyDialog,message);
		waitDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		if(!waitDialog.isShowing()){
			waitDialog.show();
		}
	}
	/**
	 * 自定义用于等待的dialog,有动画无message
	 * 调用stopWaitDialog()方法来取消
	 * @param context
	 * @param canceledOnTouchOutside
	 */
	public static void showWaitDialog(Context context,boolean canceledOnTouchOutside){
		showWaitDialog(context, null, canceledOnTouchOutside);
	}

	/**
	 * 自定义用于等待的dialog,有动画和message提示
	 * 调用stopWaitDialog()方法来取消
	 * @param context
	 * @param messageId
	 * @param canceledOnTouchOutside
	 */
	public static void showWaitDialog(Context context,int messageId,boolean canceledOnTouchOutside){
		showWaitDialog(context, context.getResources().getString(messageId), canceledOnTouchOutside);
	}

	/**
	 * 取消等待dialog
	 */
	public static void stopWaitDialog(){
		if(waitDialog != null && waitDialog.isShowing()){
			waitDialog.dismiss();
		}
	}
}
