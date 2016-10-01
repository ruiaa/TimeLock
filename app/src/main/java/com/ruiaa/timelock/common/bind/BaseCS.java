package com.ruiaa.timelock.common.bind;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.ruiaa.timelock.common.utils.LogUtil;


/**
 * Created by ruiaa on 2016/9/29.
 */

public class BaseCS {

    private Messenger replyMessenger=null;
    private MsgHandler msgHandler=null;

    private Messenger messenger=new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MsgCode.MSG_BIND:{
                    replyMessenger=msg.replyTo;
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

    public void setMsgHandler(MsgHandler msgHandler){
        this.msgHandler=msgHandler;
    }

    public boolean sendMSG(Message msg){
        if (replyMessenger!=null){
            try {
                replyMessenger.send(msg);
            }catch (RemoteException e){
                LogUtil.w("sendMSG#"+"发送消息失败");
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public boolean unBind(){
        replyMessenger=null;
        return sendMSG(Message.obtain(null,MsgCode.MSG_UNBIND));
    }

    public IBinder getIBinder(){
        return messenger.getBinder();
    }

}
