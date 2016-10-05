package com.ruiaa.timelock.main.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.bind.aidl.AIDLMainMonitor;
import com.ruiaa.timelock.common.bind.aidl.Config;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.monitor.MonitorService;

/**
 * Created by ruiaa on 2016/10/2.
 */

public class ClientToMonitor {

    private static ClientToMonitor instance = null;

    private AIDLMainMonitor binder =null;

    private ClientToMonitor(){
        Intent intent=new Intent(App.getAppContext(), MonitorService.class);
        ServiceConnection mConnection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                binder =AIDLMainMonitor.Stub.asInterface(service);
                LogUtil.i("ClientToMonitor  onServiceConnected--");
            }

            @Override
            public void onServiceDisconnected(ComponentName name)
            {
                binder =null;
                instance=null;
            }
        };
        App.getAppContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public static ClientToMonitor getInstance() {
        if(instance==null) {
            synchronized (ClientToMonitor.class) {
                if (instance == null) {
                    instance = new ClientToMonitor();
                }
            }
        }
        return instance;
    }

    public  Config getConfig(){
        try{
            while (binder==null){

            }
            return binder.getConfig();
        }catch (RemoteException e){
            LogUtil.e("getConfig--",e);
        }
        return null;
    }

    public  boolean saveConfig(Config config){
        try{
            while (binder==null){

            }
            binder.saveConfig(config);
            return true;
        }catch (RemoteException e){
            LogUtil.e("saveConfig--",e);
        }
        return false;
    }

}
