package cn.studyjams.s2.sj0225.rocex.bodydata.model;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import org.rocex.db.DBHelper;

/**
 * Created by rocexwang on 2017-6-6 21:37:01
 */
public class BodyDataDBHelper<T extends BodyData> extends DBHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bodydata.db";
    
    private static String strCreateTableSQL = "create table bodydata (id integer primary key autoincrement, weight real, height real, bmi real, create_time long, ts long);";
    
    public BodyDataDBHelper(Context context)
    {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public BodyDataDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        this(context, name, factory, version, null);
    }
    
    public BodyDataDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        if(!existTable("bodydata"))
        {
            db.execSQL(strCreateTableSQL);
        }
    }
}
