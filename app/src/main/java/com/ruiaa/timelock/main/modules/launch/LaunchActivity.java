package com.ruiaa.timelock.main.modules.launch;

import android.content.Intent;
import android.os.Bundle;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.base.BaseActivity;
import com.ruiaa.timelock.common.component.RxBus;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.common.utils.SPUtils;
import com.ruiaa.timelock.main.event.MainDataPreparedEvent;
import com.ruiaa.timelock.main.event.PrePareDataEvent;
import com.ruiaa.timelock.main.model.MainDataCache;
import com.ruiaa.timelock.main.modules.lock.ui.LockActivity;
import com.ruiaa.timelock.monitor.MonitorService;

import java.util.concurrent.TimeUnit;

public class LaunchActivity extends BaseActivity {

    long startTime;
    long waitTime=1000*1;
    private static final long WAIT_FOR_FIRST=3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        startTime=System.currentTimeMillis();
        startService(new Intent(this, MonitorService.class));

        MainDataCache.setUpdateListener();
        SPUtils spUtils=new SPUtils(this,"main");
        if (spUtils.getBoolean("no_first",false)){
            RxBus.getDefault().post(new PrePareDataEvent());
        }else {
            spUtils.putBoolean("no_first",true);
            RxBus.getDefault().postDelay(new PrePareDataEvent(),WAIT_FOR_FIRST);
        }

        RxBus.getDefault().toObservable(MainDataPreparedEvent.class)
                .delay(startTime+waitTime-System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .subscribe(mainDataPreparedEvent->{
                    startActivity(new Intent(this, LockActivity.class));
                    LaunchActivity.this.finish();
                });
        LogUtil.i("onCreate--");
    }

    @Override
    protected void onDestroy() {
        LogUtil.i("onDestroy--");
        super.onDestroy();
    }
}
