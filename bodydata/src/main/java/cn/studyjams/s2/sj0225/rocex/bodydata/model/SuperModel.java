package cn.studyjams.s2.sj0225.rocex.bodydata.model;

import java.io.Serializable;

/**
 * Created by rocexwang on 2017-06-07 22:57:01
 */
public abstract class SuperModel implements Serializable
{
    public static final String ID = "id";
    public static final String TS = "ts";
    public static final String CREATE_TIME = "create_time";
    
    private Long create_time;
    private Long id;
    private Long ts;
    
    public SuperModel()
    {
        super();
        
        create_time = System.currentTimeMillis();
        ts = create_time;
    }
    
    public abstract String getTableName();
    
    public Long getCreate_time()
    {
        return create_time;
    }
    
    public void setCreate_time(Long create_time)
    {
        this.create_time = create_time;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Long getTs()
    {
        return ts;
    }
    
    public void setTs(Long ts)
    {
        this.ts = ts;
    }
}
