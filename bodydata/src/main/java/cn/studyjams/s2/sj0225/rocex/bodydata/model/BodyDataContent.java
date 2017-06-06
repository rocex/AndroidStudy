package cn.studyjams.s2.sj0225.rocex.bodydata.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class BodyDataContent
{
    /**
     * An array of sample (model) items.
     */
    public static final List<BodyData> ITEMS = new ArrayList<BodyData>();
    
    /**
     * A map of sample (model) items, by ID.
     */
    public static final Map<String, BodyData> ITEM_MAP = new HashMap<String, BodyData>();
    
    static
    {
        addItem(createBodyData(181, 64.5));
        addItem(createBodyData(182, 65.6));
        addItem(createBodyData(183, 66.7));
        addItem(createBodyData(184, 68.3));
    }
    
    private static void addItem(BodyData bodyData)
    {
        ITEMS.add(bodyData);
        ITEM_MAP.put(bodyData.id, bodyData);
    }
    
    private static BodyData createBodyData(double height, double weight)
    {
        return new BodyData(height, weight);
    }
}
