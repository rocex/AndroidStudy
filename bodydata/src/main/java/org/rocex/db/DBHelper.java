package org.rocex.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import org.rocex.model.SuperModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rocexwang on 2017-06-14 11:23:26
 */
public class DBHelper<T extends SuperModel> extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bodydata.db";
    
    private static String strCreateTableSQL = "create table bodydata (id integer primary key autoincrement, weight real, height real, bmi" + " real, create_time long, ts long);";
    
    private Context context;
    
    public DBHelper(Context context)
    {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        
        this.context = context;
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
    
    public ContentValues convertToContentValues(T superModel, String... strFieldNames)
    {
        if(strFieldNames == null || strFieldNames.length == 0)
        {
            strFieldNames = superModel.getPropNames();
        }
        
        ContentValues contentValues = new ContentValues();
    
        for(String strFieldName : strFieldNames)
        {
            Object objPropValue = superModel.getPropValue(strFieldName);
        
            if(objPropValue == null)
            {
                contentValues.putNull(strFieldName);
            
                continue;
            }
        
            Class propType = superModel.getPropType(strFieldName);
        
            //            switch(propType.getClass())
            //            {
            //
            //            }
        
            if(propType.getClass().getName().equals(String.class.getName()))
            {
                contentValues.put(strFieldName, (String) objPropValue);
            }
        }
        
        return contentValues;
    }
    
    public T convertToModel(Class<T> clazzModel, ContentValues contentValues, String... strFieldNames)
    {
        T superModel = null;
        
        try
        {
            superModel = clazzModel.newInstance();
        }
        catch(Exception ex)
        {
            Log.e(DBHelper.class.getName(), "convertToModel: ", ex);
        }
        
        if(strFieldNames == null || strFieldNames.length == 0)
        {
            strFieldNames = superModel.getPropNames();
        }
        
        for(String strFieldName : strFieldNames)
        {
            superModel.setPropValue(strFieldName, contentValues.get(strFieldName));
        }
        
        return superModel;
    }
    
    public int update(String strPropNames[], T... superModels)
    {
        if(superModels == null)
        {
            return 0;
        }
        
        int iCount = 0;
        
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        
        try
        {
            for(T superModel : superModels)
            {
                ContentValues contentValues = getContentValues(superModel);
    
                iCount += db.update(superModel.getTableName(), contentValues, SuperModel.ID + "=?",
                        new String[]{String.valueOf(superModel.getId())});
            }
        }
        finally
        {
            close();
        }
        
        return iCount;
    }
    
    @NonNull
    public ContentValues getContentValues(T superModel, String... strPropNames)
    {
        if(strPropNames == null)
        {
            strPropNames = superModel.getPropNames();
        }
        
        ContentValues contentValues = new ContentValues();
        
        for(String strPropName : strPropNames)
        {
            contentValues.put(strPropName, (String) superModel.getPropValue(strPropName));
        }
        
        return contentValues;
    }
    
    public int delete(T... superModels)
    {
        if(superModels == null)
        {
            return 0;
        }
        
        int iCount = 0;
        
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        
        try
        {
            for(T superModel : superModels)
            {
                iCount += db.delete(superModel.getTableName(), SuperModel.ID + "=?", new String[]{String.valueOf(superModel.getId())});
            }
        }
        finally
        {
            close();
        }
        
        return iCount;
    }
    
    public List<Long> insert(String strPropNames[], T... superModels)
    {
        List<Long> listId = new ArrayList<Long>();
        
        if(superModels == null)
        {
            return listId;
        }
        
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        
        try
        {
            for(T superModel : superModels)
            {
                ContentValues contentValues = getContentValues(superModel);
                
                listId.add(db.insert(superModel.getTableName(), null, contentValues));
            }
        }
        finally
        {
            close();
        }
        
        return listId;
    }
    
    public T queryById(Class<T> clazzModel, long id)
    {
        List<T> listSuperModel = query(clazzModel, SuperModel.ID + "=?", String.valueOf(id));
        
        return listSuperModel.get(0);
    }
    
    public List<T> query(Class<T> clazzModel, String strSelection, String... strSelectionArgs)
    {
        List<T> listSuperModel = new ArrayList<T>();
        
        try
        {
            T superModel = clazzModel.newInstance();
            
            SQLiteDatabase db = getReadableDatabase();
    
            Cursor cursor = db
                    .query(superModel.getTableName(), null, strSelection, strSelectionArgs, null, null, SuperModel.CREATE_TIME, "20");
            
            cursor.moveToFirst();
            
            while(!cursor.isAfterLast())
            {
                superModel = clazzModel.newInstance();
                
                String[] strPropNames = superModel.getPropNames();
                
                for(String strPropName : strPropNames)
                {
                    
                }
                
                listSuperModel.add(superModel);
                
                cursor.moveToNext();
            }
        }
        catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        finally
        {
            close();
        }
        
        return listSuperModel;
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
