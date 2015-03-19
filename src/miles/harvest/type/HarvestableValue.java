/*
 * Mmtrix APP MONITOR, MARK 0408
 */
package miles.harvest.type;

import com.google.gson.JsonPrimitive;

public abstract class HarvestableValue extends BaseHarvestable
{

    public HarvestableValue()
    {
        super(Harvestable.Type.VALUE);
    }

    public abstract JsonPrimitive asJsonPrimitive();
}
