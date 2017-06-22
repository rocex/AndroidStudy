package org.rocex.model;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***************************************************************************
 * <br>
 * @author Rocex Wang
 * @version 2017-06-07 22:57:01
 ***************************************************************************/
public abstract class SuperModel implements Serializable
{
    public static final String CREATE_TIME = "create_time";
    public static final String ID = "id";
    public static final String TS = "ts";
    
    private static final String TAG = SuperModel.class.getName();
    
    private static Map<String, Class> mapPropType = new HashMap<String, Class>(); // 全路径类名.属性名，属性名类型
    private static Map<String, String[]> mapPropName = new HashMap<String, String[]>();// 全路径类名，属性名数组
    
    private Long create_time;
    private Long id;
    private Long ts;
    
    /***************************************************************************
     * @author Rocex Wang
     * @version 2017-6-15 10:10:36
     ***************************************************************************/
    public SuperModel()
    {
        super();
        
        create_time = System.currentTimeMillis();
        ts = create_time;
    }
    
    /***************************************************************************
     * @return
     * @author Rocex Wang
     * @version 2017-6-15 10:10:34
     ***************************************************************************/
    public Long getCreate_time()
    {
        return create_time;
    }
    
    /***************************************************************************
     * @param create_time
     * @author Rocex Wang
     * @version 2017-6-15 10:10:10
     ***************************************************************************/
    public void setCreate_time(Long create_time)
    {
        this.create_time = create_time;
    }
    
    /***************************************************************************
     * @return
     * @author Rocex Wang
     * @version 2017-6-15 10:10:31
     ***************************************************************************/
    public Long getId()
    {
        return id;
    }
    
    /***************************************************************************
     * @param id
     * @author Rocex Wang
     * @version 2017-6-15 10:10:12
     ***************************************************************************/
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /***************************************************************************
     * @param strPropName
     * @author Rocex Wang
     * @version 2017-6-15 10:10:12
     ***************************************************************************/
    public Class getPropType(String strPropName)
    {
        return mapPropType.get(getClass().getName() + "." + strPropName);
    }
    
    /***************************************************************************
     * @return 属性名数组，必须满足同时有 getter 和 setter 方法
     * @author Rocex Wang
     * @version 2017-6-15 10:10:28
     ***************************************************************************/
    public String[] getPropNames()
    {
        String strPropNames[] = mapPropName.get(getClass().getName());
    
        if(strPropNames != null)
        {
            return strPropNames;
        }
    
        List<String> listField = new ArrayList<>();//
    
        for(Class clazz = getClass(); clazz.getSuperclass() instanceof Object; clazz = clazz.getSuperclass())
        {
            for(Field field : clazz.getDeclaredFields())
            {
                if(Modifier.isStatic(field.getModifiers()))
                {
                    continue;
                }
                
                try
                {
                    clazz.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), (Class[]) null);
                    clazz.getMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), field.getType());
                }
                catch(Exception ex)
                {
                    Log.e(TAG, "getPropNames: name[" + field.getName() + "], type[" + field.getType() + "]", ex);
                    continue;
                }
    
                listField.add(field.getName());
    
                mapPropType.put(getClass().getName() + "." + field.getName(), field.getType());
            }
        }
    
        Log.d(TAG, "getPropNames: fields[" + listField + "]");
        Log.d(TAG, "getPropNames: type[" + mapPropType + "]");
        
        strPropNames = listField.toArray(new String[0]);
    
        Arrays.sort(strPropNames);
    
        mapPropName.put(getClass().getName(), strPropNames);
        
        return strPropNames;
    }
    
    public String getCreateTableSQL(String... strPropNames)
    {
        if(strPropNames == null || strPropNames.length == 0)
        {
            strPropNames = getPropNames();
        }
        
        String strFieldSQL = "";
        
        for(String strPropName : strPropNames)
        {
            strFieldSQL = strFieldSQL + strPropName + " int" + ", ";
        }
        
        String strSQL = "create table " + getTableName() + " (id integer primary key autoincrement," + strFieldSQL + "create_time long, ts long)";
        
        return strSQL;
    }
    
    /***************************************************************************
     * @param strPropName
     * @return
     * @author Rocex Wang
     * @version 2017-6-15 10:10:25
     ***************************************************************************/
    public Object getPropValue(String strPropName)
    {
        Object objReturn = null;
        
        try
        {
            String strMethodName = "get" + strPropName.substring(0, 1).toUpperCase() + strPropName.substring(1);
            Method method = getClass().getMethod(strMethodName, (Class) null);
            
            objReturn = method.invoke(this, (Object[]) null);
        }
        catch(Exception ex)
        {
            Log.e(TAG, "getPropValue: name[" + strPropName + "]", ex);
        }
        
        return objReturn;
    }
    
    /***************************************************************************
     * @return table name
     * @author Rocex Wang
     * @version 2017-6-15 10:10:21
     ***************************************************************************/
    public abstract String getTableName();
    
    /***************************************************************************
     * @return
     * @author Rocex Wang
     * @version 2017-6-15 10:10:05
     ***************************************************************************/
    public Long getTs()
    {
        return ts;
    }
    
    /***************************************************************************
     * @param ts
     * @author Rocex Wang
     * @version 2017-6-15 10:10:14
     ***************************************************************************/
    public void setTs(Long ts)
    {
        this.ts = ts;
    }
    
    /***************************************************************************
     * @param strPropName
     * @param objValue
     * @author Rocex Wang
     * @version 2017-6-15 10:12:11
     ***************************************************************************/
    public void setPropValue(String strPropName, Object objValue)
    {
        try
        {
            String strMethodName = "set" + strPropName.substring(0, 1).toUpperCase() + strPropName.substring(1);
    
            Method method = getClass().getMethod(strMethodName, getPropType(strPropName));
            
            method.invoke(this, objValue);
        }
        catch(Exception ex)
        {
            Log.e(TAG, "setPropValue: name[" + strPropName + "], type[" + getPropType(strPropName) + "], value[" + objValue + "]", ex);
        }
    }
}
