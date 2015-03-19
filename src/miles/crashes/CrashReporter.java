package miles.crashes;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import miles.info.ApplicationInformation;
import miles.info.DeviceInformation;
import miles.info.EnvironmentInformation;
import miles.logging.AgentLog;
import miles.logging.AgentLogManager;
import miles.sample.Sample;
import miles.stats.TicToc;
import miles.util.Connectivity;
import miles.util.DeviceForm;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

public class CrashReporter
{
    private class CrashSender implements Runnable
    {

        public void run()
        {
            HttpURLConnection connection = null;
            String protocol = CrashReporter.isuseSSL ? "https://" : "http://";
            String urlString = (new StringBuilder()).append(protocol).append(CrashReporter.host).append("/mobile_crash").toString();
            URL url;
			try {
				url = new URL(urlString);
				connection = (HttpURLConnection)url.openConnection();
	            connection.setDoOutput(true);
	            connection.setChunkedStreamingMode(0);
	            connection.setRequestProperty("Content-Type", "application/json");
	            connection.setConnectTimeout(5000);
	            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
	            out.write(crash.toJsonString().getBytes());
	            out.close();
	            if(connection.getResponseCode() == 200)
	            {
	                log.info((new StringBuilder()).append("Crash ").append(crash.getUuid().toString()).append(" successfully submitted.").toString());
	                crashStore.delete(crash);
	            } else
	            {
	                log.error((new StringBuilder()).append("Something went wrong while submitting a crash (will try again later) - Response code ").append(connection.getResponseCode()).toString());
	            }
	            connection.disconnect();
			} catch (Exception e) {
				if(null != connection)
					connection.disconnect();
				log.error("Unable to report crash to Mmtrix, will try again later.", e);
			}
        
        }

        private final Crash crash;

        CrashSender(Crash crash)
        {
            super();
            this.crash = crash;
        }
    }

    private class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler
    {

        public void uncaughtException(Thread thread, Throwable throwable)
        {
            if(!handledException.compareAndSet(false, true))
            {
                log.debug("!handledException.compareAndSet(false, true)");
                return;
            }
            if(!CrashReporter.instance.isEnabled)
            {
                log.debug("A crash has been detected but crash reporting is disabled!");
                chainExceptionHandler(thread, throwable);
                return;
            }
            try
            {
                TicToc timer = new TicToc();
                timer.tic();
                log.debug((new StringBuilder()).append("A crash has been detected in ").append(thread.getStackTrace()[0].getClassName()).append(" and will be reported ASAP.").toString());
                Crash crash = new Crash(throwable);
                reportCrash(crash, true);
                log.debug((new StringBuilder()).append("Crash collection took ").append(timer.toc()).append("ms").toString());
                chainExceptionHandler(thread, throwable);
            }
            catch(Throwable t)
            {
                log.error("Error encountered while preparing crash for Mmtrix!", t);
                chainExceptionHandler(thread, throwable);
            }
            return;
        }

        private void chainExceptionHandler(Thread thread, Throwable throwable)
        {
            if(previousExceptionHandler != null)
            {
                log.debug((new StringBuilder()).append("Chaining crash reporting duties to ").append(previousExceptionHandler.getClass().getSimpleName()).toString());
                previousExceptionHandler.uncaughtException(thread, throwable);
            }
        }

        private final AtomicBoolean handledException;

        private UncaughtExceptionHandler()
        {
            super();
            handledException = new AtomicBoolean(false);
        }

    }


    public CrashReporter()
    {
        isEnabled = false;
        reportCrashes = true;
    }

    public static CrashReporter initialize(){
    	return new CrashReporter();
    }
    
    public void start(Context context)
    {
        if(!initialized.compareAndSet(false, true) || null == context)
            return;
        mcontext = context;
        instance.reportSavedCrashes();
        if(instance.isEnabled)
            instance.installCrashHandler();
    }


    public UncaughtExceptionHandler getHandler()
    {
        return new UncaughtExceptionHandler();
    }

    public static UncaughtExceptionHandler getInstanceHandler()
    {
        return instance.getHandler();
    }

    public static void setReportCrashes(boolean reportCrashes)
    {
        instance.reportCrashes = reportCrashes;
    }

    public static int storedCrashes()
    {
        return instance.crashStore.count();
    }

