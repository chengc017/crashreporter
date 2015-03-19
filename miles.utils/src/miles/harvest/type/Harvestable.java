/*
 * Mmtrix APP MONITOR SDK, MARK 0331
 * */


package miles.harvest.type;

import com.google.gson.*;

public interface Harvestable
{
	public static enum Type {
		OBJECT,
        ARRAY,
        VALUE
	}
  


    public abstract Type getType();

    public abstract JsonElement asJson();

    public abstract JsonObject asJsonObject();

    public abstract JsonArray asJsonArray();

    public abstract JsonPrimitive asJsonPrimitive();

    public abstract String toJsonString();
}

