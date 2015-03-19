/*
 * Mmtrix APP MONITOR, MARK 0402
 */
package miles.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class Util
{

    public Util()
    {
    }

    public static String slurp(InputStream stream)
        throws IOException
    {
        char buf[] = new char[8192];
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        do
        {
            int n = reader.read(buf);
            if(n >= 0)
                sb.append(buf, 0, n);
            else
                return sb.toString();
        } while(true);
    }

    public static String sanitizeUrl(String urlString)
    {
        if(urlString == null)
            return null;
        URL url;
        try
        {
            url = new URL(urlString);
        }
        catch(MalformedURLException e)
        {
            return null;
        }
        StringBuffer sanitizedUrl = new StringBuffer();
        sanitizedUrl.append(url.getProtocol());
        sanitizedUrl.append("://");
        sanitizedUrl.append(url.getHost());
        if(url.getPort() != -1)
        {
            sanitizedUrl.append(":");
            sanitizedUrl.append(url.getPort());
        }
        sanitizedUrl.append(url.getPath());
        return sanitizedUrl.toString();
    }

    public static Random getRandom()
    {
        return random;
    }

    private static final Random random = new Random();

}

