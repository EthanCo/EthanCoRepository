package com.bocweb.cunyou.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * @Description
 * Created by EthanCo on 2016/4/5.
 */
public class PackageUtil {
    public static final String PACKAGE_NAME_QQ = "com.tencent.mobileqq"; //QQ包名
    public static final String PACKAGE_NAME_WECHAT = "com.tencent.mm"; //微信包名
    public static final String PACKAGE_NAME_SINA = "com.sina.weibo"; //微博包名

    /**
     * 根据包名检查app是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
        return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
