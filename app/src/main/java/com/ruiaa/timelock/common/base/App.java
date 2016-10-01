package com.ruiaa.timelock.common.base;

import android.app.Application;
import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;
import com.ruiaa.timelock.common.bind.BaseCS;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ruiaa on 2016/9/29.
 */

public class App extends Application{

    private static Context context;
    public static BaseCS cs=new BaseCS();

    @Override
    public void onCreate() {

        //初始化组件
        LeakCanary.install(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();


        //
        context=getApplicationContext();
    }


    public static Context getAppContext(){
        return context;
    }

}
