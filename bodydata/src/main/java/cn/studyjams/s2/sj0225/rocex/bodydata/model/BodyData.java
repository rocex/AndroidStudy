package cn.studyjams.s2.sj0225.rocex.bodydata.model;

import android.text.format.DateFormat;

import java.text.MessageFormat;

/**
 * A model item representing a piece of content.
 */
public class BodyData extends SuperModel
{
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String BMI = "bmi";
    public static final String TABLE_NAME = "bodydata";
    
    public final String dateString;
    
    public String id;
    public Double height = 1.0;  // 身高cm
    public Double weight = 1.0; // 体重kg
    public Double bmi; // 体质指数BMI = weight / (height / 100 * height / 100)
    
    public BodyData()
    {
        super();
        
        setId(getCreate_time());
        
        dateString = DateFormat.format("yyyy-MM-dd HH:mm:ss", getCreate_time()).toString();
    }
    
    public BodyData(Double height, Double weight)
    {
        this();
        
        this.weight = weight;
        this.height = height;
        
        calculate();
    }
    
    public void calculate()
    {
        if(height != 0)
        {
            this.bmi = weight / (height / 100 * height / 100);
        }
        else
        {
            this.bmi = 0.0;
        }
    }
    
    @Override
    public String toString()
    {
        return MessageFormat.format("{0}cm, {1}kg, BMI {2}", height, weight, bmi);
    }
    
    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }
}
