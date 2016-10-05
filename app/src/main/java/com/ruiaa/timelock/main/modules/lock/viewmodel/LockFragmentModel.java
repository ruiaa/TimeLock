package com.ruiaa.timelock.main.modules.lock.viewmodel;

import android.app.TimePickerDialog;
import android.content.Context;

import com.ruiaa.timelock.BR;
import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.utils.DataConvert;
import com.ruiaa.timelock.common.utils.ResUtil;
import com.ruiaa.timelock.main.entity.AppInfo;
import com.ruiaa.timelock.main.entity.Lock;
import com.ruiaa.timelock.main.model.AppInfoProvider;
import com.ruiaa.timelock.main.modules.BaseRecyclerViewAdapter;
import com.ruiaa.timelock.main.modules.BaseViewModel;

import java.util.Calendar;
import java.util.List;


/**
 * Created by ruiaa on 2016/10/4.
 */

public class LockFragmentModel extends BaseViewModel {

    private String packageName;
    private AppInfo appInfo;
    private List<Lock> locks;

    private Context context;

    private BaseRecyclerViewAdapter<Lock> adapter;

    public LockFragmentModel(String packageName,Context context) {
        this.packageName = packageName;
        this.appInfo = AppInfoProvider.getInstance().getTheApp(packageName);
        this.locks = AppInfoProvider.getInstance().getLock(packageName);
        this.context=context;
    }

    public BaseRecyclerViewAdapter<Lock> getAdapter() {
        adapter = new BaseRecyclerViewAdapter<>(
                R.layout.item_lock,
                locks,
                ((holder, position, model) -> {
                    holder.getBinding().setVariable(BR.lock, model);
                    holder.getBinding().setVariable(BR.lockFragmentModel,LockFragmentModel.this);
                })
        );
        return adapter;
    }

    public Lock getLock(int index) {
        return locks.get(index);
    }

    public void saveLock(Lock newLock, Lock oldLock) {
        newLock.copySettingTo(oldLock);
        AppInfoProvider.getInstance().updateLock(oldLock);
    }

    public void addLock(Lock newLock) {
        AppInfoProvider.getInstance().addLock(newLock);
        locks.clear();
        locks.addAll(AppInfoProvider.getInstance().getLock(packageName));
        adapter.notifyDataSetChanged();
    }

    public void deleteLock(Lock lock) {
        AppInfoProvider.getInstance().deleteLock(lock);
        locks.clear();
        locks.addAll(AppInfoProvider.getInstance().getLock(packageName));
        adapter.notifyDataSetChanged();
    }

    public void setStartTime(Lock lock) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(
                context,
                (view, hourOfDay, minute) -> {
                    lock.setStartTime(DataConvert.time(hourOfDay,minute,0));
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show();
    }

    public void setFinishTime(Lock lock) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(
                context,
                (view, hourOfDay, minute) -> {
                    lock.setFinishTime(DataConvert.time(hourOfDay,minute,0));
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show();
    }

    public String getPackageName() {
        return packageName;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public String lockRepeatToString(Lock lock){
        StringBuilder builder=new StringBuilder();
        builder.append(ResUtil.getString(R.string.repeat));
        builder.append(": ");
        if (!lock.getWeekRepeat()){
            return builder.append(ResUtil.getString(R.string.none)).toString();
        }
        if (lock.getMonRepeat()){
            builder.append(ResUtil.getString(R.string.monday_week));
        }
        if (lock.getTueRepeat()){
            builder.append(" , ");
            builder.append(ResUtil.getString(R.string.tuesday_week));
        }
        if (lock.getWedRepeat()){
            builder.append(" , ");
            builder.append(ResUtil.getString(R.string.wednesday_week));
        }
        if (lock.getThuRepeat()){
            builder.append(" , ");
            builder.append(ResUtil.getString(R.string.thursday_week));
        }
        if (lock.getFriRepeat()){
            builder.append(" , ");
            builder.append(ResUtil.getString(R.string.friday_week));
        }
        if (lock.getSatRepeat()){
            builder.append(" , ");
            builder.append(ResUtil.getString(R.string.saturday_week));
        }
        if (lock.getSunRepeat()){
            builder.append(" , ");
            builder.append(ResUtil.getString(R.string.sunday_week));
        }
        return builder.toString();
    }

    public String lockTimeToString(Lock lock){
        StringBuilder builder=new StringBuilder();
        builder.append(ResUtil.getString(R.string.time));
        builder.append(": ");
        builder.append("  ");
        builder.append(DataConvert.time(lock.getStartTime()));
        builder.append(" - ");
        builder.append(DataConvert.time(lock.getFinishTime()));
        return builder.toString();
    }

    @Override
    public void destroy() {
        packageName=null;
        appInfo=null;
        locks=null;
        context=null;
    }
}
