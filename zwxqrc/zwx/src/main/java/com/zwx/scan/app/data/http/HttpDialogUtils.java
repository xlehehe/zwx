package com.zwx.scan.app.data.http;

import android.content.Context;
import android.text.TextUtils;

import com.zwx.scan.app.data.http.dialog.CustomWaitDialogUtil;

/**
 *
 * HttpDialog
 */
public class HttpDialogUtils {

	public static void showDialog(Context context, boolean canceledOnTouchOutside, String messageText) {
		if (context != null) {
			if (TextUtils.isEmpty(messageText)) {
				CustomWaitDialogUtil.showWaitDialog(context, canceledOnTouchOutside);
			} else {
				CustomWaitDialogUtil.showWaitDialog(context, messageText, canceledOnTouchOutside);
			}
		}
	}

	public static void dismissDialog() {
		CustomWaitDialogUtil.stopWaitDialog();
	}

}
