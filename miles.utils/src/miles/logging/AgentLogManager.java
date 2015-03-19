/*
 * Mmtrix APP MONITOR SDK, MARK 0331
 * */
package miles.logging;

import android.util.Log;

public class AgentLogManager
{

    public AgentLogManager()
    {
    }

    public static AgentLog getAgentLog()
    {
        return instance;
    }

    public static void setAgentLog(AgentLog instance2)
    {
        instance.setImpl(instance2);
        Log.d("myLog","AgentLogManager::setAgentLog>>");
    }

    private static DefaultAgentLog instance = new DefaultAgentLog();

}

