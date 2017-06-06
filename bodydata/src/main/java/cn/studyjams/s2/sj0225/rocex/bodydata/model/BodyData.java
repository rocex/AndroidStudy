package cn.studyjams.s2.sj0225.rocex.bodydata.model;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

/**
 * A model item representing a piece of content.
 */
public class BodyData implements Serializable
{
    public final String dateString;
    public final Date date;
    
    public String id;
    public Double height = 1.0;  // 身高cm
    public Double weight = 1.0; // 体重kg
    public Double bmi; // 体质指数BMI = weight / (height / 100 * height / 100)
    
    public BodyData()
    {
        this.id = String.valueOf(System.currentTimeMillis());
        
        date = new Date();
        
        dateString = DateFormat.format("yyyy-MM-dd HH:mm:ss", date).toString();
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
}
