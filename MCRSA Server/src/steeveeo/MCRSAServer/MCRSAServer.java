package steeveeo.MCRSAServer;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class MCRSAServer extends JavaPlugin
{
	//Start Logger
	Logger log = Logger.getLogger("Minecraft");
	MCRSALogHandler logHandler = new MCRSALogHandler();
	MCRSALogFormatter logFormatter = new MCRSALogFormatter();
	
	//Start Config File Stuffs
    public String enabledstartup = "Plugin Enabled";
    public boolean enabled;
    MCRSAFileConfig config = new MCRSAFileConfig(this);
	    
    //Preferences
    public static int SERVER_PORT = 25566;
    public static Boolean MASTERPASS_ENABLED = true;
    public static String MASTERPASS = "ChangeMe";
    public static int MAX_CLIENTS = 20;
    public static int MAX_ATTEMPTS_FOR_BAN = 5;
    public static int ATTEMPT_BAN_MINUTES = 5;
    
	//Startup and Shutdown
	public void onEnable()
	{
		//Init Config
		config.configCheck();
		
		//Setup Logging
		log.addHandler(logHandler);
		logHandler.setFormatter(logFormatter);
		
		//Startup was good!
		log.info("[MCRSA] - Version 0.1 Started.");
	} 
	
	public void onDisable()
	{ 
		log.info("[MCRSA] - Version 0.1 Stopped.");
		logHandler.close();
	} 
}
