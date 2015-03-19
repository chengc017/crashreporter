package miles.info;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import miles.harvest.type.HarvestableArray;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class DeviceInformation extends HarvestableArray
{

    public DeviceInformation()
    {
        misc = new HashMap();
    }

    public JsonArray asJsonArray()
    {
        JsonArray array = new JsonArray();
        notEmpty(osName);
        array.add(new JsonPrimitive(osName));
        notEmpty(osVersion);
        array.add(new JsonPrimitive(osVersion));
        notEmpty(manufacturer);
        notEmpty(model);
        array.add(new JsonPrimitive((new StringBuilder()).append(manufacturer).append(" ").append(model).toString()));
        notEmpty(agentName);
        array.add(new JsonPrimitive(agentName));
        notEmpty(agentVersion);
        array.add(new JsonPrimitive(agentVersion));
        notEmpty(deviceId);
        array.add(new JsonPrimitive(deviceId));
        array.add(new JsonPrimitive(optional(countryCode)));
        array.add(new JsonPrimitive(optional(regionCode)));
        array.add(new JsonPrimitive(manufacturer));
        if(misc == null || misc.isEmpty())
            misc = Collections.emptyMap();
        JsonElement map = (new Gson()).toJsonTree(misc, GSON_STRING_MAP_TYPE);
        array.add(map);
        return array;
    }

    public void setOsName(String osName)
    {
        this.osName = osName;
    }

    public void setOsVersion(String osVersion)
    {
        this.osVersion = osVersion;
    }

    public void setOsBuild(String osBuild)
    {
        this.osBuild = osBuild;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }

    public void setAgentName(String agentName)
    {
        this.agentName = agentName;
    }

    public void setAgentVersion(String agentVersion)
    {
        this.agentVersion = agentVersion;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public void setArchitecture(String architecture)
    {
        this.architecture = architecture;
    }

    public void setRunTime(String runTime)
    {
        this.runTime = runTime;
    }

    public void setSize(String size)
    {
        this.size = size;
        addMisc("size", size);
    }

    public void setMisc(Map misc)
    {
        this.misc = new HashMap(misc);
    }

    public void addMisc(String key, String value)
    {
        misc.put(key, value);
    }

    public String getOsName()
    {
        return osName;
    }

    public String getOsVersion()
    {
        return osVersion;
    }

    public String getOsBuild()
    {
        return osBuild;
    }

    public String getModel()
    {
        return model;
    }

    public String getAgentName()
    {
        return agentName;
    }

    public String getAgentVersion()
    {
        return agentVersion;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public String getRegionCode()
    {
        return regionCode;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public String getArchitecture()
    {
        return architecture;
    }

    public String getRunTime()
    {
        return runTime;
    }

    public String getSize()
    {
        return size;
    }

    public String toJsonString()
    {
        return (new StringBuilder()).append("DeviceInformation{manufacturer='").append(manufacturer).append('\'').append(", osName='").append(osName).append('\'').append(", osVersion='").append(osVersion).append('\'').append(", model='").append(model).append('\'').append(", agentName='").append(agentName).append('\'').append(", agentVersion='").append(agentVersion).append('\'').append(", deviceId='").append(deviceId).append('\'').append(", countryCode='").append(countryCode).append('\'').append(", regionCode='").append(regionCode).append('\'').append('}').toString();
    }

    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        DeviceInformation that = (DeviceInformation)o;
        if(agentName == null ? that.agentName != null : !agentName.equals(that.agentName))
            return false;
        if(agentVersion == null ? that.agentVersion != null : !agentVersion.equals(that.agentVersion))
            return false;
        if(architecture == null ? that.architecture != null : !architecture.equals(that.architecture))
            return false;
        if(deviceId == null ? that.deviceId != null : !deviceId.equals(that.deviceId))
            return false;
        if(manufacturer == null ? that.manufacturer != null : !manufacturer.equals(that.manufacturer))
            return false;
        if(model == null ? that.model != null : !model.equals(that.model))
            return false;
        if(osBuild == null ? that.osBuild != null : !osBuild.equals(that.osBuild))
            return false;
        if(osName == null ? that.osName != null : !osName.equals(that.osName))
            return false;
        if(osVersion == null ? that.osVersion != null : !osVersion.equals(that.osVersion))
            return false;
        if(runTime == null ? that.runTime != null : !runTime.equals(that.runTime))
            return false;
        return size == null ? that.size == null : size.equals(that.size);
    }

    public int hashCode()
    {
        int result = osName == null ? 0 : osName.hashCode();
        result = 31 * result + (osVersion == null ? 0 : osVersion.hashCode());
        result = 31 * result + (osBuild == null ? 0 : osBuild.hashCode());
        result = 31 * result + (model == null ? 0 : model.hashCode());
        result = 31 * result + (agentName == null ? 0 : agentName.hashCode());
        result = 31 * result + (agentVersion == null ? 0 : agentVersion.hashCode());
        result = 31 * result + (deviceId == null ? 0 : deviceId.hashCode());
        result = 31 * result + (manufacturer == null ? 0 : manufacturer.hashCode());
        result = 31 * result + (architecture == null ? 0 : architecture.hashCode());
        result = 31 * result + (runTime == null ? 0 : runTime.hashCode());
        result = 31 * result + (size == null ? 0 : size.hashCode());
        return result;
    }

    private String osName;
    private String osVersion;
    private String osBuild;
    private String model;
    private String agentName;
    private String agentVersion;
    private String deviceId;
    private String countryCode;
    private String regionCode;
    private String manufacturer;
    private String architecture;
    private String runTime;
    private String size;
    private Map misc;
}
