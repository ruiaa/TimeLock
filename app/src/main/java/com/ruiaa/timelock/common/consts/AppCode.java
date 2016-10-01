package com.ruiaa.timelock.common.consts;

/**
 * Created by ruiaa on 2016/9/30.
 */

public class AppCode {

    public static final String THIS_PACKSGE="com.ruiaa.timelock";

    //安装
    public static final int IS_INSTALL_TRUE=1;
    public static final int IS_INSTALL_FALSE=0;

    //app类型
    public static final int APP_TYPE_USER=1 ;
    public static final int APP_TYPE_SYSTEM=2 ;
    public static final int APP_TYPE_USELESS=0; //没有前台

    //锁类型 1禁用 2锁定
    public static final int LOCK_TYPE_FORBIDDEN=1;
    public static final int LOCK_TYPE_USE=2;
    public static final int LOCK_TYPE_OVER_TIME=3;

    //锁状态
    public static final int LOCK_STATE_OPEN=1;
    public static final int LOCK_STATE_CLOSE=0;

    //时间限制状态
    public static final int LIMIT_STATE_OPEN=1;
    public static final int LIMIT_STATE_CLOSE=0;
}
