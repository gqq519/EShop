package com.feicuiedu.eshop.base.utils;

import android.util.Log;

/**
 * 日志工具类.
 */
public class LogUtils {

    // 日志全局控制开关
    private static boolean isDebug = true;
    private static final String TAG = "EShop";

    private LogUtils() {
        // 此类不可被实例化
        throw new UnsupportedOperationException("LogUtils can't be instantiated.");
    }

    public static void verbose(String msg, Object... objects) {
        if (isDebug) {
            msg = String.format(msg, objects);
            Log.v(TAG, msg);
        }
    }

    public static void info(String msg, Object... objects) {
        if (isDebug) {
            msg = String.format(msg, objects);
            Log.i(TAG, msg);
        }
    }

    public static void debug(String msg, Object... objects) {
        if (isDebug) {
            msg = String.format(msg, objects);
            Log.d(TAG, msg);
        }
    }

    public static void error(String msg, Throwable t, Object... objects) {
        if (isDebug) {
            msg = String.format(msg, objects);
            Log.e(TAG, msg, t);
        }
    }

}
