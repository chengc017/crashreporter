/*
 * Mmtrix APP MONITOR, MARK 0408
 */
package miles.harvest.type;

import com.google.gson.JsonPrimitive;



public class HarvestableDouble extends HarvestableValue
{

    public HarvestableDouble()
    {
    }

    public HarvestableDouble(double value)
    {
        this();
        this.value = value;
    }

    public JsonPrimitive asJsonPrimitive()
    {
        return new JsonPrimitive(Double.valueOf(value));
    }

    private double value;
}

