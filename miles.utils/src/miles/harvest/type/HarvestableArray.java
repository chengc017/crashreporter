/*
 * Mmtrix APP MONITOR SDK, MARK 0401 */
 
package miles.harvest.type;

import android.util.Log;

import com.google.gson.JsonArray;

public abstract class HarvestableArray extends BaseHarvestable
{

    public HarvestableArray()
    {
    		
        super(Harvestable.Type.ARRAY);
        //Log.d("myLog","HarvestableArray::HarvestableArray << ");
    }

    public abstract JsonArray asJsonArray();
}

