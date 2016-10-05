package com.ruiaa.timelock.common.utils;

import android.widget.Toast;

import com.ruiaa.timelock.common.base.App;


/**
 * Created by chenji on 2016/9/29.
 */

public class ToastUtil {
    public static void showShort(String msg) {
        Toast.makeText(App.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(App.getAppContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showShort(int msg) {
        Toast.makeText(App.getAppContext(), ResUtil.getString(msg), Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int msg) {
        Toast.makeText(App.getAppContext(), ResUtil.getString(msg), Toast.LENGTH_LONG).show();
    }
}
