package com.ruiaa.timelock.monitor.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.component.RxBus;
import com.ruiaa.timelock.common.consts.ConfigCode;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.common.utils.SPUtils;
import com.ruiaa.timelock.monitor.MonitorService;
import com.ruiaa.timelock.monitor.event.DateChangeEvent;
import com.ruiaa.timelock.monitor.event.ScreenChangeEvent;

/**
 * Created by ruiaa on 2016/9/30.
 */
public class StateReceiver extends BroadcastReceiver {

    public StateReceiver() {
    }

    public static void registerStateReceiver(){
        StateReceiver stateReceiver = new StateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        App.getAppContext().registerReceiver(stateReceiver, intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //亮屏
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            RxBus.getDefault().post(new ScreenChangeEvent(true));
            LogUtil.i("亮屏");
        }

        //暗屏
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            RxBus.getDefault().post(new ScreenChangeEvent(false));
            LogUtil.i("暗屏");
        }

        //日期变化
        if (Intent.ACTION_DATE_CHANGED.equals(intent.getAction())) {
            RxBus.getDefault().post(new DateChangeEvent());
            LogUtil.i("日期变化");
        }

        //开机
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            //开启控制后台
            if ((new SPUtils(App.getAppContext(),MonitorService.SPFileName)).getBoolean(ConfigCode.BOOT_COMPLETED, true)) {
                Intent startService = new Intent(App.getAppContext(), MonitorService.class);
                App.getAppContext().startService(startService);
            }
        }
    }
}
