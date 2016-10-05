package com.ruiaa.timelock.monitor.modules;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.consts.ConfigCode;
import com.ruiaa.timelock.common.consts.SqlField;
import com.ruiaa.timelock.common.utils.DataConvert;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.common.utils.SPUtils;
import com.ruiaa.timelock.monitor.MonitorService;
import com.ruiaa.timelock.monitor.model.DataCom;
import com.ruiaa.timelock.monitor.model.sqlite.DataComSqlite;

import java.util.Calendar;

/**
 * Created by ruiaa on 2016/10/1.
 */

public class DateManager {

    DataCom dataCom;

    private DateManager(){
        dataCom=DataComSqlite.getInstance();
    }

    public static DateManager getDateManager(){
        return new DateManager();
    }

    public void updateDateFieldAndDeleteLock(){

        String todayDate = String.valueOf(DataConvert.date(Calendar.getInstance()));

        //判断数据库与手机日期是否同步
        if (!todayDate.equals(MonitorService.TodayDate)) {

            //增加dateToday列
            try {
                dataCom.execSQL("alter table " + SqlField.TABLE_APP_INFO +
                        " add [" + todayDate + "] integer not null default 0 ");

                MonitorService.TodayDate = todayDate;
                (new SPUtils(App.getAppContext(),MonitorService.SPFileName)).putString(ConfigCode.DATE_TODAY,todayDate);
            } catch (Exception e) {
                LogUtil.e("updateDateFieldAndDeleteLock#",e);
            }

            //删除当天锁
            dataCom.delete(SqlField.TABLE_LOCK, SqlField.LOCK_REPEAT + "=" + 0, null);
            LogUtil.i("updateDateFieldAndDeleteLock#"+"another day");
        }


    }

}
