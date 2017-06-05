package cn.studyjams.s2.sj0225.rocex.bodydata.dummy;

import android.text.format.DateFormat;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class BodyDataContent
{
    /**
     * An array of sample (dummy) items.
     */
    public static final List<BodyData> ITEMS = new ArrayList<BodyData>();
    
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, BodyData> ITEM_MAP = new HashMap<String, BodyData>();
    
    static
    {
        addItem(createDummyItem(178, 64.5));
        addItem(createDummyItem(178, 65.6));
        addItem(createDummyItem(178, 66.7));
        addItem(createDummyItem(178, 68.3));
    }
    
    private static void addItem(BodyData item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    
    private static BodyData createDummyItem(double stature, double weight)
    {
        return new BodyData(stature, weight);
    }
    
    /**
     * A dummy item representing a piece of content.
     */
    public static class BodyData
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
}
