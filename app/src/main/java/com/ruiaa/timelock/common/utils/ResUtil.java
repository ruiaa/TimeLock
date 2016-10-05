package com.ruiaa.timelock.common.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.ruiaa.timelock.common.base.App;

/**
 * Created by ruiaa on 2016/10/2.
 */

public class ResUtil {

    private static Resources resources= App.getAppContext().getResources();

    public static String getString(int stringFromR) {
        return resources.getString(stringFromR);
    }

    public static int getColor(int colorFromR) {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColor(colorFromR, null);
        } else {
            return resources.getColor(colorFromR);
        }
    }

    public static int getColor(int colorFromR, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColor(colorFromR, theme);
        } else {
            return resources.getColor(colorFromR);
        }
    }

    public static Drawable getDrawable(int drawableFromR) {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(drawableFromR, null);
        } else {
            return resources.getDrawable(drawableFromR);
        }
    }

    public static Drawable getDrawable(int drawableFromR, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(drawableFromR, theme);
        } else {
            return resources.getDrawable(drawableFromR);
        }
    }

    public static float getDimen(int dimenFromR) {
        return resources.getDimension(dimenFromR);
    }


}
