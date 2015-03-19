package miles.info;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import miles.harvest.type.HarvestableObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ThreadInfo extends HarvestableObject
{

    private ThreadInfo()
    {
    }

    public ThreadInfo(Throwable throwable)
    {
        crashed = true;
        threadId = Thread.currentThread().getId();
        threadName = Thread.currentThread().getName();
        threadPriority = Thread.currentThread().getPriority();
        stackTrace = throwable.getStackTrace();
        state = Thread.currentThread().getState().toString();
    }

    public ThreadInfo(Thread thread, StackTraceElement stackTrace[])
    {
        crashed = false;
        threadId = thread.getId();
        threadName = thread.getName();
        threadPriority = thread.getPriority();
        this.stackTrace = stackTrace;
        state = thread.getState().toString();
    }

    public long getThreadId()
    {
        return threadId;
    }

    public static List extractThreads(Throwable throwable)
    {
        List threads = new ArrayList();
        ThreadInfo crashedThread = new ThreadInfo(throwable);
        long crashedThreadId = crashedThread.getThreadId();
        threads.add(crashedThread);
        Iterator i$ = Thread.getAllStackTraces().entrySet().iterator();
        do
        {
            if(!i$.hasNext())
                break;
            java.util.Map.Entry threadEntry = (java.util.Map.Entry)i$.next();
            Thread thread = (Thread)threadEntry.getKey();
            StackTraceElement threadStackTrace[] = (StackTraceElement[])threadEntry.getValue();
            if(thread.getId() != crashedThreadId)
                threads.add(new ThreadInfo(thread, threadStackTrace));
        } while(true);
        return threads;
    }

    public JsonObject asJsonObject()
    {
        JsonObject data = new JsonObject();
        data.add("crashed", new JsonPrimitive(Boolean.valueOf(crashed)));
        data.add("state", new JsonPrimitive(state));
        data.add("threadNumber", new JsonPrimitive(Long.valueOf(threadId)));
        data.add("threadId", new JsonPrimitive(threadName));
        data.add("priority", new JsonPrimitive(Integer.valueOf(threadPriority)));
        data.add("stack", getStackAsJson());
        return data;
    }

    public static ThreadInfo newFromJson(JsonObject jsonObject)
    {
        ThreadInfo info = new ThreadInfo();
        info.crashed = jsonObject.get("crashed").getAsBoolean();
        info.state = jsonObject.get("state").getAsString();
        info.threadId = jsonObject.get("threadNumber").getAsLong();
        info.threadName = jsonObject.get("threadId").getAsString();
        info.threadPriority = jsonObject.get("priority").getAsInt();
        info.stackTrace = stackTraceFromJson(jsonObject.get("stack").getAsJsonArray());
        return info;
    }

    public static StackTraceElement[] stackTraceFromJson(JsonArray jsonArray)
    {
        StackTraceElement stack[] = new StackTraceElement[jsonArray.size()];
        int i = 0;
        for(Iterator i$ = jsonArray.iterator(); i$.hasNext();)
        {
            JsonElement jsonElement = (JsonElement)i$.next();
            String fileName = "unknown";
            if(jsonElement.getAsJsonObject().get("fileName") != null)
                fileName = jsonElement.getAsJsonObject().get("fileName").getAsString();
            String className = jsonElement.getAsJsonObject().get("className").getAsString();
            String methodName = jsonElement.getAsJsonObject().get("methodName").getAsString();
            int lineNumber = jsonElement.getAsJsonObject().get("lineNumber").getAsInt();
            StackTraceElement stackTraceElement = new StackTraceElement(className, methodName, fileName, lineNumber);
            stack[i++] = stackTraceElement;
        }

        return stack;
    }

    public static List newListFromJson(JsonArray jsonArray)
    {
        List list = new ArrayList();
        JsonElement jsonElement;
        for(Iterator i$ = jsonArray.iterator(); i$.hasNext(); list.add(newFromJson(jsonElement.getAsJsonObject())))
            jsonElement = (JsonElement)i$.next();

        return list;
    }

    private JsonArray getStackAsJson()
    {
        JsonArray data = new JsonArray();
        StackTraceElement arr$[] = stackTrace;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            StackTraceElement element = arr$[i$];
            JsonObject elementJson = new JsonObject();
            if(element.getFileName() != null)
                elementJson.add("fileName", new JsonPrimitive(element.getFileName()));
            elementJson.add("className", new JsonPrimitive(element.getClassName()));
            elementJson.add("methodName", new JsonPrimitive(element.getMethodName()));
            elementJson.add("lineNumber", new JsonPrimitive(Integer.valueOf(element.getLineNumber())));
            data.add(elementJson);
        }

        return data;
    }

    private boolean crashed;
    private long threadId;
    private String threadName;
    private int threadPriority;
    private StackTraceElement stackTrace[];
    private String state;
}
