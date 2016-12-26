package cn.nbhope.threepartprotocollib.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by EthanCo on 2016/9/12.
 */
public class RunTimeUtil {
    //服务是否运行
    public static boolean isServiceRunning(Context context, String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> lists = am.getRunningServices(30);

        for (ActivityManager.RunningServiceInfo info : lists) {// 获取运行服务再启动
            if (info.service.getClassName().equals(serviceName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    // 进程是否运行
    public static boolean isProessRunning(Context context, String proessName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
            }
        }

        return isRunning;
    }

    //CPU个数
    public static int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Print exception
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }

    //获取进程名
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