    public static List fetchAllCrashes()
    {
        return instance.crashStore.fetchAll();
    }

    public static void clear()
    {
        instance.crashStore.clear();
    }

    private void installCrashHandler()
    {
        Thread.UncaughtExceptionHandler currentExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if(currentExceptionHandler != null)
        {
            if(currentExceptionHandler instanceof UncaughtExceptionHandler)
            {
                log.debug("Mmtrix crash handler already installed.");
                return;
            }
            previousExceptionHandler = currentExceptionHandler;
            log.debug((new StringBuilder()).append("Installing Mmtrix crash handler and chaining ").append(previousExceptionHandler.getClass().getName()).append(".").toString());
        } else
        {
            log.debug("Installing Mmtrix crash handler.");
        }
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    }

    public static void resetForTesting()
    {
        initialized.compareAndSet(true, false);
    }

    private void reportSavedCrashes()
    {
        Crash crash;
        for(Iterator i$ = crashStore.fetchAll().iterator(); i$.hasNext(); reportCrash(crash, false))
            crash = (Crash)i$.next();

    }

    private void reportCrash(Crash crash, boolean block)
    {
        crashStore.store(crash);
        if(reportCrashes)
        {
            CrashSender sender = new CrashSender(crash);
            Thread senderThread = new Thread(sender);
            senderThread.start();
            if(block)
                try
                {
                    senderThread.join();
                }
                catch(InterruptedException e)
                {
                    log.error("Exception caught while waiting to send crash", e);
                }
        }
    }

    public CrashReporter usingSsl(Boolean use){
    	isuseSSL = use;
    	return this;
    }
    
    public CrashReporter usingCollectorAddress(String host1){
    	host = host1;
    	return this;
    }
    
    public static DeviceInformation getDeviceInformation()
	{
	    if(deviceInformation != null)
	    {
	        return deviceInformation;
	    } else
	    {
	        DeviceInformation info = new DeviceInformation();
	        info.setOsName("Android");
	        info.setOsVersion(android.os.Build.VERSION.RELEASE);
	        info.setOsBuild(android.os.Build.VERSION.INCREMENTAL);
	        info.setModel(Build.MODEL);
	        info.setAgentName("AndroidAgent");
	        info.setAgentVersion(agentVersion);
	        info.setManufacturer(Build.MANUFACTURER);
	        info.setDeviceId(getuuid());
	        info.setArchitecture(System.getProperty("os.arch"));
	        info.setRunTime(System.getProperty("java.vm.version"));
	        if(null != mcontext)
	        	info.setSize(deviceForm(mcontext).name().toLowerCase());
	        deviceInformation = info;
	        return deviceInformation;
	    }
	}
    
    public static EnvironmentInformation getEnvironmentInformation()
	{
	    EnvironmentInformation envInfo;
	    ActivityManager activityManager;
	    long free[];
	    envInfo = new EnvironmentInformation();
	    activityManager = (ActivityManager)mcontext.getSystemService("activity");
	    free = new long[2];
	    StatFs rootStatFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
	    StatFs externalStatFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
	    try{
		    if(android.os.Build.VERSION.SDK_INT >= 18)
		    {
		        free[0] = rootStatFs.getAvailableBlocksLong() * rootStatFs.getBlockSizeLong();
		        free[1] = externalStatFs.getAvailableBlocksLong() * rootStatFs.getBlockSizeLong();
		    } else
		    {
		        free[0] = rootStatFs.getAvailableBlocks() * rootStatFs.getBlockSize();
		        free[1] = externalStatFs.getAvailableBlocks() * externalStatFs.getBlockSize();
		    }
		    if(free[0] < 0L)
		        free[0] = 0L;
		    if(free[1] < 0L)
		        free[1] = 0L;
		    envInfo.setDiskAvailable(free);
	    }
	    catch(Exception e){
		    if(free[0] < 0L)
		        free[0] = 0L;
		    if(free[1] < 0L)
		        free[1] = 0L;
		    envInfo.setDiskAvailable(free);
	    }
	  
	    envInfo.setMemoryUsage(sampleMemory(activityManager).getSampleValue().asLong().longValue());
	    envInfo.setOrientation(mcontext.getResources().getConfiguration().orientation);
	    envInfo.setNetworkStatus(getNetworkCarrier());
	    envInfo.setNetworkWanType(getNetworkWanType());
	    return envInfo;
	}
    
