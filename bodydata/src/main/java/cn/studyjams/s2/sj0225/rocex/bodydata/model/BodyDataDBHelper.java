package cn.studyjams.s2.sj0225.rocex.bodydata.model;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017-6-6.
 */
public class BodyDataDBHelper extends SQLiteOpenHelper
{
    private String strCreateDBSQL = "create table bodydata (id integer primary key autoincrement, weight real, height real" +
            ", create_date date, bmi real);";
    
    public BodyDataDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    
    public BodyDataDBHelper(Context context)
    {
        this(context, "bodydata", null, 1);
    }
    
    public BodyDataDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table' and name='bodydata'", null);
        
        if(cursor.getCount() == 0)
        {
            db.execSQL(strCreateDBSQL);
        }
    
        cursor.close();
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        
    }
}
