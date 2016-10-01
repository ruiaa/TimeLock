package com.ruiaa.timelock.main.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by ruiaa on 2016/9/30.
 */

public class AppInfo {
    private String packageName;
    private String appName;
    private Drawable icon;
    private int appType;
    private boolean isInstall;

    public AppInfo(String packageName,String appName,Drawable icon,int appType,boolean isInstall){

        this.packageName=packageName;
        this.appName=appName;
        this.icon=icon;
        this.appType=appType;
        this.isInstall=isInstall;

    }

    private AppInfo(){

    }

    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public int getAppType() {
        return appType;
    }

    public boolean isInstall() {
        return isInstall;
    }
}
