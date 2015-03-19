package miles.info;

import miles.harvest.type.HarvestableArray;
import miles.logging.AgentLog;
import miles.logging.AgentLogManager;

import com.google.gson.JsonArray;


public class ConnectInformation extends HarvestableArray
{

    public ConnectInformation(ApplicationInformation applicationInformation, DeviceInformation deviceInformation)
    {
        if(null == applicationInformation)
            log.error("null applicationInformation passed into ConnectInformation constructor");
        if(null == deviceInformation)
            log.error("null deviceInformation passed into ConnectInformation constructor");
        this.applicationInformation = applicationInformation;
        this.deviceInformation = deviceInformation;
    }

    public JsonArray asJsonArray()
    {
        JsonArray array = new JsonArray();
        notNull(applicationInformation);
        array.add(applicationInformation.asJsonArray());
        notNull(deviceInformation);
        array.add(deviceInformation.asJsonArray());
        return array;
    }

    public ApplicationInformation getApplicationInformation()
    {
        return applicationInformation;
    }

    public DeviceInformation getDeviceInformation()
    {
        return deviceInformation;
    }

    public void setApplicationInformation(ApplicationInformation applicationInformation)
    {
        this.applicationInformation = applicationInformation;
    }

    public void setDeviceInformation(DeviceInformation deviceInformation)
    {
        this.deviceInformation = deviceInformation;
    }

    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        ConnectInformation that = (ConnectInformation)o;
        if(applicationInformation == null ? that.applicationInformation != null : !applicationInformation.equals(that.applicationInformation))
            return false;
        return deviceInformation == null ? that.deviceInformation == null : deviceInformation.equals(that.deviceInformation);
    }

    public int hashCode()
    {
        int result = applicationInformation == null ? 0 : applicationInformation.hashCode();
        result = 31 * result + (deviceInformation == null ? 0 : deviceInformation.hashCode());
        return result;
    }

    private static final AgentLog log = AgentLogManager.getAgentLog();
    private ApplicationInformation applicationInformation;
    private DeviceInformation deviceInformation;

}
