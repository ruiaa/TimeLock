package com.ruiaa.timelock.monitor.model.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.consts.SqlField;
import com.ruiaa.timelock.common.utils.LogUtil;

/**
 * Created by ruiaa on 2016/9/30.
 */
public class CreateSql extends SQLiteOpenHelper {

    // 数据库版本号
    public static final int DATABASE_VERSION = 1;

    public CreateSql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CreateSql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public CreateSql() {
        super(App.getAppContext(), SqlField.DATABASE_NAME, null,DATABASE_VERSION);
        // 数据库实际被创建是在getWritableDatabase()或getReadableDatabase()方法调用时
        // CursorFactory设置为null,使用系统默认的工厂类
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 调用时间：数据库第一次创建时onCreate()方法会被调用
        // onCreate方法有一个 SQLiteDatabase对象作为参数，根据需要对这个对象填充表和初始化数据
        // 这个方法中主要完成创建数据库后对数据库的操作
        // 构建创建表的SQL语句（可以从SQLite Expert工具的DDL粘贴过来加进StringBuffer中）

        // 即便程序修改重新运行，只要数据库已经创建过，就不会再进入这个onCreate方法
        StringBuffer sBuffer = new StringBuffer();

        sBuffer.append("create table " + SqlField.TABLE_APP_INFO + " (");
        //app信息
        sBuffer.append(SqlField.APP_NAME + " varchar(30) ,");
        sBuffer.append(SqlField.PACKAGE + " varchar(40) not null primary key ,");
        sBuffer.append(SqlField.ICON + " blob,");
        sBuffer.append(SqlField.APP_TYPE + " integer not null default 1,");
        sBuffer.append(SqlField.INSTALL + " integer not null default 1 ,");

        sBuffer.append(SqlField.LIMIT_MONDAY + " integer not null default 86400,");
        sBuffer.append(SqlField.LIMIT_TUESDAY + " integer not null default 86400,");
        sBuffer.append(SqlField.LIMIT_WEDNESDAY + " integer not null default 86400,");
        sBuffer.append(SqlField.LIMIT_THURSDAY + " integer not null default 86400,");
        sBuffer.append(SqlField.LIMIT_FRIDAY + " integer not null default 86400,");
        sBuffer.append(SqlField.LIMIT_SATURDAY + " integer not null default 86400,");
        sBuffer.append(SqlField.LIMIT_SUNDAY+ " integer not null default 86400,");

        sBuffer.append(SqlField.LIMIT_STATE+ " integer not null default 0)");

        db.execSQL(sBuffer.toString());

        LogUtil.i("table app_info create ok");





        StringBuffer sBufferLock = new StringBuffer();
        sBufferLock.append("create table " + SqlField.TABLE_LOCK + " (");
        sBufferLock.append(SqlField.PACKAGE+ " varchar(40) NOT NULL ,");

        sBufferLock.append(SqlField.LOCK_ORDER + " integer not null primary key ,");

        sBufferLock.append(SqlField.LOCK_TYPE + " integer not null,");
        sBufferLock.append(SqlField.LOCK_STATE+ " integer not null default 1,");

        sBufferLock.append(SqlField.LOCK_REPEAT + " integer not null default 0,");
        sBufferLock.append(SqlField.REPEAT_MONDAY + " integer not null default 0,");
        sBufferLock.append(SqlField.REPEAT_TUESDAY + " integer not null default 0,");
        sBufferLock.append(SqlField.REPEAT_WEDNESDAY + " integer not null default 0,");
        sBufferLock.append(SqlField.REPEAT_THURSDAY + " integer not null default 0,");
        sBufferLock.append(SqlField.REPEAT_FRIDAY + " integer not null default 0,");
        sBufferLock.append(SqlField.REPEAT_SATURDAY + " integer not null default 0,");
        sBufferLock.append(SqlField.REPEAT_SUNDAY+ " integer not null default 0,");

        sBufferLock.append(SqlField.LOCK_START_TIME + " integer not null,");
        sBufferLock.append(SqlField.LOCK_FINISH_TIME + " integer not null)");

        db.execSQL(sBufferLock.toString());

        LogUtil.i("table lock create ok");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
