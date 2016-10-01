package com.ruiaa.timelock.monitor.modules;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.ruiaa.timelock.common.consts.ContentProviderUri;
import com.ruiaa.timelock.common.consts.SqlField;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.monitor.model.DataCom;
import com.ruiaa.timelock.monitor.model.sqlite.DataComSqlite;


/**
 * Created by ruiaa on 2016/9/30.
 */
public class DataProvider extends ContentProvider {

    public static final int LOCK = 1;
    public static final int APP_INFO = 2;

    private DataCom dataCom;

    public DataProvider() {

    }

    @Override
    public boolean onCreate() {
        LogUtil.i("DataProvider  create");
        dataCom = DataComSqlite.getInstance();
        return true;
    }


    public static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


        uriMatcher.addURI(ContentProviderUri.CONTENT, ContentProviderUri.TABLE_LOCK, LOCK);
        uriMatcher.addURI(ContentProviderUri.CONTENT, ContentProviderUri.TABLE_APP_INFO, APP_INFO);


    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case LOCK: {
                return dataCom.query(SqlField.TABLE_LOCK, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case APP_INFO: {
                return dataCom.query(SqlField.TABLE_APP_INFO, projection, selection, selectionArgs, null, null, sortOrder);
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case LOCK: {
                return dataCom.delete(SqlField.TABLE_LOCK, selection, selectionArgs);
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case LOCK: {
                dataCom.insert(SqlField.TABLE_LOCK, null, values);
                return null;
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case APP_INFO: {
                return dataCom.update(SqlField.TABLE_APP_INFO, values, selection, selectionArgs);
            }
            case LOCK: {
                return dataCom.update(SqlField.TABLE_LOCK, values, selection, selectionArgs);
            }
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            default: {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        }
    }

}
