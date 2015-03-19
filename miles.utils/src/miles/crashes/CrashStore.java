package miles.crashes;

import java.util.List;

public interface CrashStore
{

    public abstract void store(Crash crash);

    public abstract List fetchAll();

    public abstract int count();

    public abstract void clear();

    public abstract void delete(Crash crash);
}
