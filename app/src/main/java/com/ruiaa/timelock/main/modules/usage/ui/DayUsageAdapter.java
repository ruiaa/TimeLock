package com.ruiaa.timelock.main.modules.usage.ui;

import android.content.Context;

import com.ruiaa.timelock.BR;
import com.ruiaa.timelock.R;
import com.ruiaa.timelock.main.modules.BaseRecyclerAdapter;
import com.ruiaa.timelock.main.modules.usage.viewmodel.AllDayModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ruiaa on 2016/10/6.
 */

public class DayUsageAdapter extends BaseRecyclerAdapter {

    private List<String> pkgList;
    private Map<String,Integer> usageMap;

    private int date;
    private AllDayModel allDayModel;
    private AllDayFragment allDayFragment;

    public DayUsageAdapter(int date, AllDayModel allDayModel, AllDayFragment allDayFragment) {
        this.date = date;
        this.allDayModel = allDayModel;
        this.allDayFragment = allDayFragment;

        usageMap=allDayModel.getDayUsage(date);
        pkgList=new ArrayList<>();
        pkgList.addAll(usageMap.keySet());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_usage;
    }

    @Override
    protected Context getContext() {
        return allDayFragment.getActivity();
    }

    @Override
    public int getItemCount() {
        return pkgList.size();
    }



    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.getBinding().setVariable(BR.usageApp,allDayModel.getAppInfo(pkgList.get(position)));
        holder.getBinding().setVariable(BR.usageTime,usageMap.get(pkgList.get(position)));
        holder.getBinding().setVariable(BR.maxUsageTime,usageMap.get(pkgList.get(0)));
        holder.getBinding().executePendingBindings();
    }

}
