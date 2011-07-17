package steeveeo.MCRSAServer;

import java.util.LinkedList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MCRSALogHandler extends Handler
{
	//Vars
	private LinkedList<String> logLines = new LinkedList<String>();
	Boolean enabled = true;
	
	//Constructor
	public MCRSALogHandler()
	{
		setLevel(Level.ALL);
	}
	
	public void close() throws SecurityException
	{
		//Reprint everything, so we know it works
		enabled = false;
		Logger log = Logger.getLogger("Minecraft");
		log.info("=================================================");
		log.info("======CLOSING LOG, REPRINTING FOR TEST===========");
		log.info("=================================================");
		
		while(logLines.size() > 0)
		{
			log.info(logLines.removeFirst());
		}
	}

	public void flush()
	{
		close();
	}

	public void publish(LogRecord record)
	{
		if(enabled)
		{
			logLines.add(getFormatter().format(record));
		}
	}
	
}
