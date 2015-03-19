/*
 * Mmtrix APP MONITOR SDK, MARK 0331
 * */

package miles.logging;

public interface AgentLog
{

    public abstract void debug(String s);

    public abstract void verbose(String s);

    public abstract void info(String s);

    public abstract void warning(String s);

    public abstract void error(String s);

    public abstract void error(String s, Throwable throwable);

    public abstract int getLevel();

    public abstract void setLevel(int i);

    public static final int DEBUG = 5;
    public static final int VERBOSE = 4;
    public static final int INFO = 3;
    public static final int WARNING = 2;
    public static final int ERROR = 1;
}
