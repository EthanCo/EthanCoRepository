package com.ethanco.wifitest;

import java.io.Closeable;

/**
 * Created by Zhk on 2016/3/12.
 */
public class CloseUtils {
    private CloseUtils() {
    }

    /**
     * 关闭Closeable对象
     *
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
