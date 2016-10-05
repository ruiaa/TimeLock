package com.ruiaa.timelock.monitor.modules;

import android.content.ContentValues;
import android.database.Cursor;

import com.ruiaa.timelock.common.consts.SqlField;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.monitor.MonitorService;
import com.ruiaa.timelock.monitor.model.DataCom;
import com.ruiaa.timelock.monitor.model.sqlite.DataComSqlite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruiaa on 2016/10/1.
 */

public class UsageRecord {

    private DataCom dataCom;
    Map<String, Float> usages;

    private UsageRecord() {
        dataCom = DataComSqlite.getInstance();
        usages = new HashMap<>();
    }

    public static UsageRecord getUsageRecord() {
        return new UsageRecord();
    }

    public void record(String pkg) {
        if (pkg != null) {
            if (usages.containsKey(pkg)) {
                float usage = usages.get(pkg)+ MonitorService.Monitor_Interval;
                usages.put(pkg, usage);
            } else {
                usages.put(pkg, MonitorService.Monitor_Interval);
                if (usages.size()>=10){
                    saveAll();
                }
            }
        }
    }

    public void saveAll() {

        if(!usages.isEmpty()) {
            Cursor cursor = null;
            ContentValues contentValues;
            String[] strings = {SqlField.PACKAGE, "["+MonitorService.TodayDate+"]"};
            try {

                for (Map.Entry<String, Float> usage : usages.entrySet()) {
                    cursor = dataCom.query(SqlField.TABLE_APP_INFO, strings,
                            SqlField.PACKAGE + "='" + usage.getKey() + "'", null, null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        int oldTime = cursor.getInt(cursor.getColumnIndex(MonitorService.TodayDate));
                        contentValues = new ContentValues();
                        contentValues.put("["+MonitorService.TodayDate+"]", usage.getValue().intValue() + oldTime);
                        dataCom.update(SqlField.TABLE_APP_INFO, contentValues,
                                SqlField.PACKAGE + "='" + usage.getKey() + "'", null);
                    }
                }
                usages.clear();
                LogUtil.i("saveAll--");
            } catch (Exception e) {
                LogUtil.e("saveAll#", e);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }
}
