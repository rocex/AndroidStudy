package cn.studyjams.s2.sj0225.rocex.bodydata.model;

import android.provider.BaseColumns;

/**
 * Created by rocexwang on 2017-06-07 22:57:01
 */
public abstract class SuperModel implements BaseColumns
{
    public static final String CREATE_TIME = "create_time";
    
    private long create_time;
    private long _id;
    
    public SuperModel()
    {
        super();
        
        create_time = System.currentTimeMillis();
    }
    
    public abstract String getTableName();
    
    public long getCreate_time()
    {
        return create_time;
    }
    
    public void setCreate_time(long create_time)
    {
        this.create_time = create_time;
    }
    
    public long getId()
    {
        return _id;
    }
    
    public void setId(long _id)
    {
        this._id = _id;
    }
}
