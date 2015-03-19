/*
 * Mmtrix APP MONITOR SDK, MARK 0331
 * */

package miles.harvest.type;

import android.util.Log;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;


public class BaseHarvestable
    implements Harvestable
{

    public BaseHarvestable(Harvestable.Type type)
    {
    		//Log.d("myLog","BaseHarvestable::BaseHarvestable >> ");
        this.type = type;
        //Log.d("myLog","BaseHarvestable::BaseHarvestable << ");
    }

    public JsonElement asJson()
    {
        switch(type) {
            case OBJECT: // '\001'
            return asJsonObject();

            case ARRAY: // '\002'
                return asJsonArray();

            case VALUE: // '\003'
                return asJsonPrimitive(); 
        }

        return null;
    }

    public Harvestable.Type getType()
    {
        return type;
    }

    public String toJsonString()
    {
        return asJson().toString();
    }

    public JsonArray asJsonArray()
    {
        return null;
    }

    public JsonObject asJsonObject()
    {
        return null;
    }

    public JsonPrimitive asJsonPrimitive()
    {
        return null;
    }

    protected void notEmpty(String argument)
    {
        if(argument == null || argument.length() == 0)
            throw new IllegalArgumentException("Missing Harvestable field.");
        else
            return;
    }

    protected void notNull(Object argument)
    {
        if(argument == null)
            throw new IllegalArgumentException("Null field in Harvestable object");
        else
            return;
    }

    protected String optional(String argument)
    {
        if(argument == null)
            return "";
        else
            return argument;
    }

    private final Harvestable.Type type;
    //MARK is right?
    protected static final java.lang.reflect.Type GSON_STRING_MAP_TYPE = (new TypeToken<Map<String, String>>() {}).getType();

}

