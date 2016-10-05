package com.ruiaa.timelock.main.model;

import com.ruiaa.timelock.common.bind.aidl.Config;
import com.ruiaa.timelock.common.component.RxBus;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.main.entity.AppInfo;
import com.ruiaa.timelock.main.event.MainDataPreparedEvent;
import com.ruiaa.timelock.main.event.PrePareDataEvent;
import com.ruiaa.timelock.main.model.cp.DataMainContentResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.schedulers.Schedulers;

/**
 * Created by ruiaa on 2016/10/2.
 */

public class MainDataCache {

    private static MainDataCache instance = null;

    private List<AppInfo> appInfos;
    private Map<String,AppInfo> appInfoMap;
    private Config config=null;

    private MainDataCache(){
        LogUtil.i("start--");
        appInfos=(new AppInfoProvider(DataMainContentResolver.getInstance())).getAllApp();
        config=ClientToMonitor.getInstance().getConfig();

        RxBus.getDefault().post(new MainDataPreparedEvent());

        appInfoMap=new HashMap<>();
        for (AppInfo app:appInfos) {
            appInfoMap.put(app.getPackageName(),app);
        }
        LogUtil.i("ready--");
    }

    public static MainDataCache getInstance() {
        if(instance==null) {
            synchronized (MainDataCache.class) {
                if (instance == null) {
                    instance = new MainDataCache();
                }
            }
        }
        return instance;
    }

    public static void setUpdateListener(){
        RxBus.getDefault().toObservable(PrePareDataEvent.class)
                .observeOn(Schedulers.io())
                .subscribe(prePareDataEvent -> {
                    instance=new MainDataCache();
                });
    }

    public List<AppInfo> getAppInfos() {
        return appInfos;
    }

    public AppInfo getAppInfo(String packageName){
        return appInfoMap.get(packageName);
    }

    public Config getConfig(){
        return config;
    }

    public boolean setConfig(Config config){
        this.config=config;
        return ClientToMonitor.getInstance().saveConfig(config);
    }

}
