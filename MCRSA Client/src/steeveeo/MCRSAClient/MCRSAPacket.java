package steeveeo.MCRSAClient;

public class MCRSAPacket
{
	//Vars
	public String packetData;
	
	//Constructors
	public MCRSAPacket()
	{
		packetData = "";
	}
	public MCRSAPacket(byte[] data)
	{
		packetData = data.toString();
	}
	public MCRSAPacket(char[] data)
	{
		packetData = data.toString();
	}
	public MCRSAPacket(String data)
	{
		packetData = data;
	}
	
	//Accessors
	//--Put
	public void putInt(int put)
	{
		packetData += put;
		packetData += "\n";
	}
	public void putBool(boolean put)
	{
		if(put)
		{
			packetData += "true";
		}
		else
		{
			packetData += "false";
		}
		packetData += "\n";
	}
	public void putString(String put)
	{
		packetData += put;
		packetData += "\n";
	}
	//--Get
	public int getInt()
	{
		char[] data = packetData.toCharArray();
		String gotInt = "";
		for(int index = 0; index < packetData.length(); index++)
		{
			char inChar = data[index];
			if(inChar == '\n')
			{
				break;
			}
			else
			{
				gotInt += inChar;
			}
		}
		
		return Integer.parseInt(gotInt);
	}
	public boolean getBool()
	{
		char[] data = packetData.toCharArray();
		String gotBool = "";
		for(int index = 0; index < packetData.length(); index++)
		{
			char inChar = data[index];
			if(inChar == '\n')
			{
				break;
			}
			else
			{
				gotBool += inChar;
			}
		}
		
		return Boolean.parseBoolean(gotBool);
	}
	public String getString()
	{
		char[] data = packetData.toCharArray();
		String gotString = "";
		for(int index = 0; index < packetData.length(); index++)
		{
			char inChar = data[index];
			if(inChar == '\n')
			{
				break;
			}
			else
			{
				gotString += inChar;
			}
		}
		
		return gotString;
	}
}
