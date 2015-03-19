package miles.info;

import miles.harvest.type.HarvestableObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ExceptionInfo extends HarvestableObject
{

    public ExceptionInfo()
    {
    }

    public ExceptionInfo(Throwable throwable)
    {
        className = throwable.getClass().getName();
        if(throwable.getMessage() != null)
            message = throwable.getMessage();
        else
            message = "";
    }

    public String getClassName()
    {
        return className;
    }

    public String getMessage()
    {
        return message;
    }

    public JsonObject asJsonObject()
    {
        JsonObject data = new JsonObject();
        data.add("name", new JsonPrimitive(className));
        data.add("cause", new JsonPrimitive(message));
        return data;
    }

    public static ExceptionInfo newFromJson(JsonObject jsonObject)
    {
        ExceptionInfo info = new ExceptionInfo();
        info.className = jsonObject.get("name").getAsString();
        info.message = jsonObject.get("cause").getAsString();
        return info;
    }

    private String className;
    private String message;
}