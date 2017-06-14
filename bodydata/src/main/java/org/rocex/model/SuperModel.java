package org.rocex.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    
    private static Map<String, String[]> mapField = new HashMap<String, String[]>();
    
    static
    {
        Field[] fields = SuperModel.class.getFields();
        Field[] declaredFields = SuperModel.class.getDeclaredFields();
        
        System.out.println(fields);
        System.out.println(declaredFields);
    }
    
    private Long create_time;
    private Long id;
    private Long ts;
    
    public SuperModel()
    {
        super();
        
        create_time = System.currentTimeMillis();
        ts = create_time;
    }
    
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
    
    public String[] getPropNames()
    {
        String strPropNames[] = mapField.get(getClass().getName());
        
        if(strPropNames == null)
        {
            List<String> listPropGetSet = new ArrayList<>();
            List<String> listGetSet = new ArrayList<>();
            List<Method> listAllMethod = new ArrayList<>();
            
            for(Class clazz = getClass(); clazz.getSuperclass() instanceof Object; clazz = clazz.getSuperclass())
            {
                listAllMethod.addAll(Arrays.asList(clazz.getMethods()));
            }
            
            for(Method method : listAllMethod)
            {
                String strMethodName = method.getName();
                
                if(strMethodName.length() < 4)
                {
                    continue;
                }
                
                if(strMethodName.startsWith("get") || strMethodName.startsWith("set"))
                {
                    listGetSet.add(strMethodName);
                    
                    String _substring = strMethodName.substring(3);
                    
                    if(!listPropGetSet.contains(_substring))
                    {
                        listPropGetSet.add(_substring);
                    }
                }
            }
            
            List<String> listSet = new ArrayList<>();
            
            for(String strPropGetSet : listPropGetSet)
            {
                if(listGetSet.contains("get" + strPropGetSet) && listGetSet.contains("set" + strPropGetSet))
                {
                    String strPropName = strPropGetSet.substring(0, 1).toLowerCase() + strPropGetSet.substring(1);
                    
                    if(listSet.contains(strPropName))
                    {
                        continue;
                    }
                    
                    listSet.add(strPropName);
                }
            }
            
            strPropNames = listSet.toArray(new String[0]);
            
            mapField.put(getClass().getName(), strPropNames);
        }
        
        return strPropNames;
    }
    
    public abstract String getTableName();
    
    public Long getTs()
    {
        return ts;
    }
    
    public void setTs(Long ts)
    {
        this.ts = ts;
    }
}
