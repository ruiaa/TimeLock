package com.ruiaa.timelock.main.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.ruiaa.timelock.common.consts.AppCode;
import com.ruiaa.timelock.common.consts.SqlField;
import com.ruiaa.timelock.common.utils.DataConvert;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.main.entity.AppInfo;
import com.ruiaa.timelock.main.entity.Lock;
import com.ruiaa.timelock.main.entity.TimeLimit;
import com.ruiaa.timelock.main.model.cp.DataMainContentResolver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruiaa on 2016/10/1.
 */

public class AppInfoProvider {

    private DataMain dataMain;


    //
    // 限定底层实现 DataMainContentResolver
    private static AppInfoProvider instance = null;

    private AppInfoProvider() {
        dataMain = DataMainContentResolver.getInstance();
    }

    public static AppInfoProvider getInstance() {
        if (instance == null) {
            synchronized (AppInfoProvider.class) {
                if (instance == null) {
                    instance = new AppInfoProvider();
                }
            }
        }
        return instance;
    }


    //
    //
    //自定接口的底层实现
    public AppInfoProvider(DataMain dataMain) {
        this.dataMain = dataMain;
    }


    //
    //
    //
    //
    public AppInfo getTheApp(String packageName) {
        AppInfo appInfo = null;
        Cursor cursor = null;
        String[] strs = {SqlField.PACKAGE, SqlField.APP_NAME, SqlField.APP_TYPE, SqlField.ICON, SqlField.INSTALL};
        try {
            cursor = dataMain.query(SqlField.TABLE_APP_INFO, strs,
                    SqlField.PACKAGE + "='" + packageName + "'", null, null);

            if (cursor != null && cursor.moveToFirst()) {
                appInfo = new AppInfo(
                        cursor.getString(cursor.getColumnIndex(SqlField.PACKAGE)),
                        cursor.getString(cursor.getColumnIndex(SqlField.APP_NAME)),
                        DataConvert.drawable_bytes(cursor.getBlob(cursor.getColumnIndex(SqlField.ICON))),
                        cursor.getInt(cursor.getColumnIndex(SqlField.APP_TYPE)),
                        cursor.getInt(cursor.getColumnIndex(SqlField.INSTALL)) == AppCode.IS_INSTALL_TRUE
                );
            }
        } catch (Exception e) {
            LogUtil.e("getTheApp#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return appInfo;
    }

    public List<AppInfo> getAllApp() {

        List<AppInfo> appInfos = new ArrayList<>();
        String[] strs = {SqlField.PACKAGE, SqlField.APP_NAME, SqlField.APP_TYPE, SqlField.ICON, SqlField.INSTALL};
        Cursor cursor = null;

        try {
            cursor = dataMain.query(SqlField.TABLE_APP_INFO, strs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    AppInfo appInfo = new AppInfo(
                            cursor.getString(cursor.getColumnIndex(SqlField.PACKAGE)),
                            cursor.getString(cursor.getColumnIndex(SqlField.APP_NAME)),
                            DataConvert.drawable_bytes(cursor.getBlob(cursor.getColumnIndex(SqlField.ICON))),
                            cursor.getInt(cursor.getColumnIndex(SqlField.APP_TYPE)),
                            cursor.getInt(cursor.getColumnIndex(SqlField.INSTALL)) == AppCode.IS_INSTALL_TRUE
                    );
                    appInfos.add(appInfo);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            LogUtil.e("getAllApp#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return appInfos;
    }

    public List<Integer> getDate() {
        List<Integer> arrayList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_APP_INFO, null,
                    SqlField.PACKAGE + "='" + AppCode.THIS_PACKSGE + "'", null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String[] strings = cursor.getColumnNames();
                for (String s : strings) {
                    if (s.charAt(0) == '2') {
                        arrayList.add(Integer.parseInt(s));
                    }
                }
            }

        } catch (Exception e) {
            LogUtil.e("getDate#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return arrayList;
    }

    public int getTodayUsage(String pkg) {
        int usage = 0;
        String date = String.valueOf(DataConvert.date(Calendar.getInstance()));
        String[] strs = {date};
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_APP_INFO, strs,
                    SqlField.PACKAGE + "='" + pkg + "'", null, null);

            if (cursor != null && cursor.moveToFirst()) {
                usage = cursor.getInt(cursor.getColumnIndex(date));
            }
        } catch (Exception e) {
            LogUtil.e("getAllAppUsage#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return usage;
    }

    public Map<String, Integer> getAllAppUsage(int date) {
        Map<String, Integer> map = new LinkedHashMap<>();

        String[] strs = {SqlField.PACKAGE, "["+String.valueOf(date)+"]"};
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_APP_INFO, strs,
                    "["+String.valueOf(date) + "] >0", null, "["+String.valueOf(date) + "] desc");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String pkg = cursor.getString(cursor.getColumnIndex(SqlField.PACKAGE));
                    map.put(pkg, cursor.getInt(cursor.getColumnIndex(String.valueOf(date))));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            LogUtil.e("getAllAppUsage#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return map;
    }

    public Map<Integer, Integer> getTheAppAllUsage(String pkg) {
        Map<Integer, Integer> map = new HashMap<>();
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_APP_INFO, null,
                    SqlField.PACKAGE + "='" + pkg + "'", null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String[] strings = cursor.getColumnNames();
                for (String s : strings) {
                    if (s.charAt(0) == '2') {
                        map.put(Integer.parseInt(s), cursor.getInt(cursor.getColumnIndex(s)));
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e("#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return map;
    }

    public TimeLimit getTimeLimit(String packageName) {
        TimeLimit timeLimit = new TimeLimit(packageName);
        String[] strs = {SqlField.LIMIT_STATE, SqlField.LIMIT_MONDAY, SqlField.LIMIT_TUESDAY, SqlField.LIMIT_WEDNESDAY,
                SqlField.LIMIT_THURSDAY, SqlField.LIMIT_FRIDAY, SqlField.LIMIT_SATURDAY, SqlField.LIMIT_SUNDAY
        };
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_APP_INFO, strs,
                    SqlField.PACKAGE + "='" + packageName + "'", null, null);

            if (cursor != null && cursor.moveToFirst()) {
                timeLimit.setLimitState(cursor.getInt(cursor.getColumnIndex(SqlField.LIMIT_STATE)) == AppCode.LIMIT_STATE_OPEN)
                        .setMonLimit(cursor.getInt(cursor.getColumnIndex(SqlField.LIMIT_MONDAY)))
                        .setTueLimit(cursor.getInt(cursor.getColumnIndex(SqlField.LIMIT_TUESDAY)))
                        .setWedLimit(cursor.getInt(cursor.getColumnIndex(SqlField.LIMIT_WEDNESDAY)))
                        .setThuLimit(cursor.getInt(cursor.getColumnIndex(SqlField.LIMIT_THURSDAY)))
                        .setFriLimit(cursor.getInt(cursor.getColumnIndex(SqlField.LIMIT_FRIDAY)))
                        .setSatLimit(cursor.getInt(cursor.getColumnIndex(SqlField.LIMIT_SATURDAY)))
                        .setSunLimit(cursor.getInt(cursor.getColumnIndex(SqlField.LIMIT_SUNDAY)));
            }
        } catch (Exception e) {
            LogUtil.e("getTimeLimit#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return timeLimit;
    }

    public void updateTimeLimit(String packageName, TimeLimit timeLimit) {
        ContentValues contentValues;
        contentValues = new ContentValues();
        contentValues.put(SqlField.LIMIT_STATE, timeLimit.getLimitState() ? AppCode.LIMIT_STATE_OPEN : AppCode.LIMIT_STATE_CLOSE);
        contentValues.put(SqlField.LIMIT_MONDAY, timeLimit.getMonLimit());
        contentValues.put(SqlField.LIMIT_TUESDAY, timeLimit.getTueLimit());
        contentValues.put(SqlField.LIMIT_WEDNESDAY, timeLimit.getWedLimit());
        contentValues.put(SqlField.LIMIT_THURSDAY, timeLimit.getThuLimit());
        contentValues.put(SqlField.LIMIT_FRIDAY, timeLimit.getFriLimit());
        contentValues.put(SqlField.LIMIT_SATURDAY, timeLimit.getSatLimit());
        contentValues.put(SqlField.LIMIT_SUNDAY, timeLimit.getSunLimit());

        try {
            dataMain.update(SqlField.TABLE_APP_INFO, contentValues, SqlField.PACKAGE + "='" + packageName + "'", null);
        } catch (Exception e) {
            LogUtil.e("updateTimeLimit#", e);
        }
    }


    public List<Lock> getLock(String packageName) {
        String[] strings = {SqlField.LOCK_TYPE, SqlField.LOCK_ORDER,
                SqlField.REPEAT_MONDAY, SqlField.REPEAT_TUESDAY, SqlField.REPEAT_WEDNESDAY,
                SqlField.REPEAT_THURSDAY, SqlField.REPEAT_FRIDAY, SqlField.REPEAT_SATURDAY, SqlField.REPEAT_SUNDAY,
                SqlField.LOCK_START_TIME, SqlField.LOCK_FINISH_TIME};
        List<Lock> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_LOCK, strings,
                    SqlField.PACKAGE + "='" + packageName + "'", null, SqlField.LOCK_ORDER + " asc");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Lock lock = new Lock(packageName, cursor.getInt(cursor.getColumnIndex(SqlField.LOCK_ORDER)));
                    lock.setType(cursor.getInt(cursor.getColumnIndex(SqlField.LOCK_TYPE)))
                            .setStartTime(cursor.getInt(cursor.getColumnIndex(SqlField.LOCK_START_TIME)))
                            .setFinishTime(cursor.getInt(cursor.getColumnIndex(SqlField.LOCK_FINISH_TIME)))
                            .setMonRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_MONDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setTueRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_TUESDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setWedRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_WEDNESDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setThuRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_THURSDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setFriRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_FRIDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setSatRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_SATURDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setSunRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_SUNDAY)) == AppCode.LOCK_STATE_OPEN);
                    list.add(lock);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            LogUtil.e("getLock#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public List<Lock> getAllLock() {
        String[] strings = {SqlField.PACKAGE, SqlField.LOCK_TYPE, SqlField.LOCK_ORDER,
                SqlField.REPEAT_MONDAY, SqlField.REPEAT_TUESDAY, SqlField.REPEAT_WEDNESDAY,
                SqlField.REPEAT_THURSDAY, SqlField.REPEAT_FRIDAY, SqlField.REPEAT_SATURDAY, SqlField.REPEAT_SUNDAY,
                SqlField.LOCK_START_TIME, SqlField.LOCK_FINISH_TIME};
        List<Lock> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_LOCK, strings,
                    null, null, SqlField.LOCK_ORDER + " asc");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Lock lock = new Lock(cursor.getString(cursor.getColumnIndex(SqlField.PACKAGE)), cursor.getInt(cursor.getColumnIndex(SqlField.LOCK_ORDER)));
                    lock.setType(cursor.getInt(cursor.getColumnIndex(SqlField.LOCK_TYPE)))
                            .setStartTime(cursor.getInt(cursor.getColumnIndex(SqlField.LOCK_START_TIME)))
                            .setFinishTime(cursor.getInt(cursor.getColumnIndex(SqlField.LOCK_FINISH_TIME)))
                            .setMonRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_MONDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setTueRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_TUESDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setWedRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_WEDNESDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setThuRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_THURSDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setFriRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_FRIDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setSatRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_SATURDAY)) == AppCode.LOCK_STATE_OPEN)
                            .setSunRepeat(cursor.getInt(cursor.getColumnIndex(SqlField.REPEAT_SUNDAY)) == AppCode.LOCK_STATE_OPEN);
                    list.add(lock);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            LogUtil.e("getLock#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public List<String> getCurrentForbiddenLockList() {

        List<String> list = new ArrayList<>();
        int currentTime = DataConvert.time(Calendar.getInstance());
        String currentWeek = SqlField.getCurrentLockRepeatWeek();

        String[] strings = {SqlField.PACKAGE};
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_LOCK, strings,
                    SqlField.LOCK_TYPE + "=" + AppCode.LOCK_TYPE_FORBIDDEN + " and " +
                            SqlField.LOCK_START_TIME + " < " + currentTime + " and " +
                            SqlField.LOCK_FINISH_TIME + " > " + currentTime + " and " +
                            "(" + currentWeek + "=1 " + " or " + SqlField.LOCK_REPEAT + "=0 )",
                    null, SqlField.LOCK_ORDER + " desc");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String packageName = cursor.getString(cursor.getColumnIndex(SqlField.PACKAGE));
                    if (list.contains(packageName)) {
                        //已经包含
                    } else {
                        list.add(packageName);
                    }
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            LogUtil.e("getCurrentForbiddenLockList#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public List<String> getCurrentUseLockList() {
        List<String> list = new ArrayList<>();
        int currentTime = DataConvert.time(Calendar.getInstance());
        String currentWeek = SqlField.getCurrentLockRepeatWeek();

        String[] strings = {SqlField.PACKAGE};
        Cursor cursor = null;
        try {
            cursor = dataMain.query(SqlField.TABLE_LOCK, strings,
                    SqlField.LOCK_TYPE + "=" + AppCode.LOCK_TYPE_USE + " and " +
                            SqlField.LOCK_START_TIME + " < " + currentTime + " and " +
                            SqlField.LOCK_FINISH_TIME + " > " + currentTime + " and " +
                            "(" + currentWeek + "=1 " + " or " + SqlField.LOCK_REPEAT + "=0 )",
                    null, SqlField.LOCK_ORDER + " desc");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String packageName = cursor.getString(cursor.getColumnIndex(SqlField.PACKAGE));
                    if (list.contains(packageName)) {
                        //已经包含
                    } else {
                        list.add(packageName);
                    }
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            LogUtil.e("getCurrentForbiddenLockList#", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }


    public void addLock(Lock lock) {
        try {
            dataMain.insert(SqlField.TABLE_LOCK, lock.toContentValues(lock));
        } catch (Exception e) {
            LogUtil.e("addLock#", e);
        }
    }

    public void updateLock(Lock lock) {
        try {
            dataMain.update(SqlField.TABLE_LOCK, lock.toContentValues(lock),
                    SqlField.LOCK_ORDER + "=" + lock.getOrder(), null);
        } catch (Exception e) {
            LogUtil.e("updateLock#", e);
        }
    }

    public void deleteLock(Lock lock) {
        try {
            dataMain.delete(SqlField.TABLE_LOCK, SqlField.LOCK_ORDER + "=" + lock.getOrder(), null);
        } catch (Exception e) {
            LogUtil.e("deleteLock#", e);
        }
    }


}
