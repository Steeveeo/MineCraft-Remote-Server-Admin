package steeveeo.MCRSAServer;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class MCRSAServer extends JavaPlugin
{
	//Start Logger
	Logger log = Logger.getLogger("Minecraft");
	
	//Start Config File Stuffs
    public String enabledstartup = "Plugin Enabled";
    public boolean enabled;
    MCRSAFileConfig config = new MCRSAFileConfig(this);
	    
	//Startup and Shutdown
	public void onEnable()
	{
		//Init Config
		config.configCheck();
		
		//Startup was good!
		log.info("[MCRSA] - Version 0.1 Started.");
	} 
	
	public void onDisable()
	{ 
		log.info("[MCRSA] - Version 0.1 Stopped.");
	} 
}
