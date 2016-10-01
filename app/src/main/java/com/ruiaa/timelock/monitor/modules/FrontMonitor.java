package com.ruiaa.timelock.monitor.modules;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.monitor.MonitorService;

import java.util.List;


/**
 * Created by ruiaa on 2016/10/1.
 */

public class FrontMonitor {

    UsageStatsManager usageStatsManager ;


    ActivityManager activityManager ;


    public static FrontMonitor getFrontMonitor() {
        return new FrontMonitor();
    }

    private FrontMonitor() {

        if (Build.VERSION.SDK_INT>=21){
            usageStatsManager = (UsageStatsManager) App.getAppContext().getSystemService("usagestats");
        }else {
            activityManager = (ActivityManager) App.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        }

    }

    public String getFrontAppPkg(){
        if (Build.VERSION.SDK_INT>=21){
            return getFrontApi21();
        }else {
            return getFrontApi21Less();
        }
    }


    @TargetApi(21)
    private String getFrontApi21(){

        UsageStats recentStats;

        long thisTime = System.currentTimeMillis();
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, thisTime - (long) (1000 * MonitorService.Monitor_Interval), thisTime);

        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return null;
        } else {
            recentStats = null;
            for (UsageStats usageStats : queryUsageStats) {
                if (recentStats == null || recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                    recentStats = usageStats;
                }
            }
            return recentStats.getPackageName();
        }
    }

    private String getFrontApi21Less(){

       return activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();

    }
}
