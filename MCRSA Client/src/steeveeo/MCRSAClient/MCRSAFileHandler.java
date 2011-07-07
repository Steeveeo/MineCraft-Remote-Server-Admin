package steeveeo.MCRSAClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MCRSAFileHandler
{
	//Read in a file
	public static String loadFile(String path) throws IOException
	{
		String fileData = "";
		
		File inputFile = new File(path);
		
		//Read File
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String line = null;
		while ((line=reader.readLine()) != null)
		{
			fileData += line;
			fileData += '\n';
		}
		
		reader.close();
		return fileData;
	}
	
	//Write displayed log to file
	public static void writeFile(File output, String data) throws IOException
	{
		FileWriter writer = new FileWriter(output);
		writer.write(data);
		
		writer.close();
	}
}
