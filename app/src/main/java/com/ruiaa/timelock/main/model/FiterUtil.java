package com.ruiaa.timelock.main.model;

import android.support.annotation.NonNull;

import com.ruiaa.timelock.common.consts.AppCode;
import com.ruiaa.timelock.main.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruiaa on 2016/10/3.
 */

public class FiterUtil {
    public static List<AppInfo> toInstall(@NonNull List<AppInfo> appInfoList){
        List<AppInfo> list=new ArrayList<>();
        for (AppInfo appInfo:appInfoList) {
            if (appInfo.isInstall()&&appInfo.getAppType()!= AppCode.APP_TYPE_USELESS){
                list.add(appInfo);
            }
        }
        return list;
    }
}
