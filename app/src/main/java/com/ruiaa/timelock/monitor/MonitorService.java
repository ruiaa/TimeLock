package com.ruiaa.timelock.monitor;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.base.BaseService;
import com.ruiaa.timelock.common.bind.BaseCS;
import com.ruiaa.timelock.common.component.RxBus;
import com.ruiaa.timelock.common.consts.ConfigCode;
import com.ruiaa.timelock.common.utils.SPUtils;
import com.ruiaa.timelock.main.modules.MainActivity;
import com.ruiaa.timelock.monitor.event.DateChangeEvent;
import com.ruiaa.timelock.monitor.event.ScreenChangeEvent;
import com.ruiaa.timelock.monitor.modules.AppManager;
import com.ruiaa.timelock.monitor.modules.DateManager;
import com.ruiaa.timelock.monitor.modules.FrontMonitor;
import com.ruiaa.timelock.monitor.modules.LockHandle;
import com.ruiaa.timelock.monitor.modules.StateReceiver;
import com.ruiaa.timelock.monitor.modules.UsageRecord;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorService extends BaseService {

    public static final String SPFileName = "MonitorConfig";
    public static final Class INTERCEPT_ACTIVITY = MainActivity.class;

    public static String TodayDate;

    //本地储存 Config
    public static float Monitor_Interval = 0.1F;                //监控间隔 0.1 ~~ 10 秒

    public static int Intercept_Interval = 60;               //拦截间隔  60~~600 秒
    public static String LauncherPackage;                    //桌面
    public static boolean AllLockIsOpen = true;               //打开锁

    //运行时状态
    public static boolean ScreenIsLight = true;              //屏幕亮


    BaseCS server;
    SPUtils spUtils;
    AppManager appManager;
    DateManager dateManager;
    FrontMonitor frontMonitor;
    LockHandle lockHandle;
    UsageRecord usageRecord;

    public MonitorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(() -> {
            prepare();
            getModules();
            front();
            update();
            handleBusMsg();
            handleCSMsg();;
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent startService = new Intent(App.getAppContext(), MonitorService.class);
        startService(startService);
    }

    private void prepare() {
        spUtils = new SPUtils(this, SPFileName);

        TodayDate = spUtils.getString(ConfigCode.DATE_TODAY, "0");

    }

    private void getModules() {

        appManager = AppManager.getAppManager();

        dateManager = DateManager.getDateManager();

        if (TodayDate.equals("0")) {
            dateManager.updateDateFieldAndDeleteLock();
            appManager.firstUpdateApp();
        } else {
            dateManager.updateDateFieldAndDeleteLock();
            appManager.updateApp();
        }

        frontMonitor = FrontMonitor.getFrontMonitor();

        usageRecord = UsageRecord.getUsageRecord();

        lockHandle = LockHandle.getLockHandle();
        lockHandle.updateLockList();


        //注册监听屏幕亮暗 日期变化
        StateReceiver.registerStateReceiver();
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= 20) {
            if (powerManager.isInteractive()) {
                ScreenIsLight = true;
            } else {
                ScreenIsLight = false;
            }
        } else {
            if (powerManager.isScreenOn()) {
                ScreenIsLight = true;
            } else {
                ScreenIsLight = false;
            }
        }
    }

    private ScheduledExecutorService scheduleFront = null;
    private ScheduledExecutorService scheduleUpdate = null;

    private void front() {
        if (scheduleFront != null) {
            scheduleFront.shutdown();
            scheduleFront = null;
        }
        scheduleFront = Executors.newSingleThreadScheduledExecutor();
        Runnable frontR = frontR = () -> {
            if (ScreenIsLight) {
                String frontPkg = frontMonitor.getFrontAppPkg();
                usageRecord.record(frontPkg);
                if (!frontPkg.equals("com.ruiaa.timelock")) {
                    lockHandle.judgeLock(frontPkg);
                }
            }
        };
        scheduleFront.scheduleAtFixedRate(frontR, 0, (long) (Monitor_Interval * 1000), TimeUnit.MILLISECONDS);
    }

    private void update() {
        if (scheduleUpdate != null) {
            scheduleUpdate.shutdown();
            scheduleUpdate = null;
        }
        scheduleUpdate = Executors.newSingleThreadScheduledExecutor();
        Runnable updateR = updateR = () -> {
            appManager.updateApp();
            usageRecord.saveAll();
        };
        scheduleUpdate.scheduleAtFixedRate(updateR, 0, 10L, TimeUnit.MINUTES);
    }

    private void handleBusMsg() {

        RxBus.getDefault().toObservable(ScreenChangeEvent.class)
                .subscribe(screenChangeEvent -> {
                    ScreenIsLight = screenChangeEvent.isScreenIsLight();
                    if (ScreenIsLight) {
                        lockHandle.updateLockList();
                    }
                });

        RxBus.getDefault().toObservable(DateChangeEvent.class)
                .subscribe(dateChangeEvent -> {
                    dateManager.updateDateFieldAndDeleteLock();
                });

    }

    private void handleCSMsg(){
        App.cs.setMsgHandler((msg)->{
            switch (msg.what){

            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return App.cs.getIBinder();
    }
}
