package steeveeo.MCRSAClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MCRSAUtil {

	//Get Timestamp
	public static String getTimeStamp()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	//Default file directory per-OS
	//Source: http://stackoverflow.com/questions/6561172/find-directory-for-application-data-on-linux-and-macintosh
	public static String defaultDirectory()
	{
	    String OS = System.getProperty("os.name").toUpperCase();
	    if (OS.contains("WIN"))
	        return System.getenv("APPDATA");
	    else if (OS.contains("MAC"))
	        return System.getProperty("user.home") + "/Library/Application "
	                + "Support";
	    else if (OS.contains("NUX"))
	        return System.getProperty("user.home");
	    return System.getProperty("user.dir");
	}
	
	//Numeric Range Checker
	public static boolean inRange(int check, int low, int high)
	{
		if(check >= low && check <= high)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
