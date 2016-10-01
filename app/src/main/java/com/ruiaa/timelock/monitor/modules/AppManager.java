package com.ruiaa.timelock.monitor.modules;

import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.consts.AppCode;
import com.ruiaa.timelock.common.consts.SqlField;
import com.ruiaa.timelock.common.utils.DataConvert;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.monitor.model.DataCom;
import com.ruiaa.timelock.monitor.model.sqlite.DataComSqlite;

import java.util.ArrayList;
import java.util.List;

import static com.ruiaa.timelock.common.consts.AppCode.APP_TYPE_USELESS;

/**
 * Created by ruiaa on 2016/10/1.
 */

public class AppManager {
    private DataCom dataCom;

    private AppManager() {
        dataCom = DataComSqlite.getInstance();
    }

    public static AppManager getAppManager() {
        return new AppManager();
    }

    public void updateApp() {
        //手机现有已安装app
        ArrayList<String> newInstallApp = new ArrayList<String>();
        //数据库 旧的 已安装app
        ArrayList<String> oldInstallApp = new ArrayList<String>();
        //数据库 已卸载app
        ArrayList<String> oldUnInstallApp = new ArrayList<String>();

        Cursor cursorInstall = null;
        Cursor cursorUnInstall = null;

        //获取 数据库 已安装app 已卸载app
        String[] strings = {SqlField.PACKAGE};
        try {

            cursorInstall = dataCom.query(SqlField.TABLE_APP_INFO, strings, SqlField.INSTALL + "="+AppCode.IS_INSTALL_TRUE, null, null, null, null);
            if (cursorInstall != null && cursorInstall.moveToFirst()) {
                do {
                    oldInstallApp.add(cursorInstall.getString(cursorInstall.getColumnIndex(SqlField.PACKAGE)));
                } while (cursorInstall.moveToNext());
            }


            cursorUnInstall = dataCom.query(SqlField.TABLE_APP_INFO, strings, SqlField.INSTALL + "="+AppCode.IS_INSTALL_FALSE, null, null, null, null);
            if (cursorUnInstall != null && cursorUnInstall.moveToFirst()) {
                do {
                    oldUnInstallApp.add(cursorUnInstall.getString(cursorUnInstall.getColumnIndex(SqlField.PACKAGE)));
                } while (cursorUnInstall.moveToNext());
            }

        } catch (Exception e) {
            LogUtil.e("updateApp#", e);
        } finally {
            if (cursorInstall != null) {
                cursorInstall.close();
            }
            if (cursorUnInstall != null) {
                cursorUnInstall.close();
            }
        }


        //数据对比

        PackageManager packageManager = App.getAppContext().getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        String packageName = "";
        byte[] appIcon;
        ContentValues contentValues;

        dataCom.beginTransaction();


        //新安装，相同，卸载(删除锁)，重安装
        try {

            //将手机的app与数据库中的对比
            for (PackageInfo packageInfo : packageInfos) {

                packageName = packageInfo.packageName;

                //手机现有app
                newInstallApp.add(packageName);

                if (oldInstallApp.contains(packageName)) {
                    //对应
                } else if (oldUnInstallApp.contains(packageName)) {
                    //现有的标为已卸载了
                    contentValues = new ContentValues();
                    contentValues.put(SqlField.INSTALL, 1);
                    dataCom.update(SqlField.TABLE_APP_INFO, contentValues,
                            SqlField.PACKAGE + "='" + packageName + "'", null);

                } else {
                    //现有的不在数据库
                    insertApp(dataCom,packageManager,packageInfo);
                }

            }


            //将数据库中已安装的与手机上的对比
            for (String oldInApp : oldInstallApp) {
                if (newInstallApp.contains(oldInApp)) {
                    //数据库中已安装的在手机上
                } else {
                    //数据库已安装的不在手机上
                    contentValues = new ContentValues();
                    contentValues.put(SqlField.INSTALL, 0);
                    dataCom.update(SqlField.TABLE_APP_INFO, contentValues,
                            SqlField.PACKAGE + "='" + oldInApp + "'", null);
                }
            }


            dataCom.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.e("updateApp#", e);
        } finally {
            dataCom.endTransaction();
        }
    }

    public void firstUpdateApp() {
        PackageManager packageManager = App.getAppContext().getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        dataCom.beginTransaction();
        try {
            for(PackageInfo packageInfo:packageInfos){
                insertApp(dataCom,packageManager,packageInfo);
            }
            dataCom.setTransactionSuccessful();
        }catch (Exception e){
            LogUtil.e("firstUpdateApp#",e);
        }finally {
            dataCom.endTransaction();
        }

    }

    private void insertApp(DataCom d,PackageManager packageManager,PackageInfo packageInfo){
        int appType = getAppType(packageInfo);

        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlField.PACKAGE, packageInfo.packageName);
        contentValues.put(SqlField.APP_NAME, packageInfo.applicationInfo.loadLabel(packageManager).toString());
        contentValues.put(SqlField.APP_TYPE, appType);
        if (appType == AppCode.APP_TYPE_USELESS) {
            ////没有桌面图标的不储存图标
        } else {
            byte[] appIcon = DataConvert.drawable_bytes(packageInfo.applicationInfo.loadIcon(packageManager));
            contentValues.put(SqlField.ICON, appIcon);
        }

        d.insert(SqlField.TABLE_APP_INFO, null, contentValues);
    }

    private int getAppType(PackageInfo pi) {
        if (App.getAppContext().getPackageManager().getLaunchIntentForPackage(pi.packageName) == null) {
            return APP_TYPE_USELESS;
        } else if ((ApplicationInfo.FLAG_SYSTEM & pi.applicationInfo.flags) == ApplicationInfo.FLAG_SYSTEM) {
            return AppCode.APP_TYPE_SYSTEM;
        } else {
            return AppCode.APP_TYPE_USER;
        }
    }
}
