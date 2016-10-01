package com.ruiaa.timelock.common.consts;

import java.util.Calendar;

/**
 * Created by ruiaa on 2016/9/30.
 */

public class SqlField {

    // 数据库名
    public static final String DATABASE_NAME = "TimeLock.db";


    //appInfo
    // 数据表名
    public static final String TABLE_APP_INFO = "appInfo";
    //appInfo列名
    public static final String APP_NAME = "appName";
    public static final String PACKAGE = "packageName";
    public static final String ICON = "icon";
    public static final String APP_TYPE = "Type";
    public static final String INSTALL = "install";

    public static final String LIMIT_STATE = "limitState";
    public static final String LIMIT_MONDAY = "limitMon";
    public static final String LIMIT_TUESDAY = "limitTue";
    public static final String LIMIT_WEDNESDAY = "limitWed";
    public static final String LIMIT_THURSDAY = "limitThu";
    public static final String LIMIT_FRIDAY = "limitFri";
    public static final String LIMIT_SATURDAY = "limitSat";
    public static final String LIMIT_SUNDAY = "limitSun";


    //lock
    // 数据表名
    public static final String TABLE_LOCK = "lock";
    //appInfo列名
    //public static final String PACKAGE = "packageName";
    public static final String LOCK_ORDER = "lockOrder";      //以设置该锁时的时刻排序
    public static final String LOCK_TYPE = "lockType";
    public static final String LOCK_START_TIME = "lockStart";
    public static final String LOCK_FINISH_TIME = "lockFinish";

    public static final String LOCK_STATE = "lockState";

    public static final String LOCK_REPEAT = "lockWeek";
    public static final String REPEAT_MONDAY = "repeatMon";
    public static final String REPEAT_TUESDAY = "repeatTue";
    public static final String REPEAT_WEDNESDAY = "repeatWed";
    public static final String REPEAT_THURSDAY = "repeatThu";
    public static final String REPEAT_FRIDAY = "repeatFri";
    public static final String REPEAT_SATURDAY = "repeatSat";
    public static final String REPEAT_SUNDAY = "repeatSun";

    public static String getCurrentLimitWeek() {
        Calendar calendar;
        calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY: {
                return LIMIT_MONDAY;
            }
            case Calendar.TUESDAY:{
                return LIMIT_TUESDAY;
            }
            case Calendar.WEDNESDAY:{
                return LIMIT_WEDNESDAY;
            }
            case Calendar.THURSDAY:{
                return LIMIT_THURSDAY;
            }
            case Calendar.FRIDAY:{
                return LIMIT_FRIDAY;
            }
            case Calendar.SATURDAY:{
                return LIMIT_SATURDAY;
            }
            case Calendar.SUNDAY:{
                return LIMIT_SUNDAY;
            }
            default:{
                return null;
            }
        }
    }

    public static String getCurrentLockRepeatWeek() {
        Calendar calendar;
        calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY: {
                return REPEAT_MONDAY;
            }
            case Calendar.TUESDAY:{
                return REPEAT_TUESDAY;
            }
            case Calendar.WEDNESDAY:{
                return REPEAT_WEDNESDAY;
            }
            case Calendar.THURSDAY:{
                return REPEAT_THURSDAY;
            }
            case Calendar.FRIDAY:{
                return REPEAT_FRIDAY;
            }
            case Calendar.SATURDAY:{
                return REPEAT_SATURDAY;
            }
            case Calendar.SUNDAY:{
                return REPEAT_SUNDAY;
            }
            default:{
                return null;
            }
        }
    }
}
