package miles.info;

import miles.harvest.type.HarvestableArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;


public class ApplicationInformation extends HarvestableArray
{

    public ApplicationInformation()
    {
    }

    public ApplicationInformation(String appName, String appVersion, String packageId)
    {
        this();
        this.appName = appName;
        this.appVersion = appVersion;
        this.packageId = packageId;
    }

    public JsonArray asJsonArray()
    {
        JsonArray array = new JsonArray();
        notEmpty(appName);
        array.add(new JsonPrimitive(appName));
        notEmpty(appVersion);
        array.add(new JsonPrimitive(appVersion));
        notEmpty(packageId);
        array.add(new JsonPrimitive(packageId));
        return array;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppVersion(String appVersion)
    {
        this.appVersion = appVersion;
    }

    public String getAppVersion()
    {
        return appVersion;
    }

    public void setPackageId(String packageId)
    {
        this.packageId = packageId;
    }

    public String getPackageId()
    {
        return packageId;
    }

    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        ApplicationInformation that = (ApplicationInformation)o;
        if(appName == null ? that.appName != null : !appName.equals(that.appName))
            return false;
        if(appVersion == null ? that.appVersion != null : !appVersion.equals(that.appVersion))
            return false;
        return packageId == null ? that.packageId == null : packageId.equals(that.packageId);
    }

    public int hashCode()
    {
        int result = appName == null ? 0 : appName.hashCode();
        result = 31 * result + (appVersion == null ? 0 : appVersion.hashCode());
        result = 31 * result + (packageId == null ? 0 : packageId.hashCode());
        return result;
    }

    private String appName;
    private String appVersion;
    private String packageId;
}
