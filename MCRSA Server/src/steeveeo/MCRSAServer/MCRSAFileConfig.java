package steeveeo.MCRSAServer;

import java.io.File;
import java.util.List;

import org.bukkit.util.config.Configuration;

//Credit for this goes to JayJay110 on the Bukkit Forums!
//http://forums.bukkit.org/threads/tutorial-create-a-configuration-file-with-yaml.15975/
public class MCRSAFileConfig {
	
	private static MCRSAServer plugin;
    public MCRSAFileConfig(MCRSAServer instance) {
        plugin = instance;
    }

       public String directory = "plugins" + File.separator +"MCRSAServer";
       File file = new File(directory + File.separator + "config.yml");


    public void configCheck(){
        new File(directory).mkdir();


        if(!file.exists()){
            try {
                file.createNewFile();
                addDefaults();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {

            loadkeys();
        }
    }
    private void write(String root, Object x){
        Configuration config = load();
        config.setProperty(root, x);
        config.save();
    }
    private Boolean readBoolean(String root){
        Configuration config = load();
        return config.getBoolean(root, true);
    }
    @SuppressWarnings("unused")
	private Double readDouble(String root){
        Configuration config = load();
        return config.getDouble(root, 0);
    }
    @SuppressWarnings("unused")
    private int readInt(String root){
        Configuration config = load();
        return config.getInt(root, 0);
    }
    @SuppressWarnings("unused")
    private List<String> readStringList(String root){
        Configuration config = load();
        return config.getKeys(root);
    }
    @SuppressWarnings("unused")
    private String readString(String root){
        Configuration config = load();
        return config.getString(root);
    }
    private Configuration load(){

        try {
            Configuration config = new Configuration(file);
            config.load();
            return config;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void addDefaults(){
        plugin.log.info("[MCRSA] Config File Not Found, Generating...");
        
        write(plugin.enabledstartup, true);
        
        loadkeys();
        
        plugin.log.info("[MCRSA] Config File Generation Complete.");
    }
    private void loadkeys()
    {
    	plugin.enabled = readBoolean(plugin.enabledstartup);
    }
}
