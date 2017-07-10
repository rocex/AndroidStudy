package org.rocex.model.databinding;

import android.databinding.BindingConversion;

import java.text.SimpleDateFormat;

/**
 * DataBinding的数据转换
 * Created by Rocex Wang on 2017-07-10.
 */
public class DataConverter
{
    @BindingConversion
    public static String convertDate(long date)
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
    @BindingConversion
    public static String convertDateTime(long date)
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