    private static String getuuid(){
    	if(null == uuid)
    		uuid = UUID.randomUUID().toString();
    	return uuid;
    }
    
    private static DeviceForm deviceForm(Context context)
	{
	    int deviceSize = context.getResources().getConfiguration().screenLayout & 15;
	    switch(deviceSize)
	    {
	    case 1: // '\001'
	        return DeviceForm.SMALL;
	
	    case 2: // '\002'
	        return DeviceForm.NORMAL;
	
	    case 3: // '\003'
	        return DeviceForm.LARGE;
	    }
	    if(deviceSize > 3)
	        return DeviceForm.XLARGE;
	    else
	        return DeviceForm.UNKNOWN;
	}
    
    public static Sample sampleMemory(ActivityManager activityManager)
	{
	    android.os.Debug.MemoryInfo memInfo[] = activityManager.getProcessMemoryInfo(PID);
	    int totalPss = memInfo[0].getTotalPss();
	    if(totalPss >= 0)
	    {
	        Sample sample = new Sample(miles.sample.Sample.SampleType.MEMORY);
	        sample.setSampleValue((double)totalPss / 1024D);
	        return sample;
	    } else
	    {
	        return null;
	    }
	}
    
    public static String getNetworkCarrier()
	{
	    return Connectivity.carrierNameFromContext(mcontext);
	}
	
	public static String getNetworkWanType()
	{
	    return Connectivity.wanType(mcontext);
	}
    
	public static ApplicationInformation getApplicationInformation(){
		if(null == applicationInformation)
			try {
				initApplicationInformation();
			} catch (AgentInitializationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return applicationInformation;
	}
	
	public static void initApplicationInformation() throws AgentInitializationException
	{
	    if(applicationInformation != null)
	    {
	        log.debug("attempted to reinitialize ApplicationInformation.");
	        return;
	    }
	    String packageName = mcontext.getPackageName();
	    PackageManager packageManager = mcontext.getPackageManager();
	    if(appVersion == null || appVersion.equals(""))
	        try
	        {
	            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
	            if(packageInfo != null && packageInfo.versionName != null && packageInfo.versionName.length() > 0)
	                appVersion = packageInfo.versionName;
	            else
	                throw new AgentInitializationException("Your app doesn't appear to have a version defined. Ensure you have defined 'versionName' in your manifest.");
	        }
	        catch(android.content.pm.PackageManager.NameNotFoundException e)
	        {
	            throw new AgentInitializationException((new StringBuilder()).append("Could not determine package version: ").append(e.getMessage()).toString());
	        }
	    log.debug((new StringBuilder()).append("Using application version ").append(appVersion).toString());
	    String appName;
	    try
	    {
	        android.content.pm.ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);
	        if(info != null)
	            appName = packageManager.getApplicationLabel(info).toString();
	        else
	            appName = packageName;
	    }
	    catch(android.content.pm.PackageManager.NameNotFoundException e)
	    {
	        log.warning(e.toString());
	        appName = packageName;
	    }
	    catch(SecurityException e)
	    {
	        log.warning(e.toString());
	        appName = packageName;
	    }
	    applicationInformation = new ApplicationInformation(appName, appVersion, packageName);
	}
	
	public static void withApplicationVersion(String appVersion1)
    {
        if(appVersion != null)
            appVersion = appVersion1;
    }
	
    private static final String CRASH_COLLECTOR_PATH = "/mobile_crash";
    private static final int CRASH_COLLECTOR_TIMEOUT = 5000;
    private static final CrashReporter instance = new CrashReporter();
    private static Boolean isuseSSL = false;
    private static String host = "mobile-collector.mmtrix.com";
    private static Context mcontext = null;
    private static DeviceInformation deviceInformation = null;
    private static String agentVersion = "1.0.0";
    private static String uuid = null;
    private static ApplicationInformation applicationInformation = null;
    private static String	appVersion = null;
    private static final int PID[] = {
	    android.os.Process.myPid()
	};
    
    
    private final static AgentLog log = AgentLogManager.getAgentLog();
    private boolean isEnabled;
    private boolean reportCrashes;
    private Thread.UncaughtExceptionHandler previousExceptionHandler;
    private CrashStore crashStore;
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
}
