/*
 * Mmtrix APP MONITOR, MARK 0408
 */
package miles.harvest.type;

import com.google.gson.JsonPrimitive;

public class HarvestableLong extends HarvestableValue
{

    public HarvestableLong()
    {
    }

    public HarvestableLong(long value)
    {
        this();
        this.value = value;
    }

    public JsonPrimitive asJsonPrimitive()
    {
        return new JsonPrimitive(Long.valueOf(value));
    }

    private long value;
}

