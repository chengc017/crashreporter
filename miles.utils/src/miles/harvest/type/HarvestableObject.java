/*
 * Mmtrix APP MONITOR SDK, MARK 0331
 * */

package miles.harvest.type;



import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

public abstract class HarvestableObject extends BaseHarvestable
{

    public static HarvestableObject fromMap(final Map map)
    {
        return new HarvestableObject() {

            public JsonObject asJsonObject()
            {
                return (JsonObject)(new Gson()).toJsonTree(map, GSON_STRING_MAP_TYPE);
            }

            /*final Map val$map;

            
            {
                map = map1;
                super();
            }*/
        }
;
    }

    public HarvestableObject()
    {
        super(Harvestable.Type.OBJECT);
    }

    public abstract JsonObject asJsonObject();
}

