package miles.crashes;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import miles.harvest.type.HarvestableObject;
import miles.info.ApplicationInfo;
import miles.info.DeviceInfo;
import miles.info.ExceptionInfo;
import miles.info.ThreadInfo;
import miles.util.Util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Crash extends HarvestableObject
{

    public Crash(UUID uuid, String buildId, long timestamp)
    {
        this.uuid = uuid;
        this.buildId = buildId;
        this.timestamp = timestamp;
    }

    public Crash(Throwable throwable)
    {
        Throwable cause = getRootCause(throwable);
        uuid = new UUID(Util.getRandom().nextLong(), Util.getRandom().nextLong());
        buildId = getBuildId();
        timestamp = System.currentTimeMillis() / 1000L;
        deviceInfo = new DeviceInfo(CrashReporter.getDeviceInformation(), CrashReporter.getEnvironmentInformation());
        applicationInfo = new ApplicationInfo(CrashReporter.getApplicationInformation());
        exceptionInfo = new ExceptionInfo(cause);
        threads = ThreadInfo.extractThreads(cause);
    }

    public static String getBuildId()
    {
        return "";
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public ExceptionInfo getExceptionInfo()
    {
        return exceptionInfo;
    }

    
    public JsonObject asJsonObject()
    {
        JsonObject data = new JsonObject();
        data.add("protocolVersion", new JsonPrimitive(Integer.valueOf(1)));
        data.add("platform", new JsonPrimitive("Android"));
        data.add("uuid", new JsonPrimitive(uuid.toString()));
        data.add("buildId", new JsonPrimitive(buildId));
        data.add("timestamp", new JsonPrimitive(Long.valueOf(timestamp)));
        data.add("deviceInfo", deviceInfo.asJsonObject());
        data.add("appInfo", applicationInfo.asJsonObject());
        data.add("exception", exceptionInfo.asJsonObject());
        data.add("threads", getThreadsAsJson());
        return data;
    }

    public static Crash crashFromJsonString(String json)
    {
        JsonElement element = (new JsonParser()).parse(json);
        JsonObject crashObject = element.getAsJsonObject();
        String uuid = crashObject.get("uuid").getAsString();
        String buildIdentifier = crashObject.get("buildId").getAsString();
        long timestamp = crashObject.get("timestamp").getAsLong();
        Crash crash = new Crash(UUID.fromString(uuid), buildIdentifier, timestamp);
        crash.deviceInfo = DeviceInfo.newFromJson(crashObject.get("deviceInfo").getAsJsonObject());
        crash.applicationInfo = ApplicationInfo.newFromJson(crashObject.get("appInfo").getAsJsonObject());
        crash.exceptionInfo = ExceptionInfo.newFromJson(crashObject.get("exception").getAsJsonObject());
        crash.threads = ThreadInfo.newListFromJson(crashObject.get("threads").getAsJsonArray());
        return crash;
    }

    private static Throwable getRootCause(Throwable throwable)
    {
        Throwable cause = throwable.getCause();
        if(cause == null)
            return throwable;
        else
            return getRootCause(cause);
    }

    private JsonArray getThreadsAsJson()
    {
        JsonArray data = new JsonArray();
        ThreadInfo thread;
        for(Iterator i$ = threads.iterator(); i$.hasNext(); data.add(thread.asJsonObject()))
            thread = (ThreadInfo)i$.next();

        return data;
    }

    public static final int PROTOCOL_VERSION = 1;
    private final UUID uuid;
    private final String buildId;
    private final long timestamp;
    private DeviceInfo deviceInfo;
    private ApplicationInfo applicationInfo;
    private ExceptionInfo exceptionInfo;
    private List threads;
}
