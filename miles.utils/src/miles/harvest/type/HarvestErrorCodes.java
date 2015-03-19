package miles.harvest.type;

public interface HarvestErrorCodes
{

    public static final int NSURLErrorUnknown = -1;
    public static final int NSURLErrorBadURL = -1000;
    public static final int NSURLErrorTimedOut = -1001;
    public static final int NSURLErrorCannotConnectToHost = -1004;
    public static final int NSURLErrorDNSLookupFailed = -1006;
    public static final int NSURLErrorBadServerResponse = -1011;
    public static final int NSURLErrorSecureConnectionFailed = -1200;
    public static final int NSURLErrorIOException = -1300;
    public static final int NSURLErrorRuntimeException = -1301;
}
