package cn.studyjams.s2.sj0225.rocex.bodydata.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rocexwang on 2017-6-6 21:37:01
 */
public class BodyDataDBHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bodydata.db";
    
    private static String strCreateTableSQL = "create table bodydata (id integer primary key autoincrement, weight real, height real, bmi" +
            " real, create_time long, ts long);";
    
    private Context context;
    
    public BodyDataDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    
        this.context = context;
    }
    
    public BodyDataDBHelper(Context context)
    {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public BodyDataDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler)
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
    
    public int update(BodyData... bodyDatas)
    {
        if(bodyDatas == null)
        {
            return 0;
        }
        
        int iCount = 0;
        
        SQLiteDatabase db = new BodyDataDBHelper(context).getWritableDatabase();
        
        try
        {
            for(BodyData bodyData : bodyDatas)
            {
                ContentValues contentValues = new ContentValues();
                
                contentValues.put(BodyData.BMI, bodyData.bmi);
                contentValues.put(BodyData.HEIGHT, bodyData.height);
                contentValues.put(BodyData.WEIGHT, bodyData.weight);
                contentValues.put(BodyData.CREATE_TIME, bodyData.getCreate_time());
                
                iCount += db.update(BodyData.TABLE_NAME, contentValues, BodyData.ID + "=?", new String[]{String.valueOf(bodyData.getId())});
            }
        }
        finally
        {
            close();
        }
        
        return iCount;
    }
    
    public int delete(BodyData... bodyDatas)
    {
        if(bodyDatas == null)
        {
            return 0;
        }
        
        int iCount = 0;
        
        SQLiteDatabase db = new BodyDataDBHelper(context).getWritableDatabase();
        
        try
        {
            for(BodyData bodyData : bodyDatas)
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
    
    public List<Long> insert(BodyData... bodyDatas)
    {
        List<Long> listId = new ArrayList<Long>();
        
        if(bodyDatas == null)
        {
            return listId;
        }
        
        SQLiteDatabase db = new BodyDataDBHelper(context).getWritableDatabase();
        
        try
        {
            for(BodyData bodyData : bodyDatas)
            {
                ContentValues contentValues = new ContentValues();
                
                contentValues.put(BodyData.BMI, bodyData.bmi);
                contentValues.put(BodyData.HEIGHT, bodyData.height);
                contentValues.put(BodyData.WEIGHT, bodyData.weight);
                contentValues.put(BodyData.CREATE_TIME, bodyData.getCreate_time());
                
                listId.add(db.insert(BodyData.TABLE_NAME, null, contentValues));
            }
        }
        finally
        {
            close();
        }
        
        return listId;
    }
    
    public List<BodyData> query()
    {
        SQLiteDatabase db = getReadableDatabase();
        
        Cursor cursor = db.query(BodyData.TABLE_NAME, new String[]{}, null, null, null, null, BodyData.CREATE_TIME);
        
        cursor.moveToFirst();
        
        List<BodyData> listBodyData = new ArrayList<>();
        
        do
        {
            BodyData bodyData = new BodyData();
            
            bodyData.setId(cursor.getLong(cursor.getColumnIndex(BodyData.ID)));
            bodyData.setTs(cursor.getLong(cursor.getColumnIndex(BodyData.TS)));
            bodyData.setCreate_time(cursor.getLong(cursor.getColumnIndex(BodyData.CREATE_TIME)));
            
            bodyData.bmi = cursor.getDouble(cursor.getColumnIndex(BodyData.BMI));
            bodyData.height = cursor.getDouble(cursor.getColumnIndex(BodyData.HEIGHT));
            bodyData.weight = cursor.getDouble(cursor.getColumnIndex(BodyData.WEIGHT));
            
            listBodyData.add(bodyData);
            
        } while(cursor.isAfterLast());
        
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
