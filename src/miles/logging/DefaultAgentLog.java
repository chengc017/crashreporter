/*
 * Mmtrix APP MONITOR SDK, MARK 0331
 * */

package miles.logging;

public class DefaultAgentLog
 implements AgentLog
{

 public DefaultAgentLog()
 {
     impl = new NullAgentLog();
 }

 public void setImpl(AgentLog impl)
 {
     synchronized(this)
     {
         this.impl = impl;
     }
 }

 public void debug(String message)
 {
     synchronized(this)
     {
         impl.debug(message);
     }
 }

 public void info(String message)
 {
     synchronized(this)
     {
         impl.info(message);
     }
 }

 public void verbose(String message)
 {
     synchronized(this)
     {
         impl.verbose(message);
     }
 }

 public void warning(String message)
 {
     synchronized(this)
     {
         impl.warning(message);
     }
 }

 public void error(String message)
 {
     synchronized(this)
     {
         impl.error(message);
     }
 }

 public void error(String message, Throwable cause)
 {
     synchronized(this)
     {
         impl.error(message, cause);
     }
 }

 public int getLevel()
 {
     synchronized(this) {
    	 	return impl.getLevel();
     }
	 
 }

 public void setLevel(int level)
 {
     synchronized(this)
     {
         impl.setLevel(level);
     }
 }

 private AgentLog impl;
}
