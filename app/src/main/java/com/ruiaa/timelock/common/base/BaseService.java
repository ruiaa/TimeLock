package com.ruiaa.timelock.common.base;

import android.app.Service;
import android.content.Intent;

/**
 * Created by ruiaa on 2016/9/29.
 */

public abstract class BaseService extends Service {


    public BaseService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
