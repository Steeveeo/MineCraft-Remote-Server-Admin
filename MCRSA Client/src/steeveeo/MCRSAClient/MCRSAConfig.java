package steeveeo.MCRSAClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

@SuppressWarnings("serial")
public class MCRSAConfig extends Properties
{
	
	public static String VERSION_STRING = "0.1a";
	String pathDir = MCRSAUtil.defaultDirectory() + "\\.MCRSA Client\\";
	
	//Constructor
	public MCRSAConfig()
	{
		//---------
		// Startup
		//---------
		
		//Existence Checks
		//--Main Asset Directory
		File assetDir = new File(pathDir);
		if(!assetDir.exists())
		{
			assetDir.mkdir();
		}
		//--Config File
		File cfgFile = new File(pathDir + "config.xml");
		if(!cfgFile.exists())
		{
			System.out.println("Config Not Found, generating.");
			writeDefaultCFG(cfgFile);
		}
		else
		{
			FileInputStream inFile;
			try {
				inFile = new FileInputStream(cfgFile);
				loadFromXML(inFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(!getProperty("Config Version").equals(VERSION_STRING))
			{
				System.out.println("Version Mismatch (expected \"" + VERSION_STRING + "\", got \"" + getProperty("Config Version") + "\"), generating.");
				cfgFile.delete();
				writeDefaultCFG(cfgFile);
			}
		}
	}
	
	//Write Out Settings Out
	public void saveConfig()
	{
		FileOutputStream outFile;
		try {
			outFile = new FileOutputStream(new File(pathDir + "config.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			storeToXML(outFile, "MineCraft Remote Server Admin Console Config");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void writeDefaultCFG(File cfgFile)
	{
		FileOutputStream outFile;
		try {
			outFile = new FileOutputStream(cfgFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		//Setup Defaults
		setProperty("Config Version",VERSION_STRING);
		setProperty("MAX_LINES","1024");
		setProperty("KEEPALIVE_INTERVAL","5000");
		setProperty("AUTORECONNECT","true");
		try {
			storeToXML(outFile, "MineCraft Remote Server Admin Console Config");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
