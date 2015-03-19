package miles.info;

import java.util.Iterator;

import miles.harvest.type.HarvestableObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class DeviceInfo extends HarvestableObject
{

    public DeviceInfo()
    {
    }

    public DeviceInfo(DeviceInformation devInfo, EnvironmentInformation envInfo)
    {
        memoryUsage = envInfo.getMemoryUsage();
        orientation = envInfo.getOrientation();
        networkStatus = envInfo.getNetworkStatus();
        diskAvailable = envInfo.getDiskAvailable();
        OSVersion = devInfo.getOsVersion();
        deviceName = devInfo.getManufacturer();
        OSBuild = devInfo.getOsBuild();
        architecture = devInfo.getArchitecture();
        modelNumber = devInfo.getModel();
        screenResolution = devInfo.getSize();
        deviceUuid = devInfo.getDeviceId();
        runTime = devInfo.getRunTime();
    }

    public JsonObject asJsonObject()
    {
        JsonObject data = new JsonObject();
        data.add("memoryUsage", new JsonPrimitive(Long.valueOf(memoryUsage)));
        data.add("orientation", new JsonPrimitive(Integer.valueOf(orientation)));
        data.add("networkStatus", new JsonPrimitive(networkStatus));
        data.add("diskAvailable", getDiskAvailableAsJson());
        data.add("osVersion", new JsonPrimitive(OSVersion));
        data.add("deviceName", new JsonPrimitive(deviceName));
        data.add("osBuild", new JsonPrimitive(OSBuild));
        data.add("architecture", new JsonPrimitive(architecture));
        data.add("runTime", new JsonPrimitive(runTime));
        data.add("modelNumber", new JsonPrimitive(modelNumber));
        data.add("screenResolution", new JsonPrimitive(screenResolution));
        data.add("deviceUuid", new JsonPrimitive(deviceUuid));
        return data;
    }

    public static DeviceInfo newFromJson(JsonObject jsonObject)
    {
        DeviceInfo info = new DeviceInfo();
        info.memoryUsage = jsonObject.get("memoryUsage").getAsLong();
        info.orientation = jsonObject.get("orientation").getAsInt();
        info.networkStatus = jsonObject.get("networkStatus").getAsString();
        info.diskAvailable = longArrayFromJsonArray(jsonObject.get("diskAvailable").getAsJsonArray());
        info.OSVersion = jsonObject.get("osVersion").getAsString();
        info.deviceName = jsonObject.get("deviceName").getAsString();
        info.OSBuild = jsonObject.get("osBuild").getAsString();
        info.architecture = jsonObject.get("architecture").getAsString();
        info.runTime = jsonObject.get("runTime").getAsString();
        info.modelNumber = jsonObject.get("modelNumber").getAsString();
        info.screenResolution = jsonObject.get("screenResolution").getAsString();
        info.deviceUuid = jsonObject.get("deviceUuid").getAsString();
        return info;
    }

    private static long[] longArrayFromJsonArray(JsonArray jsonArray)
    {
        long array[] = new long[jsonArray.size()];
        int i = 0;
        for(Iterator i$ = jsonArray.iterator(); i$.hasNext();)
        {
            JsonElement jsonElement = (JsonElement)i$.next();
            array[i++] = jsonElement.getAsLong();
        }

        return array;
    }

    private JsonArray getDiskAvailableAsJson()
    {
        JsonArray data = new JsonArray();
        long arr$[] = diskAvailable;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            long value = arr$[i$];
            data.add(new JsonPrimitive(Long.valueOf(value)));
        }

        return data;
    }

    private long memoryUsage;
    private int orientation;
    private String networkStatus;
    private long diskAvailable[];
    private String OSVersion;
    private String deviceName;
    private String OSBuild;
    private String architecture;
    private String modelNumber;
    private String screenResolution;
    private String deviceUuid;
    private String runTime;
}
