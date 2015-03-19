package miles.info;

import miles.harvest.type.HarvestableObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ApplicationInfo extends HarvestableObject
{

    public ApplicationInfo()
    {
        applicationName = "";
        applicationVersion = "";
        bundleId = "";
        processId = 0;
    }

    public ApplicationInfo(ApplicationInformation applicationInformation)
    {
        applicationName = "";
        applicationVersion = "";
        bundleId = "";
        processId = 0;
        applicationName = applicationInformation.getAppName();
        applicationVersion = applicationInformation.getAppVersion();
        bundleId = applicationInformation.getPackageId();
    }

    public JsonObject asJsonObject()
    {
        JsonObject data = new JsonObject();
        data.add("appName", new JsonPrimitive(applicationName));
        data.add("appVersion", new JsonPrimitive(applicationVersion));
        data.add("bundleId", new JsonPrimitive(bundleId));
        data.add("processId", new JsonPrimitive(Integer.valueOf(processId)));
        return data;
    }

    public static ApplicationInfo newFromJson(JsonObject jsonObject)
    {
        ApplicationInfo info = new ApplicationInfo();
        info.applicationName = jsonObject.get("appName").getAsString();
        info.applicationVersion = jsonObject.get("appVersion").getAsString();
        info.bundleId = jsonObject.get("bundleId").getAsString();
        info.processId = jsonObject.get("processId").getAsInt();
        return info;
    }

    private String applicationName;
    private String applicationVersion;
    private String bundleId;
    private int processId;
}
