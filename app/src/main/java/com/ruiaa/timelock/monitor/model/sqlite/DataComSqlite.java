package com.ruiaa.timelock.monitor.model.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ruiaa.timelock.monitor.model.DataCom;

/**
 * Created by ruiaa on 2016/9/30.
 */

public class DataComSqlite implements DataCom {
    
    SQLiteDatabase sqLiteDatabase;
    
    private static DataComSqlite instance = null;
     
    private DataComSqlite(){
        sqLiteDatabase=new CreateSql().getWritableDatabase();
    }
     
    public static DataComSqlite getInstance() {
        if(instance==null) {
            synchronized (DataComSqlite.class) {
                if (instance == null) {
                    instance = new DataComSqlite();
                }
            }
        }
        return instance;
    }

    private DataComSqlite(Context context){
        sqLiteDatabase=new CreateSql(context).getWritableDatabase();
    }

    public static DataComSqlite getInstance(Context context) {
        if(instance==null) {
            synchronized (DataComSqlite.class) {
                if (instance == null) {
                    instance = new DataComSqlite(context);
                }
            }
        }
        return instance;
    }
    

    public long insert(String table, String nullColumnHack, ContentValues values){
        return sqLiteDatabase.insert(table,nullColumnHack,values);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return sqLiteDatabase.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
    }

    public int	update(String table, ContentValues values, String whereClause, String[] whereArgs){
        return  sqLiteDatabase.update(table,values,whereClause,whereArgs);
    }

    public int	delete(String table, String whereClause, String[] whereArgs){
        return sqLiteDatabase.delete(table,whereClause,whereArgs);
    }

    public void execSQL(String sql){
        sqLiteDatabase.execSQL(sql);
    }

    public void beginTransaction(){
        sqLiteDatabase.beginTransaction();
    }

    public void setTransactionSuccessful(){
        sqLiteDatabase.setTransactionSuccessful();
    }

    public void endTransaction(){
        sqLiteDatabase.endTransaction();
    }

}
