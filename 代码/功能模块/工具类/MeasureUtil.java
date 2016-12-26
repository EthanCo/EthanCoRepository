/**
 * 
 */
package com.example.customview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * ��ȡ�ֻ���Ļ��С
 * @author 
 *
 */
public class MeasureUtil {
	
	/**
	 * ��
	 * @return
	 */
	public static int getWidth(Context context){
		WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}
	
	/**
	 * ��
	 * @return
	 */
	public static int getHeight(Context context){
		WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

}
