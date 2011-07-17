package steeveeo.MCRSAClient;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.MaskFormatter;

@SuppressWarnings("serial")
public class MCRSAClient extends JFrame implements ActionListener
{
	public static String VERSIONSTRING = "0.1a";
	
	//Load Preferences from File
	MCRSAConfig config = new MCRSAConfig();
	int MAX_LINES = Integer.parseInt(config.getProperty("MAX_LINES"));
	int KEEPALIVE_INTERVAL = Integer.parseInt(config.getProperty("KEEPALIVE_INTERVAL"));
	boolean AUTORECONNECT = Boolean.parseBoolean(config.getProperty("AUTORECONNECT"));
	
	//Program Variables
	MCRSALog LogDisplay = new MCRSALog(MAX_LINES);
	boolean LogLinkBottom = true;

	//Load Log Test
	String preParse = "";
	String displayString = "";
	{
		try 
		{
			preParse = MCRSAFileHandler.loadFile("C:\\Users\\Steeveeo\\Desktop\\Minecraft Dev Server\\server.log");
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
		LinkedList<String> parsedList = MCRSALog.parseString(preParse, '\n');
		LogDisplay.importAll(parsedList);
		displayString = LogDisplay.exportString('\n');
	}
	
	//Element Vars
	//--Menu Bar
	private JMenu Menu_File, Menu_Connect, Menu_Options;
	private JMenuItem File_SaveLog, File_Exit;
	private JMenuItem Connect_ServerManager, Connect_Connect, Connect_Disconnect;
	private JMenuItem Options_Preferences, Options_ViewHelp, Options_ViewAbout;
	//--Control Screen
	private JTextArea LogDisplay_All;
	private JScrollPane LogPane_All;
	private JPanel Command_Panel;
	private JTextField Command_Field;
	private JButton Command_Submit, Log_Link;
	//--Preferences Screen
	private JFrame Pref_Frame;
	private JPanel Pref_SettingsPanel;
	private JFormattedTextField Pref_MaxLines, Pref_KAInterval;
	private JRadioButton Pref_AutoreconnectEnable, Pref_AutoreconnectDisable;
	private ButtonGroup Pref_Autoreconnect;
	private JPanel Pref_ButtonPanel;
	private JButton Pref_Submit, Pref_Cancel;
	
	//Fonts
	Font FONT_TIMES = new Font("Times New Roman", 0, 12);
	Font FONT_TIMESBOLD = new Font("Times New Roman", 1, 12);
	
	//Constructor
	public MCRSAClient()
	{
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//----------------
		// Setup Menu Bar
		//----------------
		
		//File Menu
		Menu_File = new JMenu("File");
		Menu_File.setFont(FONT_TIMES);
		Menu_File.setMnemonic(KeyEvent.VK_F);
		
		//--Save Log
		File_SaveLog = new JMenuItem("Save Log");
		File_SaveLog.setFont(FONT_TIMES);
		File_SaveLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		Menu_File.add(File_SaveLog);
		File_SaveLog.addActionListener(this);
		
		//--Close
		File_Exit = new JMenuItem("Exit");
		File_Exit.setFont(FONT_TIMES);
		File_Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		Menu_File.add(File_Exit);
		File_Exit.addActionListener(this);
		
		//--End File Menu--
		
		//Connection Menu
		Menu_Connect = new JMenu("Connect");
		Menu_Connect.setFont(FONT_TIMES);
		Menu_Connect.setMnemonic(KeyEvent.VK_C);
		
		//--Server Manager
		Connect_ServerManager = new JMenuItem("Server Manager");
		Connect_ServerManager.setFont(FONT_TIMES);
		Connect_ServerManager.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK));
		//Menu_Connect.add(Connect_ServerManager);
		//Connect_ServerManager.addActionListener(this);
		
		//--New Connection
		Connect_Connect = new JMenuItem("Connect");
		Connect_Connect.setFont(FONT_TIMES);
		Connect_Connect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		Menu_Connect.add(Connect_Connect);
		Connect_Connect.addActionListener(this);
		
		//--Close Current Connection
		Connect_Disconnect = new JMenuItem("Disconnect");
		Connect_Disconnect.setFont(FONT_TIMES);
		Connect_Disconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.CTRL_MASK));
		Menu_Connect.add(Connect_Disconnect);
		Connect_Disconnect.addActionListener(this);
		
		//--End Connection Menu--
		
		//Options Menu
		Menu_Options = new JMenu("Options");
		Menu_Options.setFont(FONT_TIMES);
		Menu_Options.setMnemonic(KeyEvent.VK_O);
		
		//--Preferences
		Options_Preferences = new JMenuItem("Preferences");
		Options_Preferences.setFont(FONT_TIMES);
		Options_Preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		Menu_Options.add(Options_Preferences);
		Options_Preferences.addActionListener(this);
		
		Menu_Options.addSeparator();
		
		//--Help (ToDo, Help Documentation)
