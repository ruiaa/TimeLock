package com.ruiaa.timelock.common.utils;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.ruiaa.timelock.R;

/**
 * Created by ruiaa on 2016/10/4.
 */

public class AppUtils {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void getUsagePermission(Context context){
        if (Build.VERSION.SDK_INT>=21) {
            if (!havePermission(context)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.no_permission);
                builder.setPositiveButton(R.string.get_permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        context.startActivity(intent);
                    }
                });
                builder.create();
                builder.show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean havePermission(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
