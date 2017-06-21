package org.rocex.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.rocex.model.SuperModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rocexwang on 2017-06-14 11:23:26
 */
public class DBHelper<T extends SuperModel> extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "bodydata.db";
    public static final int DATABASE_VERSION = 1;
    
    private static final String TAG = "DBHelper";
    
    private static String strCreateTableSQL = "create table bodydata (id integer primary key autoincrement, weight real, height real, bmi" + " real, create_time long, ts long);";
    
    private Context context;
    
    public DBHelper(Context context)
    {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public DBHelper(Context context, String strDatabaseName, SQLiteDatabase.CursorFactory cursorFactory, int iDatabaseVersion)
    {
        this(context, strDatabaseName, cursorFactory, iDatabaseVersion, null);
    }
    
    public DBHelper(Context context, String strDatabaseName, SQLiteDatabase.CursorFactory cursorFactory, int iDatabaseVersion, DatabaseErrorHandler errorHandler)
    {
        super(context, strDatabaseName, cursorFactory, iDatabaseVersion, errorHandler);
    
        this.context = context;
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
    
            Class classPropType = superModel.getPropType(strFieldName);
    
            if(classPropType.getClass().equals(Boolean.class))
            {
                contentValues.put(strFieldName, (Boolean) objPropValue);
            }
            else if(classPropType.getClass().equals(Byte.class))
            {
                contentValues.put(strFieldName, (Byte) objPropValue);
            }
            else if(classPropType.getClass().equals(byte[].class))
            {
                contentValues.put(strFieldName, (byte[]) objPropValue);
            }
            else if(classPropType.getClass().equals(Double.class))
            {
                contentValues.put(strFieldName, (Double) objPropValue);
            }
            else if(classPropType.getClass().equals(Float.class))
            {
                contentValues.put(strFieldName, (Float) objPropValue);
            }
            else if(classPropType.getClass().equals(Integer.class))
            {
                contentValues.put(strFieldName, (Integer) objPropValue);
            }
            else if(classPropType.getClass().equals(Long.class))
            {
                contentValues.put(strFieldName, (Long) objPropValue);
            }
            else if(classPropType.getClass().equals(Short.class))
            {
                contentValues.put(strFieldName, (Short) objPropValue);
            }
            else if(classPropType.getClass().equals(String.class))
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
            Log.e(TAG, "convertToModel: class[" + clazzModel + "]", ex);
    
            return null;
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
    
    public List<Long> insert(T superModels[], String... strPropNames)
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
                ContentValues contentValues = convertToContentValues(superModel, strPropNames);
    
                listId.add(db.insert(superModel.getTableName(), null, contentValues));
            }
        }
        finally
        {
            close();
        }
    
        return listId;
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
    
            String[] strPropNames = superModel.getPropNames();
            
            while(!cursor.isAfterLast())
            {
                superModel = clazzModel.newInstance();
                
                for(String strPropName : strPropNames)
                {
                    int iColumnIndex = cursor.getColumnIndex(strPropName);
    
                    Class classPropType = superModel.getPropType(strPropName);
    
                    if(classPropType.getClass().equals(Boolean.class))
                    {
                        superModel.setPropValue(strPropName, Boolean.valueOf(cursor.getString(iColumnIndex)));
                    }
                    else if(classPropType.getClass().equals(byte[].class))
                    {
                        superModel.setPropValue(strPropName, cursor.getBlob(iColumnIndex));
                    }
                    else if(classPropType.getClass().equals(Double.class))
                    {
                        superModel.setPropValue(strPropName, cursor.getDouble(iColumnIndex));
                    }
                    else if(classPropType.getClass().equals(Float.class))
                    {
                        superModel.setPropValue(strPropName, cursor.getFloat(iColumnIndex));
                    }
                    else if(classPropType.getClass().equals(Integer.class))
                    {
                        superModel.setPropValue(strPropName, cursor.getInt(iColumnIndex));
                    }
                    else if(classPropType.getClass().equals(Long.class))
                    {
                        superModel.setPropValue(strPropName, cursor.getLong(iColumnIndex));
                    }
                    else if(classPropType.getClass().equals(Short.class))
                    {
                        superModel.setPropValue(strPropName, cursor.getShort(iColumnIndex));
                    }
                    else if(classPropType.getClass().equals(String.class))
                    {
                        superModel.setPropValue(strPropName, cursor.getString(iColumnIndex));
                    }
                }
                
                listSuperModel.add(superModel);
                
                cursor.moveToNext();
            }
        }
        catch(Exception ex)
        {
            Log.e(TAG, "query: class[" + clazzModel + "]", ex);
        }
        finally
        {
            close();
        }
        
        return listSuperModel;
    }
    
    public T queryById(Class<T> clazzModel, long id)
    {
        List<T> listSuperModel = query(clazzModel, SuperModel.ID + "=?", String.valueOf(id));
    
        return listSuperModel.get(0);
    }
    
    public int update(T superModels[], String... strPropNames)
    {
        if(superModels == null || superModels.length == 0)
        {
            return 0;
        }
        
        int iCount = 0;
        
        SQLiteDatabase db = getWritableDatabase();
        
        try
        {
            for(T superModel : superModels)
            {
                ContentValues contentValues = convertToContentValues(superModel);
    
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
}
