package com.ruiaa.timelock.common.utils;


import android.util.Log;

import com.ruiaa.timelock.BuildConfig;
/**
 * Created by ruiaa on 2016/9/29.
 */

public class LogUtil {
    public static final String USER_NAME="chenji" ;
    public static final boolean USE_LOG= BuildConfig.DEBUG;

    public static void d(String msg){
        if (USE_LOG){
            Log.d(getClassName(),msg);
        }
    }

    public static void i(String msg){
        if (USE_LOG){
            Log.i(getClassName(),msg);
        }
    }

    public static void w(String msg){
        if (USE_LOG){
            Log.w(getClassName(),msg);
        }
    }

    public static void e(String msg){
        if (USE_LOG){
            Log.e(getClassName(),msg);
        }
    }

    public static void w(String msg ,Exception e){
        if (USE_LOG){
            Log.w(getClassName(),msg);
            e.printStackTrace();
        }
    }

    public static void e(String msg ,Exception e){
        if (USE_LOG){
            Log.e(getClassName(),msg);
            e.printStackTrace();
        }
    }

    /**
     * @return 当前的类名(simpleName)
     */
    private static String getClassName() {

        String result;
        StackTraceElement thisMethodStack = Thread.currentThread().getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1);

        int i = result.indexOf("$");// 剔除匿名内部类名

        return i == -1 ? result : result.substring(0, i);
    }

    /**
     * 打印 Log 行数位置
     */
    private static String log(String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[5];
        String className = targetElement.getClassName();
        className = className.substring(className.lastIndexOf('.') + 1) + ".java";
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0) lineNumber = 0;
        return "(" + className + ":" + lineNumber + ") " + message;
    }
}
