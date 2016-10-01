package com.ruiaa.timelock.monitor.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by ruiaa on 2016/9/30.
 */

public interface DataCom {

    long insert(String table, String nullColumnHack, ContentValues values);

    Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy);

    int	update(String table, ContentValues values, String whereClause, String[] whereArgs);

    int	delete(String table, String whereClause, String[] whereArgs);

    void execSQL(String sql);

    void beginTransaction();

    void setTransactionSuccessful();

    void endTransaction();

}
