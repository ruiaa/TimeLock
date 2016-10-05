package com.ruiaa.timelock.main.modules.usage.viewmodel;

import android.content.Context;

import com.ruiaa.timelock.BR;
import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.utils.Pair;
import com.ruiaa.timelock.main.entity.AppInfo;
import com.ruiaa.timelock.main.model.AppInfoProvider;
import com.ruiaa.timelock.main.model.MainDataCache;
import com.ruiaa.timelock.main.modules.BaseRecyclerViewAdapter;
import com.ruiaa.timelock.main.modules.BaseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by ruiaa on 2016/10/4.
 */

public class AllDayModel extends BaseViewModel {

    private Context context;
    private List<Integer> dateList;
    private int date;
    private int maxUsage;

    public AllDayModel(Context context) {
        this.context = context;
        this.dateList= AppInfoProvider.getInstance().getDate();
    }

    public BaseRecyclerViewAdapter<Pair<AppInfo,Integer>> getDayUsageAdapter(int date){
        List<Pair<AppInfo,Integer>>  usageList=getDayUsage(date);
        if (!usageList.isEmpty()){
            final int maxUsage=usageList.get(0).getB();
            return new BaseRecyclerViewAdapter<>(
                    R.layout.item_usage,
                    usageList,
                    (holder, position, model) -> {
                        holder.getBinding().setVariable(BR.usageApp,model.getA());
                        holder.getBinding().setVariable(BR.usageTime,model.getB());
                        holder.getBinding().setVariable(BR.maxUsageTime,maxUsage);
                    }
            );
        }else {
            return new BaseRecyclerViewAdapter<>(
                    R.layout.item_usage,
                    usageList,
                    (holder, position, model) -> {
                        holder.getBinding().setVariable(BR.usageApp,model.getA());
                        holder.getBinding().setVariable(BR.usageTime,model.getB());
                        holder.getBinding().setVariable(BR.maxUsageTime,0);
                    }
            );
        }
    }

    public List<Pair<AppInfo,Integer>> getDayUsage(int date){
        Pair<List<String>, Map<String, Integer>> allAppUsage=AppInfoProvider.getInstance().getAllAppUsage(date);
        List<Pair<AppInfo,Integer>>  usageList=new ArrayList<>();
        for (String pkg:allAppUsage.getA()) {
            usageList.add(new Pair<>(MainDataCache.getInstance().getAppInfo(pkg),allAppUsage.getB().get(pkg)));
        }
        return usageList;
    }

    @Override
    public void destroy() {
        context=null;
    }
}
