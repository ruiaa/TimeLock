package com.ruiaa.timelock.common.base;

import android.app.Application;
import android.content.Context;

import com.ruiaa.timelock.common.utils.LogUtil;

/**
 * Created by ruiaa on 2016/9/29.
 */

public class App extends Application{

    private static Context context;


    @Override
    public void onCreate() {

        //初始化组件
        //LeakCanary.install(this);
        //BlockCanary.install(this, new AppBlockCanaryContext()).start();

        //
        context=getApplicationContext();

        LogUtil.i("onCreate--");
    }


    public static Context getAppContext(){
        return context;
    }


}
