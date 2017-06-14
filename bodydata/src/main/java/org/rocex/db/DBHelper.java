package org.rocex.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.rocex.model.SuperModel;

import java.util.ArrayList;
import java.util.List;

import cn.studyjams.s2.sj0225.rocex.bodydata.model.BodyData;

/**
 * Created by rocexwang on 2017-06-14 11:23:26
 */
public class DBHelper<T extends SuperModel> extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bodydata.db";
    
    private static String strCreateTableSQL = "create table bodydata (id integer primary key autoincrement, weight real, height real, bmi" + " real, create_time long, ts long);";
    
    private Context context;
    
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        
        this.context = context;
    }
    
    public DBHelper(Context context)
    {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);
    }
    
    public static void close(Cursor cursor)
    {
        if(cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
    }
    
    public static void close(SQLiteDatabase db)
    {
        if(db != null && db.isOpen())
        {
            db.close();
        }
    }
    
    public int update(String strPropNames[], T... bodyDatas)
    {
        if(bodyDatas == null)
        {
            return 0;
        }
        
        int iCount = 0;
        
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        
        try
        {
            for(T bodyData : bodyDatas)
            {
                ContentValues contentValues = new ContentValues();
                
                contentValues.put(BodyData.BMI, bodyData.bmi);
                contentValues.put(BodyData.HEIGHT, bodyData.height);
                contentValues.put(BodyData.WEIGHT, bodyData.weight);
                contentValues.put(BodyData.CREATE_TIME, bodyData.getCreate_time());
                contentValues.put(BodyData.TS, bodyData.getTs());
                
                iCount += db.update(BodyData.TABLE_NAME, contentValues, BodyData.ID + "=?", new String[]{String.valueOf(bodyData.getId())});
            }
        }
        finally
        {
            close();
        }
        
        return iCount;
    }
    
    public int delete(T... bodyDatas)
    {
        if(bodyDatas == null)
        {
            return 0;
        }
        
        int iCount = 0;
        
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        
        try
        {
            for(T bodyData : bodyDatas)
            {
                iCount += db.delete(BodyData.TABLE_NAME, BodyData.ID + "=?", new String[]{String.valueOf(bodyData.getId())});
            }
        }
        finally
        {
            close();
        }
        
        return iCount;
    }
    
    public List<Long> insert(String strPropNames[], T... bodyDatas)
    {
        List<Long> listId = new ArrayList<Long>();
        
        if(bodyDatas == null)
        {
            return listId;
        }
        
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        
        try
        {
            for(T bodyData : bodyDatas)
            {
                ContentValues contentValues = new ContentValues();
                
                contentValues.put(BodyData.BMI, bodyData.bmi);
                contentValues.put(BodyData.HEIGHT, bodyData.height);
                contentValues.put(BodyData.WEIGHT, bodyData.weight);
                contentValues.put(BodyData.CREATE_TIME, bodyData.getCreate_time());
                contentValues.put(BodyData.TS, bodyData.getTs());
                
                listId.add(db.insert(BodyData.TABLE_NAME, null, contentValues));
            }
        }
        finally
        {
            close();
        }
        
        return listId;
    }
    
    public BodyData queryById(long id)
    {
        List<BodyData> listBodyData = query(BodyData.ID + "=?", String.valueOf(id));
        
        return listBodyData.get(0);
    }
    
    public List<BodyData> query(String strSelection, String... strSelectionArgs)
    {
        List<BodyData> listBodyData = new ArrayList<BodyData>();
        
        try
        {
            SQLiteDatabase db = getReadableDatabase();
            
            Cursor cursor = db.query(BodyData.TABLE_NAME, null, strSelection, strSelectionArgs, null, null, BodyData.CREATE_TIME, "20");
            
            cursor.moveToFirst();
            
            while(!cursor.isAfterLast())
            {
                BodyData bodyData = new BodyData();
                
                bodyData.setId(cursor.getLong(cursor.getColumnIndex(BodyData.ID)));
                bodyData.setTs(cursor.getLong(cursor.getColumnIndex(BodyData.TS)));
                bodyData.setCreate_time(cursor.getLong(cursor.getColumnIndex(BodyData.CREATE_TIME)));
                
                bodyData.bmi = cursor.getDouble(cursor.getColumnIndex(BodyData.BMI));
                bodyData.height = cursor.getDouble(cursor.getColumnIndex(BodyData.HEIGHT));
                bodyData.weight = cursor.getDouble(cursor.getColumnIndex(BodyData.WEIGHT));
                
                listBodyData.add(bodyData);
                
                cursor.moveToNext();
            }
        }
        finally
        {
            close();
        }
        
        return listBodyData;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Cursor cursor = null;
        
        try
        {
            cursor = db.rawQuery("select name from sqlite_master where type='table' and name='bodydata'", null);
            
            if(cursor.getCount() == 0)
            {
                db.execSQL(strCreateTableSQL);
            }
        }
        finally
        {
            close(cursor);
        }
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}
