package com.ruiaa.timelock.main.modules.usage.viewmodel;

import android.content.Context;

import com.ruiaa.timelock.main.entity.AppInfo;
import com.ruiaa.timelock.main.model.AppInfoProvider;
import com.ruiaa.timelock.main.model.MainDataCache;
import com.ruiaa.timelock.main.modules.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ruiaa on 2016/10/4.
 */

public class AllDayModel extends BaseViewModel {

    private Context context;
    private List<Integer> dateList;
    private Map<Integer,Map<String,Integer>> allDayUsageMap;


    public AllDayModel(Context context) {
        this.context = context;
        this.dateList= AppInfoProvider.getInstance().getDate();
        this.allDayUsageMap=new HashMap<>();
    }

    public Map<String,Integer> getDayUsage(int date){
        if (allDayUsageMap.containsKey(date)){
            return allDayUsageMap.get(date);
        }else {
            Map<String,Integer> newDate=AppInfoProvider.getInstance().getAllAppUsage(date);
            allDayUsageMap.put(date,newDate);
            return newDate;
        }
    }

    public List<Integer> getDateList() {
        return dateList;
    }

    public AppInfo getAppInfo(String pkg){
        return MainDataCache.getInstance().getAppInfo(pkg);
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void destroy() {
        context=null;
        dateList=null;
        allDayUsageMap=null;
    }
}
