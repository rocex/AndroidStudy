package org.rocex.bodydata.model;

import android.text.format.DateFormat;

import org.rocex.model.SuperModel;

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
    
    private Double height;  // 身高cm
    private Double weight; // 体重kg
    private Double bmi; // 体质指数BMI = weight / (height / 100 * height / 100)
    
    public BodyData()
    {
        super();
        
        dateString = DateFormat.format("yyyy-MM-dd HH:mm:ss", getCreate_time()).toString();
    }
    
    public BodyData(Double height, Double weight)
    {
        this();
        
        this.weight = weight;
        this.height = height;
        
        calculate();
    }
    
    public Double getWeight()
    {
        return weight;
    }
    
    public void setWeight(Double weight)
    {
        this.weight = weight;
    }
    
    public Double getBmi()
    {
        return bmi;
    }
    
    public void setBmi(Double bmi)
    {
        this.bmi = bmi;
    }
    
    public Double getHeight()
    {
        return height;
    }
    
    public void setHeight(Double height)
    {
        this.height = height;
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
