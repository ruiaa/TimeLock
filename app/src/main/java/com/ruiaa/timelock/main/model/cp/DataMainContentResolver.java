package com.ruiaa.timelock.main.model.cp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.consts.ContentProviderUri;
import com.ruiaa.timelock.main.model.DataMain;

/**
 * Created by ruiaa on 2016/10/1.
 */

public class DataMainContentResolver implements DataMain {

    private static DataMainContentResolver instance = null;

    private ContentResolver contentResolver;

    private DataMainContentResolver(){
        contentResolver= App.getAppContext().getContentResolver();
    }

    public static DataMainContentResolver getInstance() {
        if(instance==null) {
            synchronized (DataMainContentResolver.class) {
                if (instance == null) {
                    instance = new DataMainContentResolver();
                }
            }
        }
        return instance;
    }

    public long insert(String table,ContentValues values){
        contentResolver.insert(ContentProviderUri.getUri(table),values);
        return 0;
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs,String orderBy){
        return contentResolver.query(ContentProviderUri.getUri(table),columns,selection,selectionArgs,orderBy);
    }

    public int	update(String table, ContentValues values, String whereClause, String[] whereArgs){
        return contentResolver.update(ContentProviderUri.getUri(table),values,whereClause,whereArgs);
    }

    public int	delete(String table, String whereClause, String[] whereArgs){
        return contentResolver.delete(ContentProviderUri.getUri(table),whereClause,whereArgs);
    }
}