//		Options_ViewHelp = new JMenuItem("Help");
//		Options_ViewHelp.setFont(FONT_TIMES);
//		Options_ViewHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
//		Menu_Options.add(Options_ViewHelp);
//		Options_ViewHelp.addActionListener(this);
		
		//--About
		Options_ViewAbout = new JMenuItem("About");
		Options_ViewAbout.setFont(FONT_TIMES);
		Options_ViewAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		Menu_Options.add(Options_ViewAbout);
		Options_ViewAbout.addActionListener(this);
		
		
		//--Apply Menu Bar
		JMenuBar MenuBar = new JMenuBar();
		MenuBar.add(Menu_File);
		MenuBar.add(Menu_Connect);
		MenuBar.add(Menu_Options);
		setJMenuBar(MenuBar);
		
		//--End Menu Bar--
		
		//------------------
		// Setup Log Viewer
		//------------------
		
		//Start Tabs
		//JTabbedPane LogTabs = new JTabbedPane();
		
		//Create Log Box
		LogDisplay_All = new JTextArea(5, 30);
		LogDisplay_All.setText(displayString);
		LogDisplay_All.setCaretPosition(LogDisplay_All.getDocument()
				.getLength());
		LogDisplay_All.setEditable(false);
		LogPane_All = new JScrollPane(LogDisplay_All);
		contentPane.add(LogPane_All, BorderLayout.CENTER);
		LogPane_All.getViewport().setViewPosition(new Point(0, MAX_LINES*20));
		
		//--End Log Viewer--
		
		//-------------------
		// Setup Command Bar
		//-------------------
		
		//Container Panel
		Command_Panel = new JPanel();
		GridBagLayout Command_GridLayout = new GridBagLayout();
		GridBagConstraints Command_GridCons = new GridBagConstraints();
		Command_GridCons.fill = GridBagConstraints.BOTH;
		Command_Panel.setLayout(Command_GridLayout);
		
		//Bar
		Command_Field = new JTextField();
		Command_GridCons.anchor = GridBagConstraints.WEST;
		Command_GridCons.gridwidth = GridBagConstraints.RELATIVE;
		Command_GridCons.weightx = 1.0;
		Command_GridLayout.setConstraints(Command_Field, Command_GridCons);
		Command_Field.setSize(contentPane.getWidth(), getHeight());
		Command_Panel.add(Command_Field);
		//--Enterkey Listener
		Command_Field.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent key)
			{
				//Dummy to shut this up
			}
			public void keyReleased(KeyEvent key)
			{
				if(key.getKeyCode() == KeyEvent.VK_ENTER)
				{
					sendCommand(Command_Field.getText());
				}
			}
			public void keyTyped(KeyEvent key)
			{
				//Dummy to shut this up
			}
			
		});
		
		//Submit
		Command_Submit = new JButton("Send");
		Command_GridCons.anchor = GridBagConstraints.EAST;
		Command_GridCons.gridwidth = GridBagConstraints.RELATIVE;
		Command_GridCons.weightx = 0.0;
		Command_GridLayout.setConstraints(Command_Submit, Command_GridCons);
		Command_Panel.add(Command_Submit);
		Command_Submit.addActionListener(this);
		
		//Link log to bottom
		Log_Link = new JButton("L");
		Log_Link.setFont(FONT_TIMESBOLD);
		Command_GridCons.anchor = GridBagConstraints.EAST;
		Command_GridCons.gridwidth = GridBagConstraints.RELATIVE;
		Command_GridCons.weightx = 0.0;
		Command_GridLayout.setConstraints(Log_Link, Command_GridCons);
		Command_Panel.add(Log_Link);
		Log_Link.addActionListener(this);
		
		//Apply
		contentPane.add(Command_Panel, BorderLayout.SOUTH);
		Command_Panel.setVisible(true);
		
		
		//--End Command Bar--
		
		
		//Focus Here
		requestFocus();
		
		//Window Closed, end program
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}
	
	private void openPreferencesMenu()
	{
		//Setup Window
		Pref_Frame = new JFrame();
		Pref_Frame.setTitle("MCRSA Preferences");
		Pref_Frame.setSize(256, 256);
		Pref_Frame.setMinimumSize(new Dimension(256, 256));
		Pref_Frame.setLocation(150,150);
		Pref_Frame.setVisible(true);
		Pref_Frame.setResizable(false);
		Pref_Frame.setLayout(new BorderLayout());
		
		
		//Settings Panel
		Pref_SettingsPanel = new JPanel();
		GridBagLayout Pref_SettingsLayout = new GridBagLayout();
		GridBagConstraints Pref_SettingsCons = new GridBagConstraints();
		Pref_SettingsCons.fill = GridBagConstraints.BOTH;
		Pref_SettingsPanel.setLayout(Pref_SettingsLayout);
		
		//--AutoReconnet Radiobuttons
		JLabel Pref_ARLabel = new JLabel("Autoreconnect on Loss");
		Pref_SettingsCons.anchor = GridBagConstraints.WEST;
		Pref_SettingsCons.gridwidth = GridBagConstraints.REMAINDER;
		Pref_SettingsCons.weightx = 0.0;
		Pref_SettingsLayout.setConstraints(Pref_ARLabel, Pref_SettingsCons);
		Pref_SettingsPanel.add(Pref_ARLabel);
		//---Enable
		Pref_AutoreconnectEnable = new JRadioButton("Enable", AUTORECONNECT);
		Pref_SettingsCons.anchor = GridBagConstraints.WEST;
		Pref_SettingsCons.gridwidth = GridBagConstraints.RELATIVE;
		Pref_SettingsCons.weightx = 0.0;
		Pref_SettingsLayout.setConstraints(Pref_AutoreconnectEnable, Pref_SettingsCons);
		Pref_SettingsPanel.add(Pref_AutoreconnectEnable);
		//---Disable
		Pref_AutoreconnectDisable = new JRadioButton("Disable", !AUTORECONNECT);
		Pref_SettingsCons.anchor = GridBagConstraints.WEST;
		Pref_SettingsCons.gridwidth = GridBagConstraints.REMAINDER;
		Pref_SettingsCons.weightx = 0.0;
		Pref_SettingsLayout.setConstraints(Pref_AutoreconnectDisable, Pref_SettingsCons);
		Pref_SettingsPanel.add(Pref_AutoreconnectDisable);
		//---Grouping
		Pref_Autoreconnect = new ButtonGroup();
		Pref_Autoreconnect.add(Pref_AutoreconnectEnable);
		Pref_Autoreconnect.add(Pref_AutoreconnectDisable);
		
		
		//--Max Lines
		JLabel Pref_MLLabel = new JLabel("Maximum Log Lines (min: 10)");
		Pref_SettingsCons.anchor = GridBagConstraints.WEST;
		Pref_SettingsCons.gridwidth = GridBagConstraints.REMAINDER;
		Pref_SettingsCons.weightx = 0.0;
		Pref_SettingsLayout.setConstraints(Pref_MLLabel, Pref_SettingsCons);
		Pref_SettingsPanel.add(Pref_MLLabel);
		//---Control Field
		MaskFormatter MaxLines_FieldMask;
		try {
			MaxLines_FieldMask = new MaskFormatter("#######");
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
			return;
		}
		Pref_MaxLines = new JFormattedTextField(MaxLines_FieldMask);
		Pref_MaxLines.setFocusLostBehavior(JFormattedTextField.PERSIST); 
		Pref_MaxLines.setText(Integer.toString(MAX_LINES));
		//----Trim mask spaces on focus (countermeasure to MaskFormatter's behavior)
		Pref_MaxLines.addFocusListener(new FocusListener()
		{
			public void focusGained(FocusEvent arg0)
			{
				Pref_MaxLines.setText(Pref_MaxLines.getText().trim());
			}
			public void focusLost(FocusEvent arg0)
			{
				Pref_KAInterval.setText(Pref_KAInterval.getText().trim());
			}
			
		});
		Pref_SettingsCons.anchor = GridBagConstraints.WEST;
		Pref_SettingsCons.gridwidth = GridBagConstraints.REMAINDER;
		Pref_SettingsCons.weightx = 0.0;
		Pref_SettingsLayout.setConstraints(Pref_MaxLines, Pref_SettingsCons);
		Pref_SettingsPanel.add(Pref_MaxLines);
		
		
		//--Keepalive Interval
		JLabel Pref_KALabel = new JLabel("KeepAlive Interval (ms) (min: 100)");
		Pref_SettingsCons.anchor = GridBagConstraints.WEST;
		Pref_SettingsCons.gridwidth = GridBagConstraints.REMAINDER;
		Pref_SettingsCons.weightx = 0.0;
		Pref_SettingsLayout.setConstraints(Pref_KALabel, Pref_SettingsCons);
		Pref_SettingsPanel.add(Pref_KALabel);
		//---Control Field
		MaskFormatter KeepAlive_FieldMask;
		try {
			KeepAlive_FieldMask = new MaskFormatter("#######");
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
			return;
		}
		Pref_KAInterval = new JFormattedTextField(KeepAlive_FieldMask);
		Pref_KAInterval.setFocusLostBehavior(JFormattedTextField.PERSIST); 
		Pref_KAInterval.setText(Integer.toString(KEEPALIVE_INTERVAL));
		//----Trim mask spaces on focus (countermeasure to MaskFormatter's behavior)
		Pref_KAInterval.addFocusListener(new FocusListener()
		{
			public void focusGained(FocusEvent arg0)
			{
				Pref_KAInterval.setText(Pref_KAInterval.getText().trim());
			}
			public void focusLost(FocusEvent arg0)
			{
				Pref_KAInterval.setText(Pref_KAInterval.getText().trim());
			}
			
		});
		Pref_SettingsCons.anchor = GridBagConstraints.WEST;
		Pref_SettingsCons.gridwidth = GridBagConstraints.REMAINDER;
		Pref_SettingsCons.weightx = 0.0;
		Pref_SettingsLayout.setConstraints(Pref_KAInterval, Pref_SettingsCons);
		Pref_SettingsPanel.add(Pref_KAInterval);
		
		
		//--Apply
		Pref_Frame.add(Pref_SettingsPanel, BorderLayout.CENTER);
		Pref_SettingsPanel.setVisible(true);
		
		
		
		//Button Container Panel
		Pref_ButtonPanel = new JPanel();
		GridBagLayout Pref_ButtonLayout = new GridBagLayout();
		GridBagConstraints Pref_ButtonCons = new GridBagConstraints();
		Pref_ButtonCons.fill = GridBagConstraints.BOTH;
		Pref_ButtonPanel.setLayout(Pref_ButtonLayout);
		
		//--Submit Button
		Pref_Submit = new JButton("OK");
		Pref_ButtonCons.anchor = GridBagConstraints.EAST;
		Pref_ButtonCons.gridwidth = GridBagConstraints.RELATIVE;
		Pref_ButtonCons.weightx = 0.0;
		Pref_ButtonLayout.setConstraints(Pref_Submit, Pref_ButtonCons);
		Pref_ButtonPanel.add(Pref_Submit);
		Pref_Submit.addActionListener(this);
		
		//--Cancel Button
		Pref_Cancel = new JButton("Cancel");
		Pref_ButtonCons.anchor = GridBagConstraints.EAST;
		Pref_ButtonCons.gridwidth = GridBagConstraints.REMAINDER;
		Pref_ButtonCons.weightx = 0.0;
		Pref_ButtonLayout.setConstraints(Pref_Cancel, Pref_ButtonCons);
		Pref_ButtonPanel.add(Pref_Cancel);
		Pref_Cancel.addActionListener(this);
		
		
		//--Apply
		Pref_Frame.add(Pref_ButtonPanel, BorderLayout.SOUTH);
		Pref_ButtonPanel.setVisible(true);
	}
	
	//About Menu
	public void openAboutPanel()
	{
		//Setup Window
		JFrame about = new JFrame();
		about.setTitle("About MCRSA");
		about.setSize(340, 128);
		about.setLocation(150,150);
		about.setVisible(true);
		about.setResizable(false);
		about.setLayout(new BorderLayout());
		
		//Text Panel
		JPanel aboutTextContainer = new JPanel();
		GridBagLayout aboutTextLayout = new GridBagLayout();
		GridBagConstraints aboutTextCons = new GridBagConstraints();
		aboutTextCons.fill = GridBagConstraints.BOTH;
		aboutTextContainer.setLayout(aboutTextLayout);
		
		//Text
		JLabel aboutLabelAuth = new JLabel("Author: Steeveeo");
		aboutTextCons.anchor = GridBagConstraints.WEST;
		aboutTextCons.gridwidth = GridBagConstraints.REMAINDER;
		aboutTextCons.weightx = 0.0;
		aboutTextLayout.setConstraints(aboutLabelAuth, aboutTextCons);
		aboutTextContainer.add(aboutLabelAuth);
		JLabel aboutLabelVer = new JLabel("Version: " + VERSIONSTRING);
		aboutTextCons.anchor = GridBagConstraints.WEST;
		aboutTextCons.gridwidth = GridBagConstraints.REMAINDER;
		aboutTextCons.weightx = 0.0;
		aboutTextLayout.setConstraints(aboutLabelVer, aboutTextCons);
		aboutTextContainer.add(aboutLabelVer);
		JLabel aboutLabelRel = new JLabel("Last Release: <FILL OUT>");
		aboutTextCons.anchor = GridBagConstraints.WEST;
		aboutTextCons.gridwidth = GridBagConstraints.REMAINDER;
		aboutTextCons.weightx = 0.0;
		aboutTextLayout.setConstraints(aboutLabelRel, aboutTextCons);
		aboutTextContainer.add(aboutLabelRel);
		
		//Apply
		about.add(aboutTextContainer, BorderLayout.WEST);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		//File -> Save Log
		if(event.getSource() == File_SaveLog)
		{
			JFileChooser fileSaver = new JFileChooser();
			int retval = fileSaver.showSaveDialog(null);
			if (retval == JFileChooser.APPROVE_OPTION)
			{
			    File myFile = fileSaver.getSelectedFile();
			    
			    try
			    {
					MCRSAFileHandler.writeFile(myFile, LogDisplay.exportString('\n'));
				}
			    catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		//File -> Exit
		if(event.getSource() == File_Exit)
		{
			System.exit(0);
		}
		
		//Options -> Preferences
		if(event.getSource() == Options_Preferences)
		{
			openPreferencesMenu();
		}
		
		//Options -> About
		if(event.getSource() == Options_ViewAbout)
		{
			openAboutPanel();
		}
		
		//Send Command Button
		if(event.getSource() == Command_Submit)
		{
			if(!Command_Field.getText().isEmpty())
			{
				sendCommand(Command_Field.getText());
			}
		}
		
		//-------------------
		// Preferences Panel
		//-------------------
		
		//Submit
		if(event.getSource() == Pref_Submit)
		{
			//Set All Variables to Config
			//--AutoReconnect
			Boolean AutoReconnectEnabled = Pref_AutoreconnectEnable.isSelected();
			config.setProperty("AUTORECONNECT",AutoReconnectEnabled.toString());
			AUTORECONNECT = AutoReconnectEnabled;
			//--Max Lines
			int MaxLinesSet = Math.max(Integer.parseInt(Pref_MaxLines.getText().trim()), 10);
			config.setProperty("MAX_LINES", Pref_MaxLines.getText().trim());
			if(MaxLinesSet > MAX_LINES)
			{
				LogDisplay.resize(MaxLinesSet);
			}
			MAX_LINES = MaxLinesSet;
			//--KeepAlive Interval
			int KeepAliveSet = Math.max(Integer.parseInt(Pref_KAInterval.getText().trim()), 100);
			config.setProperty("KEEPALIVE_INTERVAL", Pref_KAInterval.getText().trim());
			KEEPALIVE_INTERVAL = KeepAliveSet;
			
			//Save Config
			config.saveConfig();
			
			//Close Frame
			Pref_Frame.dispose();
		}
		
		//Cancel
		if(event.getSource() == Pref_Cancel)
		{
			Pref_Frame.dispose();
		}
		
	}
	
	//Send a command to the console
	public void sendCommand(String cmd)
	{
		//Clear Command Field
		Command_Field.setText("");
		
		//Update Log
		LogDisplay.appendItem(MCRSAUtil.getTimeStamp() + " > [SENT COMMAND]: " + cmd);
		LogDisplay_All.setText(LogDisplay.exportString('\n'));
		
		//Move Viewport (if scroll is at bottom)
		if(MCRSAUtil.inRange(LogPane_All.getViewport().getViewPosition().y, LogDisplay.size()*19, LogDisplay.size()*20))
		{
			LogPane_All.getViewport().setViewPosition(new Point(0,LogDisplay.size()*20));
		}
		
		//Transmit
		
	}
	
	//Main
	public static void main(String args[])
	{
		MCRSAClient client = new MCRSAClient();
		
		//Setup Window
		client.setTitle("MineCraft Remote Server Admin - Client " + VERSIONSTRING);
		client.setSize(768, 512);
		client.setMinimumSize(new Dimension(512,256));
		client.setLocation(100,100);
		client.setVisible(true);
		client.setResizable(true);
	}

}
