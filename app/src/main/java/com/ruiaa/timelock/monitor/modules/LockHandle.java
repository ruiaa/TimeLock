package com.ruiaa.timelock.monitor.modules;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.consts.AppCode;
import com.ruiaa.timelock.common.consts.SqlField;
import com.ruiaa.timelock.common.utils.DataConvert;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.monitor.MonitorService;
import com.ruiaa.timelock.monitor.model.DataCom;
import com.ruiaa.timelock.monitor.model.sqlite.DataComSqlite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ruiaa on 2016/10/1.
 */

public class LockHandle {

    private DataCom dataCom;

    private List<String> currentUseLockList;
    private List<String> currentForbiddenLockList;
    private List<String> currentTimeOver;

    public static LockHandle getLockHandle(){
        return new LockHandle();
    }

    private LockHandle(){
        dataCom= DataComSqlite.getInstance();

        currentUseLockList = new ArrayList<>();
        currentForbiddenLockList = new ArrayList<>();
        currentTimeOver = new ArrayList<>();
    }

    public boolean judgeLock(@NonNull String appPackage){


        //禁用锁拦截
        if (currentForbiddenLockList.contains(appPackage)) {
            intercept(appPackage,AppCode.LOCK_TYPE_FORBIDDEN);

            LogUtil.i("禁用"+appPackage);
            return true;
        }

        //超时锁拦截
        if (currentTimeOver.contains(appPackage)) {
            intercept(appPackage,AppCode.LOCK_TYPE_OVER_TIME);

            LogUtil.i("超时"+appPackage);
            return true;
        }

        //锁定锁拦截
        if (currentUseLockList.isEmpty()) {
            //没锁定锁 不拦截
            return true;
        } else if (currentUseLockList.contains(appPackage)) {
            //锁定该app 不拦截
            return true;
        } else {
            //锁定其他app 拦截
            intercept(appPackage,AppCode.LOCK_TYPE_USE);

            LogUtil.i("锁定"+appPackage);
            return true;
        }
    }
    //打开拦截界面
    private void intercept(@NonNull String frontPkg, int lockType){
        /*ActivityManager activityManager = (ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(appPackage);*/
        Intent intent = new Intent(App.getAppContext(), MonitorService.INTERCEPT_ACTIVITY);
        intent.putExtra(SqlField.PACKAGE, frontPkg);
        intent.putExtra(SqlField.LOCK_TYPE, lockType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getAppContext().startActivity(intent);
    }

    public void updateLockList(){
        currentForbiddenLockList=getCurrentForbiddenPkg();
        currentUseLockList=getCurrentUsePkg();
        currentTimeOver=getOverTimeAppPkg();
    }

    private List<String> getOverTimeAppPkg() {

        Cursor cursorTimeOver=null;
        String[] strings = {SqlField.PACKAGE};
        ArrayList<String> over = new ArrayList<>();
        String overTimePkg;

        try {
            cursorTimeOver = dataCom.query(SqlField.TABLE_APP_INFO, strings,
                    SqlField.LIMIT_STATE + " = " + AppCode.LIMIT_STATE_OPEN + " and " +
                            SqlField.getCurrentLimitWeek() + " < " + MonitorService.TodayDate,
                    null, null, null, null);


            if (cursorTimeOver != null && cursorTimeOver.moveToFirst()) {
                do {
                    overTimePkg = cursorTimeOver.getString(cursorTimeOver.getColumnIndex(SqlField.PACKAGE));
                    if (over.contains(overTimePkg)) {
                        //已经包含
                    } else {
                        over.add(overTimePkg);
                    }
                } while (cursorTimeOver.moveToNext());
            }
        }catch (Exception e){
            LogUtil.e("getOverTimeAppPkg#",e);
        }finally {
            if (cursorTimeOver!=null){
                cursorTimeOver.close();
            }
            return over;
        }

    }

    private List<String> getCurrentUsePkg() {

        Cursor cursor=null;
        String[] strings = {SqlField.PACKAGE};
        ArrayList<String> list= new ArrayList<>();
        String pkg;
        int currentTime= DataConvert.time(Calendar.getInstance());
        try {
            cursor = dataCom.query(SqlField.TABLE_LOCK, strings,
                    SqlField.LOCK_STATE+ " = " + AppCode.LIMIT_STATE_OPEN + " and " +
                            SqlField.LOCK_TYPE + "=" + AppCode.LOCK_TYPE_USE + " and " +
                            SqlField.LOCK_START_TIME + " < " + currentTime + " and " +
                            SqlField.LOCK_FINISH_TIME + " > " + currentTime + " and " +
                            "(" + SqlField.getCurrentLockRepeatWeek()+ "=1 " + " or " + SqlField.LOCK_REPEAT + "=0 )",
                    null, null, null, SqlField.LOCK_ORDER + " desc");


            if (cursor != null && cursor.moveToFirst()) {
                do {
                    pkg = cursor.getString(cursor.getColumnIndex(SqlField.PACKAGE));
                    if (list.contains(pkg)) {
                        //已经包含
                    } else {
                        list.add(pkg);
                    }
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            LogUtil.e("getCurrentUsePkg#",e);
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            return list;
        }

    }

    private List<String> getCurrentForbiddenPkg() {

        Cursor cursor=null;
        String[] strings = {SqlField.PACKAGE};
        ArrayList<String> list= new ArrayList<>();
        String pkg;
        int currentTime=DataConvert.time(Calendar.getInstance());
        try {
            cursor = dataCom.query(SqlField.TABLE_LOCK, strings,
                    SqlField.LOCK_STATE+ " = " + AppCode.LIMIT_STATE_OPEN + " and " +
                            SqlField.LOCK_TYPE + "=" + AppCode.LOCK_TYPE_FORBIDDEN + " and " +
                            SqlField.LOCK_START_TIME + " < " + currentTime + " and " +
                            SqlField.LOCK_FINISH_TIME + " > " + currentTime + " and " +
                            "(" + SqlField.getCurrentLockRepeatWeek()+ "=1 " + " or " + SqlField.LOCK_REPEAT + "=0 )",
                    null, null, null, SqlField.LOCK_ORDER + " desc");


            if (cursor != null && cursor.moveToFirst()) {
                do {
                    pkg = cursor.getString(cursor.getColumnIndex(SqlField.PACKAGE));
                    if (list.contains(pkg)) {
                        //已经包含
                    } else {
                        list.add(pkg);
                    }
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            LogUtil.e("getCurrentUsePkg#",e);
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            return list;
        }

    }

}
