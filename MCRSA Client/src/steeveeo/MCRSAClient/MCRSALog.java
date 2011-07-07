package steeveeo.MCRSAClient;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class MCRSALog extends LinkedList<String>
{
	//Vars
	private int MAX_ENTRIES = 1024;
	
	//Constructor
	public MCRSALog(){}
	public MCRSALog(int max)
	{
		if(max > 0)
		{
			MAX_ENTRIES = max;
		}
	}
	
	//Add item to list, overwrite if too big
	public void appendItem(String input)
	{
		if(input != null)
		{
			add(input);
			if(size() > MAX_ENTRIES)
			{
				removeFirst();
			}
		}
	}
	
	//Line amount changed, shorten to match
	public void resize(int newSize)
	{
		if(newSize > 0)
		{
			MAX_ENTRIES = newSize;
			while(size() > MAX_ENTRIES)
			{
				removeFirst();
			}
		}
	}
	
	//Import stuff into list
	public void importAll(LinkedList<String> input)
	{
		String thisEntry;
		while(input.size() > 0)
		{
			thisEntry = input.removeFirst();
			if(!thisEntry.isEmpty())
			{
				appendItem(thisEntry);
			}
		}
	}
	
	//Concat all items to one string with Separator in between each entry
	public String exportString(char delim)
	{
		String output = "";
		
		for(int ii = 0; ii < size(); ii++)
		{
			output += get(ii);
			output += delim;
		}
		
		return output;
	}
	
	//Explode a string into List items
	public static LinkedList<String> parseString(String input, char delim)
	{
		LinkedList<String> output = new LinkedList<String>();
		
		//Parse into entries by delimiter
		int caretPos = 0;
		while(caretPos < input.length())
		{
			String newEntry = "";
			char inChar = 0;
			while(caretPos < input.length())
			{
				try 
				{
					inChar = input.charAt(caretPos);
					caretPos++;
				} 
				catch (IndexOutOfBoundsException e1) 
				{
					output.add(newEntry);
					System.exit(1);
				}
				
				//Delim found, add to list
				if(inChar == delim)
				{
					output.add(newEntry);
					break;
				}
				//Add to entry
				else
				{
					newEntry += inChar;
				}
			}
		}
		
		return output;
	}
}
