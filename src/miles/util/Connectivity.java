package miles.util;

import java.text.MessageFormat;

import miles.logging.AgentLog;
import miles.logging.AgentLogManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

public final class Connectivity
{

    public Connectivity()
    {
    }

    public static String carrierNameFromContext(Context context)
    {
        NetworkInfo networkInfo;
        try
        {
            networkInfo = getNetworkInfo(context);
        }
        catch(SecurityException e)
        {
            return "unknown";
        }
        if(!isConnected(networkInfo))
            return "none";
        if(isWan(networkInfo))
            return carrierNameFromTelephonyManager(context);
        if(isWifi(networkInfo))
        {
            return "wifi";
        } else
        {
            log.warning(MessageFormat.format("Unknown network type: {0} [{1}]", new Object[] {
                networkInfo.getTypeName(), Integer.valueOf(networkInfo.getType())
            }));
            return "unknown";
        }
    }

    public static String wanType(Context context)
    {
        NetworkInfo networkInfo;
        try
        {
            networkInfo = getNetworkInfo(context);
        }
        catch(SecurityException e)
        {
            return "unknown";
        }
        if(!isConnected(networkInfo))
            return "none";
        if(isWifi(networkInfo))
            return "wifi";
        if(isWan(networkInfo))
            return connectionNameFromNetworkSubtype(networkInfo.getSubtype());
        else
            return "unknown";
    }

    private static boolean isConnected(NetworkInfo networkInfo)
    {
        return networkInfo != null && networkInfo.isConnected();
    }

    private static boolean isWan(NetworkInfo networkInfo)
    {
        switch(networkInfo.getType())
        {
        case 0: // '\0'
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
            return true;

        case 1: // '\001'
        default:
            return false;
        }
    }

    private static boolean isWifi(NetworkInfo networkInfo)
    {
        switch(networkInfo.getType())
        {
        case 1: // '\001'
        case 6: // '\006'
        case 7: // '\007'
        case 9: // '\t'
            return true;

        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
        case 8: // '\b'
        default:
            return false;
        }
    }

    private static NetworkInfo getNetworkInfo(Context context)
        throws SecurityException
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        try
        {
            return connectivityManager.getActiveNetworkInfo();
        }
        catch(SecurityException e)
        {
            log.warning("Cannot determine network state. Enable android.permission.ACCESS_NETWORK_STATE in your manifest.");
            throw e;
        }
    }

    private static String carrierNameFromTelephonyManager(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        String networkOperator = telephonyManager.getNetworkOperatorName();
        boolean smellsLikeAnEmulator = Build.PRODUCT.equals("google_sdk") || Build.PRODUCT.equals("sdk") || Build.PRODUCT.equals("sdk_x86") || Build.FINGERPRINT.startsWith("generic");
        if(networkOperator.equals("Android") && smellsLikeAnEmulator)
            return "wifi";
        else
            return networkOperator;
    }

    private static String connectionNameFromNetworkSubtype(int subType)
    {
        switch(subType)
        {
        case 7: // '\007'
            return "1xRTT";

        case 4: // '\004'
            return "CDMA";

        case 2: // '\002'
            return "EDGE";

        case 5: // '\005'
            return "EVDO rev 0";

        case 6: // '\006'
            return "EVDO rev A";

        case 1: // '\001'
            return "GPRS";

        case 8: // '\b'
            return "HSDPA";

        case 10: // '\n'
            return "HSPA";

        case 9: // '\t'
            return "HSUPA";

        case 3: // '\003'
            return "UMTS";

        case 11: // '\013'
            return "IDEN";

        case 12: // '\f'
            return "EVDO rev B";

        case 15: // '\017'
            return "HSPAP";

        case 14: // '\016'
            return "HRPD";

        case 13: // '\r'
            return "LTE";

        case 0: // '\0'
        default:
            return "unknown";
        }
    }

    private static final String ANDROID = "Android";
    private static AgentLog log = AgentLogManager.getAgentLog();

}
