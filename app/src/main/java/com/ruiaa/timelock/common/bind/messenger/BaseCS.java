package com.ruiaa.timelock.common.bind.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.utils.LogUtil;


/**
 * Created by ruiaa on 2016/9/29.
 *
 * !!! 跨进程  单线
 *
 * client   baseCS.bindServer(intent)->
 *
 * server      service.onBind(){return baseCS.getIBinder;}->
 *
 * c/s              c/s.sendMsg ----------> s/c.setMsgHandler(msgHandler)
 */

public class BaseCS {

    private Messenger replyMessenger=null;

    private Messenger thisMessenger=null;

    public BaseCS(){
        thisMessenger =new Messenger(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MsgCode.MSG_BIND:{
                        replyMessenger=msg.replyTo;
                        LogUtil.w("BaseCS--MSG_BIND");
                        break;
                    }
                    case MsgCode.MSG_UNBIND:{
                        replyMessenger=null;
                        break;
                    }
                    default:{
                        if (msgHandler!=null){
                            msgHandler.handleMSG(msg);
                        }
                        break;
                    }
                }
                super.handleMessage(msg);
            }
        });
    }

    private MsgHandler msgHandler=null;

    public void setMsgHandler(MsgHandler msgHandler){
        this.msgHandler=msgHandler;
    }

    public boolean sendMsg(Message msg){
        if (replyMessenger!=null){
            try {
                replyMessenger.send(msg);
            }catch (RemoteException e){
                LogUtil.w("sendMsg#"+"发送消息失败");
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean unBind(){
        replyMessenger=null;
        return sendMsg(Message.obtain(null,MsgCode.MSG_UNBIND));
    }

    public IBinder getIBinder(){
        return thisMessenger.getBinder();
    }

    public void bindServer(Intent intent){
        ServiceConnection mConnection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                replyMessenger = new Messenger(service);
                Message message=Message.obtain(null,MsgCode.MSG_UNBIND);
                message.replyTo= thisMessenger;
                sendMsg(message);
            }

            @Override
            public void onServiceDisconnected(ComponentName name)
            {
                replyMessenger=null;
            }
        };
        App.getAppContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

}
