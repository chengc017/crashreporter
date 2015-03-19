package miles.crashes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import miles.logging.AgentLog;
import miles.logging.AgentLogManager;
import android.content.Context;
import android.content.SharedPreferences;

public class JsonCrashStore
	implements CrashStore
	{
	
	public JsonCrashStore(Context context)
	{
	    this.context = context;
	}
	
	public void store(Crash crash)
	{
	    synchronized(this)
	    {
	        SharedPreferences store = context.getSharedPreferences("NRCrashStore", 0);
	        android.content.SharedPreferences.Editor editor = store.edit();
	        editor.putString(crash.getUuid().toString(), crash.toJsonString());
	        editor.commit();
	    }
	}
	
	public List fetchAll()
	{
	    SharedPreferences store = context.getSharedPreferences("NRCrashStore", 0);
	    List crashes = new ArrayList();
	    Map crashStrings;
	    synchronized(this)
	    {
	        crashStrings = store.getAll();
	    }
	    Iterator i$ = crashStrings.values().iterator();
	    do
	    {
	        if(!i$.hasNext())
	            break;
	        Object string = i$.next();
	        if(string instanceof String)
	            try
	            {
	                crashes.add(Crash.crashFromJsonString((String)string));
	            }
	            catch(Exception e)
	            {
	                log.error("Exception encountered while deserializing crash", e);
	            }
	    } while(true);
	    return crashes;
	}
	
	public int count()
	{
	    SharedPreferences store = context.getSharedPreferences("NRCrashStore", 0);
	    return store.getAll().size();
	}
	
	public void clear()
	{
	    synchronized(this)
	    {
	        SharedPreferences store = context.getSharedPreferences("NRCrashStore", 0);
	        android.content.SharedPreferences.Editor editor = store.edit();
	        editor.clear();
	        editor.commit();
	    }
	}
	
	public void delete(Crash crash)
	{
	    synchronized(this)
	    {
	        SharedPreferences store = context.getSharedPreferences("NRCrashStore", 0);
	        android.content.SharedPreferences.Editor editor = store.edit();
	        editor.remove(crash.getUuid().toString());
	        editor.commit();
	    }
	}
	
	private static final String STORE_FILE = "NRCrashStore";
	private static final AgentLog log = AgentLogManager.getAgentLog();
	private final Context context;

}
