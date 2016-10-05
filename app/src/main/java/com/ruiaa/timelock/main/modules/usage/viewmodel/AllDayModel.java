package com.ruiaa.timelock.main.modules.usage.viewmodel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;

import com.ruiaa.timelock.BR;
import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.main.model.AppInfoProvider;
import com.ruiaa.timelock.main.model.MainDataCache;
import com.ruiaa.timelock.main.modules.BaseRecyclerViewAdapter;
import com.ruiaa.timelock.main.modules.BaseViewModel;
import com.ruiaa.timelock.main.modules.usage.ui.DayUsageView;

import java.util.ArrayList;
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

    public PagerAdapter getAllDayPagerAdapter(){
        List<DayUsageView> list=new ArrayList<>();
        for (Integer i:dateList) {
            list.add(new DayUsageView(this,i));
        }
        LogUtil.i("getAllDayPagerAdapter--");
        return new AllDayPagerAdapter(list);
    }

    public BaseRecyclerViewAdapter<Map.Entry <String,Integer>> getDayUsageAdapter(int date){
        List<Map.Entry <String,Integer>>  usageList=new ArrayList<>();
        usageList.addAll(getDayUsage(date).entrySet());
        BaseRecyclerViewAdapter<Map.Entry <String,Integer>> adapter;
        if (!usageList.isEmpty()){
            final int maxUsage=usageList.get(0).getValue();
            adapter=new BaseRecyclerViewAdapter<Map.Entry <String,Integer>>(
                    R.layout.item_usage,
                    usageList,
                    (holder, position, model) -> {
                        holder.getBinding().setVariable(BR.usageApp, MainDataCache.getInstance().getAppInfo(model.getKey()));
                        holder.getBinding().setVariable(BR.usageTime,model.getValue());
                        holder.getBinding().setVariable(BR.maxUsageTime,maxUsage);
                    }
            );
        }else {
            adapter=new BaseRecyclerViewAdapter<>(
                    R.layout.item_usage,
                    usageList,
                    (holder, position, model) -> {
                        holder.getBinding().setVariable(BR.usageApp,MainDataCache.getInstance().getAppInfo(model.getKey()));
                        holder.getBinding().setVariable(BR.usageTime,model.getValue());
                        holder.getBinding().setVariable(BR.maxUsageTime,0);
                    }
            );
        }
        LogUtil.i("getDayUsageAdapter--");
        return adapter;
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

    public Context getContext() {
        return context;
    }

    @Override
    public void destroy() {
        context=null;
    }
}
