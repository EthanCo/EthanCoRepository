package com.ethanco.lockscreenlocksample;

import android.content.Context;
import android.os.PowerManager;

import static android.content.Context.POWER_SERVICE;

/**
 * 保持常亮工具类
 * 需要权限 <uses-permission android:name="android.permission.WAKE_LOCK"/>
 *
 * @author EthanCo
 * @since 2016/11/24
 */

public class WakeLockUtil {
    private static final String HOLD_TAG = "wake_lock_hold_log";
    private static boolean isKeepPowerOn = false;
    private static PowerManager.WakeLock wakeLock;

    /**
     * 保持屏幕常亮
     */
    public static void acquire(Context context) {
        wakeLock = ((PowerManager) context.getSystemService(POWER_SERVICE))
                .newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                        | PowerManager.ON_AFTER_RELEASE, HOLD_TAG);
        wakeLock.acquire();
        isKeepPowerOn = true;
    }

    /**
     * 取消屏幕常亮
     */
    public static void release() {
        if (!isKeepPowerOn) {
            return;
        }
        if (wakeLock == null) {
            return;
        }
        wakeLock.release();
    }
}
