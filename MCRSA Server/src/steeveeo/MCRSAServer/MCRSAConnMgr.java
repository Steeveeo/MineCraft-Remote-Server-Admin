package steeveeo.MCRSAServer;

import java.net.Socket;

public class MCRSAConnMgr extends Thread
{
	//Client thread
	private class ClientConnection implements Runnable
	{
		//Client vars
		private String UserName;
		private String Password;
		private boolean Authed;
		
		//Connection vars
		private Socket client;
		private String IPAddress;
		
		//Constructor
		public ClientConnection(Socket connection)
		{
			client = connection;
			IPAddress = connection.getInetAddress().getHostAddress();
			UserName = "";
			Password = "";
			Authed = false;
		}

		public void run()
		{
			
		}
		
		//Authentication
		private boolean authenticate()
		{
			boolean success = false;
			
			//Check against master password, else check user account
			if(MCRSAServer.MASTERPASS_ENABLED)
			{
				if(Password == MCRSAServer.MASTERPASS)
				{
					success = true;
					Authed = true;
				}
			}
			else
			{
				//TODO: Write Useraccount stuff
			}
			
			return success;
		}
		
	}
}