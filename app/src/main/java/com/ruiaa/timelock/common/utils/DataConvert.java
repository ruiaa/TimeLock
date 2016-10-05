package com.ruiaa.timelock.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.base.App;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * Created by ruiaa on 2016/9/30.
 */

public class DataConvert {

    public static Drawable drawable_bytes(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return new BitmapDrawable(App.getAppContext().getResources(), bitmap);
    }

    public static byte[] drawable_bytes(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ((BitmapDrawable) drawable).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /*
     *2016/9/30 -->20160930=2016*10000+9*100+30
     */
    public static int date(Calendar calendar) {
        return calendar.get(Calendar.YEAR) * 10000
                + calendar.get(Calendar.MONTH) * 100
                + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String date(int date) {
        return String.valueOf(date / 10000) + "/"
                + String.valueOf((date % 10000) / 100) + "/"
                + String.valueOf(date % 100);
    }

    /*
     *20:07:23  -->200723=20*10000+7*100+23
     */
    public static int time(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60
                + calendar.get(Calendar.MINUTE) * 60
                + calendar.get(Calendar.SECOND);
    }

    public static int time(int hour, int minute, int second) {
        return hour * 60 * 60 + minute * 60 + second;
    }

    public static String time(int time) {
        return String.valueOf(time / 3600) + ":"
                + String.valueOf((time % 3600) / 60);
    }

    public static String timeToMinute(int time) {
        StringBuilder builder = new StringBuilder();
        int i = time / 3600;
        if (i != 0) {
            builder.append(i);
            builder.append(ResUtil.getString(R.string.hour));
        }
        i = (time % 3600) / 60;
        builder.append(i);
        builder.append(ResUtil.getString(R.string.minute));
        return builder.toString();
    }
}
