package steeveeo.MCRSAServer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MCRSALogFormatter extends Formatter
{
	
	public String format(LogRecord record)
	{
		String formatted = "";
		
		//Add date
		formatted += calcDate(record.getMillis());
		
		//Add Level
		formatted += " [" + record.getLevel() + "]: ";
		
		//Add Message
		formatted += record.getMessage();
		
		return formatted;
	}
	
	private String calcDate(long millisecs)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
		Date result = new Date(millisecs);
		
		return dateFormat.format(result);
	}
	
}
