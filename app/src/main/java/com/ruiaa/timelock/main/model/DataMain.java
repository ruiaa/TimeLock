package com.ruiaa.timelock.main.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by ruiaa on 2016/10/1.
 */

public interface DataMain {

    long insert(String table,ContentValues values);

    Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String orderBy);

    int	update(String table, ContentValues values, String whereClause, String[] whereArgs);

    int	delete(String table, String whereClause, String[] whereArgs);

}
