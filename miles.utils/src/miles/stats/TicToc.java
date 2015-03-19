/*
 * 
 * Mmtrix APP MONITOR, MARK 0402
 */
package miles.stats;


public class TicToc
{
	private static enum State{
		STOPPED,
		STARTED;
	}
  
    public TicToc()
    {
    }

    public void tic()
    {
        state = State.STARTED;
        startTime = System.currentTimeMillis();
    }

    public long toc()
    {
        endTime = System.currentTimeMillis();
        if(state == State.STARTED)
        {
            state = State.STOPPED;
            return endTime - startTime;
        } else
        {
            return -1L;
        }
    }

    private long startTime;
    private long endTime;
    private State state;
}


